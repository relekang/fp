/*
 * Created on Oct 27, 2004
 */
package no.ntnu.fp.net.co;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import no.ntnu.fp.net.admin.Log;
import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;

/**
 * Implementation of the Connection-interface. <br>
 * <br>
 * This class implements the behaviour in the methods specified in the interface
 * {@link Connection} over the unreliable, connectionless network realised in
 * {@link ClSocket}. The base class, {@link AbstractConnection} implements some
 * of the functionality, leaving message passing and error handling to this
 * implementation.
 * 
 * @author Sebjørn Birkeland and Stein Jakob Nordbø
 * @see no.ntnu.fp.net.co.Connection
 * @see no.ntnu.fp.net.cl.ClSocket
 */
public class ConnectionImpl extends AbstractConnection {

	public static final String DEBUG_ACCEPT = "DEBUG: ConnectionImpl->accept->";
	public static final String DEBUG_CONNECT = "DEBUG: ConnectionImpl->connect->";
	public static final String DEBUG_SEND = "DEBUG: ConnectionImpl->send->";
	public static final String DEBUG_RECEIVE = "DEBUG: ConnectionImpl->receive->";
	public static final String DEBUG_CLOSE = "DEBUG: ConnectionImpl->close->";

	/** Keeps track of the used ports for each server port. */
	private static Map<Integer, Boolean> usedPorts = Collections
			.synchronizedMap(new HashMap<Integer, Boolean>());

	/**
	 * Initialise initial sequence number and setup state machine.
	 * 
	 * @param myPort
	 *            - the local port to associate with this connection
	 */
	public ConnectionImpl(int myPort) {
		this.myPort = myPort;
		this.myAddress = getIPv4Address();
	}

	private String getIPv4Address() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "127.0.0.1";
		}
	}

	/**
	 * Establish a connection to a remote location.
	 * 
	 * @param remoteAddress
	 *            - the remote IP-address to connect to
	 * @param remotePort
	 *            - the remote portnumber to connect to
	 * @throws IOException
	 *             If there's an I/O error.
	 * @throws java.net.SocketTimeoutException
	 *             If timeout expires before connection is completed.
	 * @see Connection#connect(InetAddress, int)
	 */
	public void connect(InetAddress remoteAddress, int remotePort)
			throws IOException, SocketTimeoutException {
		this.remoteAddress = remoteAddress.getHostAddress();
		this.remotePort = remotePort;
		KtnDatagram syn = constructInternalPacket(Flag.SYN);
		try {
			simplySendPacket(syn);
		} catch (ClException e) {
			e.printStackTrace();
		}
		state = State.SYN_SENT;
		KtnDatagram synAckPacket = receiveAck();
		lastValidPacketReceived = synAckPacket;
		this.remotePort = synAckPacket.getSrc_port();
		sendAck(synAckPacket, false);
		state = State.ESTABLISHED;
	}

	/**
	 * Listen for, and accept, incoming connections.
	 * 
	 * @return A new ConnectionImpl-object representing the new connection.
	 * @see Connection#accept()
	 */
	public Connection accept() throws IOException, SocketTimeoutException {
//		Listen for anyone trying to connect to the server
		state = State.LISTEN;
		KtnDatagram synPacket = null;
		synPacket = null;
		while (synPacket == null || synPacket.getFlag() != Flag.SYN) {
			synPacket = receivePacket(true);
		}
//		Establish a new connection with a new port number and return it to the client
		ConnectionImpl newConn = new ConnectionImpl(getPort());
		newConn.lastValidPacketReceived = synPacket;
		newConn.remoteAddress = synPacket.getSrc_addr();
		newConn.remotePort = synPacket.getSrc_port();
		newConn.state = State.SYN_RCVD;
		newConn.sendAck(synPacket, true);
		KtnDatagram ackPacket = null;
		while (ackPacket == null || ackPacket.getFlag() != Flag.ACK)
			ackPacket = newConn.receiveAck();
		newConn.lastValidPacketReceived = ackPacket;
		newConn.state = State.ESTABLISHED;
		return newConn;
	}

	private int getPort() {
//		Returns a available port to the client
		int i = 33333;
		while (usedPorts.containsKey(i)) 
			i++;
		return i;
	}

	/**
	 * Send a message from the application.
	 * 
	 * @param msg
	 *            - the String to be sent.
	 * @throws ConnectException
	 *             If no connection exists.
	 * @throws IOException
	 *             If no ACK was received.
	 * @see AbstractConnection#sendDataPacketWithRetransmit(KtnDatagram)
	 * @see no.ntnu.fp.net.co.Connection#send(String)
	 */
	public void send(String msg) throws ConnectException, IOException {
		KtnDatagram packet = constructDataPacket(msg);
		KtnDatagram ackPacket = null;
		while (!isValid(ackPacket)/*ackPacket == null || ackPacket.getFlag() != Flag.ACK/**/) {
			ackPacket = sendDataPacketWithRetransmit(packet);
		}
		lastValidPacketReceived = ackPacket;
	}

	/**
	 * Wait for incoming data.
	 * 
	 * @return The received data's payload as a String.
	 * @see Connection#receive()
	 * @see AbstractConnection#receivePacket(boolean)
	 * @see AbstractConnection#sendAck(KtnDatagram, boolean)
	 */
	public String receive() throws ConnectException, IOException {
		if (state != State.ESTABLISHED)
			throw new ConnectException("Can't receive in an un-established connection");
//		Recieve a packet until it's valid, then send an ACK and return the data to the serverapplication
		KtnDatagram receivedPacket = null;
		while (receivedPacket == null) {
			receivedPacket = receivePacket(false);
			if (!isValid(receivedPacket))
				receivedPacket = null;
		}
		sendAck(receivedPacket, false);
		return receivedPacket.getPayload().toString();
	}

	/**
	 * Close the connection.
	 * 
	 * @see Connection#close()
	 */
	public void close() throws IOException {
		if (state != State.ESTABLISHED)
			throw new IOException("Can't close an un-established connection");
		KtnDatagram ackPacket = null;
		KtnDatagram fin = constructInternalPacket(Flag.FIN);
		if (disconnectRequest == null) {
//			The host initiates the closing of the connection
			state = State.FIN_WAIT_1;
			while (ackPacket == null || ackPacket.getFlag() != Flag.ACK) {
				try {
					simplySendPacket(fin);
				} catch (ClException e1) {
					e1.printStackTrace();
				}
				ackPacket = receiveAck();
			}
			state = State.FIN_WAIT_2;
//			FIN sent, now waiting for a confirmation from the other host
			fin = null;
			while(fin == null) {
				fin = receivePacket(true);
				if(fin != null && fin.getFlag() == Flag.FIN) 
					sendAck(fin, false);
				else
					fin = null;
			}
		} else {
//			EOFException caught and host is now responding on the other host's close
			sendAck(disconnectRequest, false);
			state = State.FIN_WAIT_2;
			while(ackPacket == null || ackPacket.getFlag() != Flag.ACK/*ackPacket.getSeq_nr() != fin.getSeq_nr()*/) {
				try {
					simplySendPacket(fin);
				} catch (ClException e) {
					e.printStackTrace();
				}
				ackPacket = receiveAck();
			}
//			FIN sent and ACK received
		}
		state = State.CLOSED;
	}

	/**
	 * Test a packet for transmission errors. This function should only called
	 * with data or ACK packets in the ESTABLISHED state.
	 * 
	 * @param packet
	 *            Packet to test.
	 * @return true if packet is free of errors, false otherwise.
	 */
	protected boolean isValid(KtnDatagram packet) {
		return packet != null && packet.getChecksum() == packet.calculateChecksum();
	}

}
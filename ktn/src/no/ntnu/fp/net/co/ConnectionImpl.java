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
		fixAddressAndPort(syn);
		try {
			simplySendPacket(syn);
		} catch (ClException e) {
			System.out.println(DEBUG_CONNECT + "send SYN failed");
			e.printStackTrace();
		}
		state = State.SYN_SENT;
		KtnDatagram ackPacket = receiveAck();
		if(ackPacket != null && ackPacket.getFlag() != Flag.SYN_ACK)
			lastValidPacketReceived = ackPacket;
		
		if (isValid(ackPacket) && ackPacket.getFlag() != Flag.SYN_ACK)
			throw new ConnectException("Never received SYN_ACK");

		KtnDatagram ack = new KtnDatagram();
		fixAddressAndPort(ack);
		sendAck(ack, false);
		state = State.ESTABLISHED;
		System.out
				.println(DEBUG_CONNECT + "connection established with server");
	}

	/**
	 * Listen for, and accept, incoming connections.
	 * 
	 * @return A new ConnectionImpl-object representing the new connection.
	 * @see Connection#accept()
	 */
	public Connection accept() throws IOException, SocketTimeoutException {
		state = State.LISTEN;
		KtnDatagram synPacket = null;
		synPacket = receivePacket(true);
		// while(synPacket == null) {
		// System.out.println(DEBUG_ACCEPT+"server trying to receive SYN");
		// synPacket = receivePacket(true);
		// }
		state = State.SYN_RCVD;
		System.out.println(DEBUG_ACCEPT + "SYN received");
		this.remoteAddress = synPacket.getSrc_addr();
		this.remotePort = synPacket.getSrc_port();
		if (synPacket.getFlag() == Flag.SYN) {
			KtnDatagram packet = new KtnDatagram();
			fixAddressAndPort(packet);
			sendAck(packet, true);
			System.out.println(DEBUG_ACCEPT + "trying to receive ACK");
			KtnDatagram ackPacket = receiveAck();
			if (ackPacket != null) {
				state = State.ESTABLISHED;
				System.out.println(DEBUG_ACCEPT
						+ "connection established at server");
				return this;
			}
		}
		return null;
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
		fixAddressAndPort(packet);
		KtnDatagram ackPacket = sendDataPacketWithRetransmit(packet);
		if (ackPacket == null || ackPacket.getFlag() != Flag.ACK)
			throw new ConnectException(
					"Never received ACK at client after sending a datapacket");
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
			throw new ConnectException("Connection is not established");
		KtnDatagram receivedPacket = receivePacket(false);
		// KtnDatagram receivedPacket = receivePacket(false);
		KtnDatagram ack = new KtnDatagram();
		fixAddressAndPort(ack);
		sendAck(ack, false);
		System.out.println(DEBUG_RECEIVE + "packet received, flag: "
				+ receivedPacket.getFlag());

		if (isValid(receivedPacket) && receivedPacket.getFlag() == Flag.FIN) {
			// close();

			KtnDatagram fin = constructInternalPacket(Flag.FIN);
			fixAddressAndPort(fin);
			try {
				simplySendPacket(fin);
			} catch (ClException e1) {
				System.out.println(DEBUG_RECEIVE + "sending of FIN failed");
				e1.printStackTrace();
			}
			KtnDatagram ackPacket = receiveAck();
			if (isValid(ackPacket) && ackPacket.getFlag() == Flag.ACK) {
				throw new EOFException("Connection closed");
			}
			// KtnDatagram finPacket = constructInternalPacket(Flag.FIN);
			// fixAddressAndPort(finPacket);
			// try {
			// simplySendPacket(finPacket);
			// } catch (ClException e) {
			// e.printStackTrace();
			// }
			// KtnDatagram ackPacket = receiveAck();
			// if(isValid(ackPacket) && ackPacket.getFlag() == Flag.ACK) {
			// System.out.println(DEBUG_RECEIVE+"FIN received, connection closed");
			// state = State.CLOSED;
			// } else {
			// throw new
			// ConnectException("Never receieved ACK after sending FIN");
			// }
		} else {
			if (isValid(receivedPacket) && receivedPacket.getPayload() != null) {
				return receivedPacket.getPayload().toString();
			}
			System.out.println(DEBUG_RECEIVE + "reached end of receive");
			return null;
		}
		throw new EOFException();
	}

	/**
	 * Close the connection.
	 * 
	 * @see Connection#close()
	 */
	public void close() throws IOException {
		KtnDatagram fin = constructInternalPacket(Flag.FIN);
		fixAddressAndPort(fin);
		try {
			simplySendPacket(fin);
		} catch (ClException e1) {
			System.out.println(DEBUG_CLOSE + "sending of FIN failed");
			e1.printStackTrace();
		}
		KtnDatagram ackPacket = receiveAck();
		if (isValid(ackPacket) && ackPacket.getFlag() == Flag.ACK) {
			System.out.println(DEBUG_CLOSE + "received ack after sending FIN!");
			KtnDatagram finPacket = receivePacket(true);
			if (isValid(finPacket) && finPacket.getFlag() == Flag.FIN) {
				KtnDatagram ackRespond = new KtnDatagram();
				fixAddressAndPort(ackRespond);
				sendAck(ackRespond, false);
				System.out.println(DEBUG_CLOSE + "FIN received, ACK sent");
			}
		} else {
			throw new ConnectException("never received ACK after sending FIN");
		}
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
		boolean valid = true;
		
		if (packet == null)
			valid = false;
		else if (packet.getChecksum() != packet.calculateChecksum())
			valid = false;
		else if (packet.getSeq_nr() != lastValidPacketReceived.getSeq_nr() + 1)
			valid = false;
		else if (!packet.getSrc_addr().equals(remoteAddress))
			valid = false;
		else if (packet.getSrc_port() != remotePort)
			valid = false;
		else if (!(packet.getFlag() == Flag.ACK || packet.getFlag() == Flag.NONE))
			valid = false;
		
		if(valid) 
			lastValidPacketReceived = packet;
		System.out.println("Packet isValid = " + valid);
		return valid;
	}

	private KtnDatagram getEmptyPacketWidthAdressAndPort() {
		KtnDatagram packet = new KtnDatagram();
		fixAddressAndPort(packet);
		return packet;
	}

	private void fixAddressAndPort(KtnDatagram packet) {
		packet.setDest_addr(remoteAddress);
		packet.setDest_port(remotePort);
		packet.setSrc_addr(myAddress);
		packet.setSrc_port(myPort);
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("ConnectionImpl, instance: " + getClass().getName() + "@"
				+ Integer.toHexString(hashCode()));
		b.append("\naddress: " + this.myAddress);
		b.append(", port: " + this.myPort);
		b.append("\nremoteAdress: " + this.remoteAddress);
		b.append(", remotePort: " + this.remotePort);
		return b.toString();
	}
}

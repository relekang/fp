package no.ntnu.fp.server;


import no.ntnu.fp.common.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection {

    ServerSocket server;
    BufferedReader in;
    InetAddress ip;
    int port;
    public ServerConnection() throws IOException {
        server = new ServerSocket(Constants.SERVER_PORT);
        ip = InetAddress.getLocalHost();
    }

    public void send(String data){
        try {
            System.out.println("Print: " + ip);
            Socket socket = new Socket(ip.getHostAddress(), Constants.CLIENT_PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("\n----------\nSending string: '" + data + "'\nIP: " + ip +":"+ port);
            out.print(data);
            out.close();
            socket.close();
        }
        catch(Exception e) {
            System.out.println("Exception in send:\n");
            e.printStackTrace();
        }
    }

    public String receive(){
        String message = "";
        try {
            Socket socket = server.accept();
            ip = socket.getInetAddress();
            port = socket.getPort();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (!in.ready()) {}
            message = in.readLine();
            in.close();
            socket.close();
            System.out.println("Received string: '" + message + "'\nFrom: " + ip + ":" + port);
        }
        catch(Exception e) { System.out.print("Exception in receive:\n" + e.toString()); }
        if(message != null)
            return message;
        return "";
    }
}

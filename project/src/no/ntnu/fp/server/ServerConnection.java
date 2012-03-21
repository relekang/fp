package no.ntnu.fp.server;


import no.ntnu.fp.common.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection {

    ServerSocket server;
    BufferedReader in;

    public ServerConnection() throws IOException {
        server = new ServerSocket(Constants.SERVER_PORT);
    }

    public static void send(String data){
        System.out.print(data);
        try {
            Socket socket = new Socket("localhost", Constants.CLIENT_PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            System.out.print("Sending string: '" + data + "'\n");
            out.print(data);
            out.close();
            socket.close();
        }
        catch(Exception e) {
            System.out.print(e.toString());
        }
    }

    public String receive(){
        String message = "";
        try {
            Socket socket = server.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.print("Received string: '");

            while (!in.ready()) {}
            message = in.readLine();
            System.out.println(message);

            System.out.print("'\n");
            in.close();
        }
        catch(Exception e) { System.out.print(e.toString()); }
        if(message != null)
            return message;
        return "";
    }
}

package no.ntnu.fp.client;

import no.ntnu.fp.common.Constants;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection {
    ServerSocket server;
    BufferedReader in;

    public Connection() throws IOException {
        server = new ServerSocket(Constants.CLIENT_PORT);
    }

    public static void send(JSONObject object){
        System.out.print(object.toString());
        try {
            Socket socket = new Socket("localhost", Constants.SERVER_PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Sending string: '" + object.toString() + "'\n");
            out.print(object.toString());
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
            socket.close();
        }
        catch(Exception e) { System.out.print(e.toString()); }
        if(message != null)
            return message;
        return "";
    }

    public void close() throws IOException {
        server.close();
    }
}

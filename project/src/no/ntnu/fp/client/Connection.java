package no.ntnu.fp.client;

import no.ntnu.fp.common.Constants;
import no.ntnu.fp.common.model.Event;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.events.EventException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Connection {
    ServerSocket server;
    BufferedReader in;

    public Connection() throws IOException {
        server = new ServerSocket(Constants.CLIENT_PORT);
    }

    public void send(JSONObject object) {
        try {
            Socket socket = new Socket("lkng.me", Constants.SERVER_PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Sending string: '" + object.toString() + "'\n");
            out.print(object.toString());
            out.close();
            socket.close();
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public String receive() {
        String message = "";
        try {
            Socket socket = server.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.print("Received string: '");

            while (!in.ready()) {
            }
            message = in.readLine();
            System.out.println(message);

            System.out.print("'\n");
            in.close();
            socket.close();
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        if (message != null)
            return message;
        return "";
    }

    public void close() throws IOException {
        server.close();
    }


    public static Event fetchEvent(int id) {
        try {
            Connection conn = new Connection();
            JSONObject message = new JSONObject();
            message.put("key", "event");
            message.put("action", "get");
            message.put("argument", "id = " + id);
            conn.send(message);
            String ack = conn.receive();
            JSONObject object = new JSONObject(ack);
            System.out.println(object.toString());
            return new Event(object);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Event> fetchEvents() {
        ArrayList<Event> list = new ArrayList<Event>();
        try {
            Connection conn = new Connection();
            JSONObject message = new JSONObject();
            message.put("key", "event");
            message.put("action", "all");
            conn.send(message);
            String ack = conn.receive();
            JSONArray jsonArray = new JSONArray(ack);
            for(int i = 0; i<jsonArray.length();i++){
                JSONObject object =  jsonArray.optJSONObject(i);
                Event e = new Event(object);
                list.add(e);
            }
            System.out.println(list.toString());
            return list;


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

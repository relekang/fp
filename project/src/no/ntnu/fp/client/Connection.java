package no.ntnu.fp.client;

import no.ntnu.fp.common.Constants;
import no.ntnu.fp.common.Util;
import no.ntnu.fp.common.model.Day;
import no.ntnu.fp.common.model.Employee;
import no.ntnu.fp.common.model.Event;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.events.EventException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Connection {
    ServerSocket server;
    BufferedReader in;

    public Connection() throws IOException {
//        try{
        server = new ServerSocket(Constants.CLIENT_PORT);
//        } catch (Exception e){
//            //Silence is gold
//        }
    }

    public void send(JSONObject object) {
        try {
            Socket socket = new Socket(Constants.getServerUrl(), Constants.SERVER_PORT);
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Util.print("Sending string: '" + object.toString() + "'\n");
                out.print(object.toString());
                out.close();
                socket.close();
            } catch (SocketException e) {
                Util.print(e.toString());
                socket.close();

            } catch (UnknownHostException e) {
                e.printStackTrace();
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Util.print("Sent");
    }

    public String receive() throws SocketTimeoutException {
        String message = "";
        try {
            Socket socket = server.accept();
            try {
                socket.setSoTimeout(100);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Util.print("Received string: '");

                while (!in.ready()) {
                }
                message = in.readLine();
                Util.print(message);

                Util.print("'\n");
                in.close();
                socket.close();
            } catch (SocketException e) {
                e.printStackTrace();
                socket.close();
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
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
            Util.print(object.toString());
            conn.close();
            return new Event(object, false);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Event> fetchEvents(String arg) {
        ArrayList<Event> list = new ArrayList<Event>();
        try {
            Connection conn = new Connection();
            JSONObject message = new JSONObject();
            message.put("key", "event");
            message.put("action", "all");
            conn.send(message);
            String ack = conn.receive();
            JSONArray jsonArray = new JSONArray(ack);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.optJSONObject(i);
                Event e = new Event(object, false);
                list.add(e);
            }
            Util.print(list.toString());
            conn.close();
            return list;


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Day fetchDayEvents(Date date, Employee employee){
        String argument = "employee_id = %d and date_from contains '%s'";
        String.format(argument, employee.getId(), Util.dateToString(date));
        return new Day(fetchEvents(argument), date);
    }
}

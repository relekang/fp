package no.ntnu.fp.server;

import no.ntnu.fp.common.model.*;
import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.server.storage.db.EmployeeHandler;
import no.ntnu.fp.server.storage.db.EventHandler;
import no.ntnu.fp.server.storage.db.NotificationHandler;
import no.ntnu.fp.server.storage.db.RoomHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerApplication {
    static JFrame frame;
    static JPanel eventListPanel, roomListPanel;
    static GridBagConstraints gbc;

    public static void main(String args[]) {
        String message = "";
        ServerConnection conn = null;
        try {
            conn = new ServerConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                System.out.println("\n--------------------------------------------------------------------------------");
                message = conn.receive();
                try {
                    JSONObject object = new JSONObject(message);

                    System.out.println("\n\n Recieved JSON-object: \n" + object.toString() + "\n");

                    if (object.getString("key").equals("authenticate")) {
                        Employee employee = null;
                        try {
                            employee = ServerAuthentication.authenticate(object.get("username").toString(), object.get("password").toString());
                        } catch (SQLException e) {
                            conn.send(new JSONObject().put("key", "failure").toString());
                        }
                        if (employee != null)
                            conn.send(new JSONObject().put("key", "success").put("employee", employee.toJson()).toString());
                        else
                            conn.send(new JSONObject().put("key", "failure").toString());
                    } else if (object.getString("key").equals("event")) {
                        try {
                            handleEventRequest(object, conn);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else if (object.getString("key").equals("notification")) {
                        try {
                            handleNotificationRequest(object, conn);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else if (object.getString("key").equals("room")) {
                        try {
                            handleRoomRequest(object, conn);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else if (object.getString("key").equals("employees")) {
                        try {
                            handleEmployeeRequest(object, conn);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        conn.send(new JSONObject().put("key", "failure").toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                try {
                    e.printStackTrace();
                    conn.send(new JSONObject().put("key", "failure").toString());
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private static void handleEmployeeRequest(JSONObject object, ServerConnection conn) throws JSONException, SQLException {
        EmployeeHandler employeeHandler = new EmployeeHandler();
        String action = object.getString("action");
        if(action.equals("all")){
            ArrayList<Employee> list = employeeHandler.fetchAllEmployees();
            ArrayList<JSONObject> jsonList = new ArrayList<JSONObject>();
            for(Employee e:list){
                jsonList.add(e.toJson());
            }
            String message = new JSONArray(jsonList).toString();
            conn.send(message);
        } else {
            conn.send(new JSONObject().put("key", "failure").toString());
        }
    }

    private static void handleEventRequest(JSONObject object, ServerConnection conn) throws JSONException, SQLException {
        EventHandler eventHandler = new EventHandler();
        String action = object.getString("action");
        if(action.equals("all")){
            ArrayList<Event> list = eventHandler.fetchAllEvents();
            ArrayList<JSONObject> jsonList = new ArrayList<JSONObject>();
            for(Event e:list){
                jsonList.add(e.toJson());
            }
            String message = new JSONArray(jsonList).toString();
            conn.send(message);
        }
        else if(action.equals("get")){

                Event event =  eventHandler.fetchEvent(object.getString("argument"));
                conn.send(event.toJson().toString());
        }
        else if(action.equals("save")){

            Event event = new Event(object.getJSONObject("event"), true);
            if(eventHandler.updateEvent(event) != null) {
                eventHandler.updateParticipants(event);
                conn.send(new JSONObject().put("key", "success").toString());
            }
            else
                conn.send(new JSONObject().put("key", "failure").toString());

        }
        else if(action.equals("create")){

            Event event = new Event(object.getJSONObject("event"), true);
            int id = eventHandler.createEvent(event);
            if(id != 0){
                eventHandler.addParticipants(event, id);
                conn.send(new JSONObject().put("key", "success").toString());
            }
            else
                conn.send(new JSONObject().put("key", "failure").toString());

        }
        else if(action.equals("all_for_user")){
            String arg = object.getString("argument");
            ArrayList<Event> list = eventHandler.fetchEventsForUser(arg);
            ArrayList<JSONObject> jsonList = new ArrayList<JSONObject>();
            for(Event e:list){
                jsonList.add(e.toJson());
            }
            String message = new JSONArray(jsonList).toString();
            conn.send(message);
        } else {
            conn.send(new JSONObject().put("key", "failure").toString());
        }


    }
    private static void handleNotificationRequest(JSONObject object, ServerConnection conn) throws JSONException, SQLException {
        NotificationHandler notificationHandler = new NotificationHandler();
        String action = object.getString("action");
        if(action.equals("all_for_user")){
            String arg = object.getString("argument");
            ArrayList<Notification> list = notificationHandler.fetchNotificationsForUser(arg);
            ArrayList<JSONObject> jsonList = new ArrayList<JSONObject>();
            for(Notification n:list){
                jsonList.add(n.toJson());
                System.out.println();
            }
            String message = new JSONArray(jsonList).toString();
            conn.send(message);
        } else {
            conn.send(new JSONObject().put("key", "failure").toString());
        }
    }

    private static void handleRoomRequest(JSONObject object, ServerConnection conn) throws JSONException, SQLException {
        RoomHandler roomHandler = new RoomHandler();
        String action = object.getString("action");
        if(action.equals("get")){
            Room room = roomHandler.fetchRoom(object.getString("argument"));
            JSONObject message = new JSONObject();
            message.put("key", "success");
            message.put("room", room.toJson());

        } else {
            conn.send(new JSONObject().put("key", "failure").toString());
        }
    }

}

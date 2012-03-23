package no.ntnu.fp.server;

import no.ntnu.fp.common.model.*;
import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.server.gui.EventListPanel;
import no.ntnu.fp.server.gui.RoomListPanel;
import no.ntnu.fp.server.storage.db.EventHandler;
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
            message = conn.receive();
            try {
                JSONObject object = new JSONObject(message);

                System.out.println("\n\n Recieved JSON-object: \n" + object.toString());

                if (object.getString("key").equals("authenticate")) {
                    Employee employee = null;
                    try {
                        employee = ServerAuthentication.authenticate(object.get("username").toString(), object.get("password").toString());
                    } catch (SQLException e) { conn.send(new JSONObject().put("key", "failure").toString()); }
                    if (employee != null)
                        conn.send(new JSONObject().put("key", "success").put("employee", employee.toJson()).toString());
                    else
                        conn.send(new JSONObject().put("key", "failure").toString());
                } else
                if (object.getString("key").equals("event")){
                    try {
                        handleEventRequest(object, conn);
                    } catch (SQLException e) { e.printStackTrace(); }
                }

            } catch (JSONException e) { e.printStackTrace(); }

        }
    }

    private static void handleEventRequest(JSONObject object, ServerConnection conn) throws JSONException, SQLException {
        EventHandler eventHandler = new EventHandler();
        if(object.getString("action").equals("all")){
            ArrayList<Event> list = eventHandler.fetchAllEvents();
            ArrayList<JSONObject> jsonList = new ArrayList<JSONObject>();
            for(Event e:list){
                jsonList.add(e.toJson());
            }
            String message = new JSONArray(jsonList).toString();
            conn.send(message);
        }
        else if(object.getString("action").equals("get")){

                Event event =  eventHandler.fetchEvent(object.getString("argument"));
                conn.send(event.toJson().toString());
        }
    }


    private static void gui() {
        gbc = new GridBagConstraints();
        frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        eventListPanel = new EventListPanel();
        roomListPanel = new RoomListPanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(eventListPanel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(roomListPanel, gbc);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

}

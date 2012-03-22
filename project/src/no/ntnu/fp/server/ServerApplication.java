package no.ntnu.fp.server;

import no.ntnu.fp.model.Employee;
import no.ntnu.fp.server.gui.EventListPanel;
import no.ntnu.fp.server.gui.RoomListPanel;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

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

        while (1 == 1) {
            message = conn.receive();
            try {
                JSONObject object = new JSONObject(message);

                System.out.println("\n\n Recieved JSON-object: \n" + object.toString());

                if (object.get("key").toString().equals("authenticate")) {
                    Employee employee = null;
                    try {
                        employee = ServerAuthentication.authenticate(object.get("username").toString(), object.get("password").toString());
                    } catch (SQLException e) { conn.send(new JSONObject().put("key", "failure").toString()); }
                    if (employee != null)
                        conn.send(new JSONObject().put("key", "success").put("employee", employee.toJson()).toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

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

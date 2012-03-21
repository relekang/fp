package no.ntnu.fp.server;

import no.ntnu.fp.model.Employee;
import no.ntnu.fp.server.gui.EventListPanel;
import no.ntnu.fp.server.gui.RoomListPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class ServerApplication {
    static JFrame frame;
    static JPanel eventListPanel, roomListPanel;
    static GridBagConstraints gbc;

    public static void main (String args[]){
        String message = "";
        ServerConnection conn = null;
        try {
            conn = new ServerConnection();
        } catch (IOException e) { e.printStackTrace(); }

        while (1==1){
            message = conn.receive();
            System.out.println(message.split("-")[0]);
            if(message.split("-")[0].equals("authenticate")){
                Employee employee = null;
                System.out.println("Trying to auth " + message.split("-")[1] + message.split("-")[2]);
                try {
                    employee = ServerAuthentication.authenticate(message.split("-")[1], message.split("-")[2]);
                } catch (SQLException e) {
                    ServerConnection.send("failure");
                }
                if(employee != null) ServerConnection.send("success");

            }
        }
    }

    private static void gui(){
        gbc = new GridBagConstraints();
        frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        eventListPanel = new EventListPanel();
        roomListPanel = new RoomListPanel();
        gbc.gridx = 0; gbc.gridy = 0;
        frame.add(eventListPanel, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        frame.add(roomListPanel, gbc);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

}

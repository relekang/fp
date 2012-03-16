package no.ntnu.fp.server;

import no.ntnu.fp.gui.EventView;
import no.ntnu.fp.gui.FindPersonView;
import no.ntnu.fp.gui.MainView;
import no.ntnu.fp.server.gui.EventListPanel;
import no.ntnu.fp.server.gui.RoomListPanel;

import javax.swing.*;
import java.awt.*;

public class ServerApplication {
    static JFrame frame;
    static JPanel eventListPanel, roomListPanel;
    static GridBagConstraints gbc;

    public static void main (String args[]){

        gui();
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

package no.ntnu.fp.server;

import no.ntnu.fp.gui.EventView;
import no.ntnu.fp.gui.FindPersonView;
import no.ntnu.fp.gui.MainView;
import no.ntnu.fp.server.gui.EventListPanel;

import javax.swing.*;

public class ServerApplication {
    static JFrame frame;
    static JPanel eventListPanel;


    public static void main (String args[]){
        gui();
    }

    private static void gui(){
        frame = new JFrame("Events");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        eventListPanel = new EventListPanel();
        frame.add(eventListPanel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

}

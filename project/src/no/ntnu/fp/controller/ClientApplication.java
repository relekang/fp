package no.ntnu.fp.controller;

import no.ntnu.fp.gui.EventView;
import no.ntnu.fp.gui.MainView;

import javax.swing.*;

public class ClientApplication {
    private static JFrame mainFrame, eventFrame;
    public static void main (String args[]){
        mainFrame = new MainView();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        eventFrame = new EventView();
        eventFrame.setLocationRelativeTo(null);
        eventFrame.setVisible(false);
    }

    public static void setMainFrameVisible(boolean visibility){
        mainFrame.setVisible(visibility);
    }

    public static void setEventFrameVisible(boolean visibility){
        eventFrame.setVisible(visibility);
    }

}

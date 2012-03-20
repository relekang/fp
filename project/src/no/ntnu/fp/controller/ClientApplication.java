package no.ntnu.fp.controller;

import no.ntnu.fp.gui.EventView;
import no.ntnu.fp.gui.MainView;
import no.ntnu.fp.gui.FindPersonView;

import javax.swing.*;

public class ClientApplication {
    private static JFrame mainFrame, eventFrame, findPersonFrame;
    public static void main (String args[]){

    	mainFrame = new MainView();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        eventFrame = new EventView();
        eventFrame.setLocationRelativeTo(null);
        eventFrame.setVisible(false);

        findPersonFrame = new FindPersonView();
        findPersonFrame.setLocationRelativeTo(null);
        findPersonFrame.setVisible(false);



    }

    public static void pack(){
        if(mainFrame.isVisible()) mainFrame.pack();
        if(eventFrame.isVisible()) eventFrame.pack();
        if(findPersonFrame.isVisible()) findPersonFrame.pack();
    }

    public static void setMainFrameVisible(boolean visibility){
        mainFrame.setVisible(visibility);
    }

    public static void setEventFrameVisible(boolean visibility){
        eventFrame.setVisible(visibility);
    }
    public static void setFindPersonFrameVisible(boolean visibility){
        findPersonFrame.setVisible(visibility);
    }

}

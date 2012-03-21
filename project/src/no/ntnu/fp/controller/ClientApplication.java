package no.ntnu.fp.controller;

import java.util.Date;

import no.ntnu.fp.client.Connection;
import no.ntnu.fp.gui.EventView;
import no.ntnu.fp.gui.MainView;
import no.ntnu.fp.gui.FindPersonView;
import no.ntnu.fp.model.Employee;
import no.ntnu.fp.model.Event;

import javax.swing.*;

public class ClientApplication {
    private static JFrame mainFrame, eventFrame, findPersonFrame;
    
    private static EventViewController eventViewController = new EventViewController();
    private static MainViewController mainViewController;
    private static FindPersonViewController findPersonViewController = new FindPersonViewController();
    
    
    public static void showEventView(){
    	eventViewController.setEventVisible(true);
    }
    
    public static void main (String args[]){

    	mainFrame = new MainView();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        
        mainViewController = new MainViewController((MainView)mainFrame);
        eventViewController = new EventViewController();
        
        
        findPersonFrame = new FindPersonView();
        findPersonFrame.setLocationRelativeTo(null);
        findPersonFrame.setVisible(false);

//        pack();
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

    public static void setCurrentUser(Employee employee) {
        eventViewController.setCurrentUser(employee);
        findPersonViewController.setCurrentUser(employee);
        mainViewController.setCurrentUser(employee);
    }
    
    public static MainViewController getMainViewController() {
    	return mainViewController;
    }
}

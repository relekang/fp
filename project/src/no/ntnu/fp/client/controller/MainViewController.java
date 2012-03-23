package no.ntnu.fp.client.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import no.ntnu.fp.client.Connection;
import no.ntnu.fp.client.gui.CalendarPanel;
import no.ntnu.fp.client.gui.MainView;
import no.ntnu.fp.client.gui.OverviewCalendarPanel;
import no.ntnu.fp.common.model.Employee;
import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.common.model.Notification;
import no.ntnu.fp.server.storage.db.EventHandler;

import javax.swing.*;

public class MainViewController implements PropertyChangeListener {

    private MainView mainView;
    private Employee currentUser;
    private ArrayList<Event> events;
    private ArrayList<Notification> notifications;
    private DefaultListModel weekModel;

    public MainViewController(MainView view) {
    	events = new ArrayList<Event>();
    	weekModel = new DefaultListModel();
        mainView = view;
        mainView.setCalendarModel(weekModel);
        
        organizePropertyChangeListeners();
        loadUserEvents();
        
        events.add(Event.getDummyEvent("En"));
        events.add(Event.getDummyEvent("To"));
        events.add(Event.getDummyEvent("Tre"));
        events.add(Event.getDummyEvent("Fire"));
        events.add(Event.getDummyEvent("Fem"));
        events.add(Event.getDummyEvent("Seks"));
        events.add(Event.getDummyEvent("Syv"));
    }

    private void organizePropertyChangeListeners() {
    	CalendarPanel c = mainView.getCalendarPanel();
    	OverviewCalendarPanel o = mainView.getOverviewCalendarPanel(); 
		o.addPCL(c);
	}
    
	public void setCurrentUser(Employee currentUser) {
        this.currentUser = currentUser;
        currentUser.addPropertyChangeListener(this);
        mainView.getUserLabel().setText("Signed in as " + currentUser.getName());
        loadUserEvents();
        loadUserNotifications();
    }

    public void loadUserEvents() {
//    	if(currentUser.getName().equals("Bernt Arne")) {
//    		events = new ArrayList<Event>();
//    		events.add(Event.getDummyEvent("Fisketur"));
//    		events.add(Event.getDummyEvent("Meeting with kingkong"));
//    		mainView.getCalendarPanel().addEvents(events);
//    	}
        if (currentUser != null) {
            events = getCurrentUser().getEvents(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
//            System.out.println("EVENT:" + events.get(0));
            mainView.getCalendarPanel().addEvents(events);
        }
    }

    public void loadUserNotifications() {
    	if(currentUser != null) {
    		notifications = currentUser.getAllNotifications();
    		for(Notification n : notifications) {
    			mainView.getNotificationPanel().addNotification(n);
    		}
    	}
    }

    public Employee getCurrentUser() {
        return currentUser;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("propertychange in MainViewController: " + evt);
    }

    public MainView getMainView() {
        return mainView;
    }

}

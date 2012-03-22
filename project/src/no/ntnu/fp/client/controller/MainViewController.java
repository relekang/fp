package no.ntnu.fp.client.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;

import no.ntnu.fp.client.Connection;
import no.ntnu.fp.client.gui.CalendarPanel;
import no.ntnu.fp.client.gui.MainView;
import no.ntnu.fp.client.gui.OverviewCalendarPanel;
import no.ntnu.fp.common.model.Employee;
import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.server.storage.db.EventHandler;

public class MainViewController implements PropertyChangeListener {

    private MainView mainView;
    private Employee currentUser;
    private ArrayList<Event> events;

    public MainViewController(MainView view) {
        mainView = view;
        organizePropertyChangeListeners();
    }

    private void organizePropertyChangeListeners() {
    	CalendarPanel c = mainView.getCalendarPanel();
    	OverviewCalendarPanel o = mainView.getOverviewCalendarPanel(); 
		o.addPCL(c);
	}

	public void setCurrentUser(Employee currentUser) {
        this.currentUser = currentUser;
        currentUser.addPropertyChangeListener(this);
        loadUserEvents();
        loadUserNotifications();
    }

    public void loadUserEvents() {
    	if(currentUser.getName().equals("Bernt Arne")) {
    		events = new ArrayList<Event>();
    		events.add(Event.getDummyEvent("Fisketur"));
    		events.add(Event.getDummyEvent("Meeting with kingkong"));
    		mainView.getCalendarPanel().addEvents(events);
    	}
//        if (currentUser != null) {
//            events = Connection.fetchEvents();
//            System.out.println("EVENT:" + events.get(0));
//            mainView.getCalendarPanel().addEvents(events);
//        }
    }

    public void loadUserNotifications() {
    	
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

package no.ntnu.fp.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import no.ntnu.fp.gui.MainView;
import no.ntnu.fp.model.Employee;
import no.ntnu.fp.model.Event;

public class MainViewController implements PropertyChangeListener {
	
	private MainView mainView;
	private Employee currentUser;
	private ArrayList<Event> events;
	
	public MainViewController(MainView view) {
		mainView = view;
	}
	
	public void setCurrentUser(Employee currentUser){
		this.currentUser = currentUser;
		currentUser.addPropertyChangeListener(this);
		loadUserEvents();
		loadUserNotifications();
	}
	
	public void loadUserEvents() {
		events = currentUser.getRelatedEvents();
//		mainView.getCalendarPanel().addEvents(events);
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

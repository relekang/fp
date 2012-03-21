package no.ntnu.fp.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

import no.ntnu.fp.model.Employee;
import no.ntnu.fp.model.Event;
import no.ntnu.fp.storage.db.EventHandler;

public class EventViewController{
	
	public static void createEvent(String title){
		EventHandler eventHandler = new EventHandler();
		Employee admin = new Employee();
		Event event = new Event(title, admin);
		eventHandler.createEvent(event);
		
	}

	public Event getEvent (int ID){
		try {
			return EventHandler.getEvent(ID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	

}

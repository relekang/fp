package no.ntnu.fp.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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
	
	public static void saveEvent(String title, Date dateTo, Date dateFrom, 
								 String description, ArrayList<Employee> participants, Employee admin, int ID){
		Event event = new Event(ID, title, dateTo, dateFrom);
		EventHandler handler;
		try {
			handler = new EventHandler();
			handler.updateEvent(event);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setEventTitle(String title){
		
	}
	
	public void setEventDateFrom(Date dateFrom){
		
	}
	
	public void setEventDateTo(Date dateTo){
		
	}
	
	public void setDescription(String Description){
		
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

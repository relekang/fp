package no.ntnu.fp.client.controller;

import java.sql.SQLException;

import no.ntnu.fp.client.gui.EventView;
import no.ntnu.fp.common.model.Employee;
import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.server.storage.db.EventHandler;

public class EventViewController{
	
	private EventView view;
	private Employee currentUser;
	private Event event;
	
	public EventViewController(){
		view = new EventView();
		view.setVisible(false);
    }
		
	
	
	public void setCurrentUser(Employee currentUser){
		this.currentUser = currentUser;
	}
	
	public void showEvent(){
		
	}
	
	public void showEvent(Event event){
		this.event = event;
		view.setVisible(true);
		view.setTitle(event.getTitle());
		view.setFromDate(event.getDateFrom().toString());
		view.setToDate(event.getDateTo().toString());
	}
	
	public void setEventVisible(boolean visible){
		view.setVisible(visible);
	}
	
	public void setEvent(Event event){
		this.event = event;
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

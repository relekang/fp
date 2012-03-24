package no.ntnu.fp.client.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import no.ntnu.fp.common.model.Employee;

public class FindPersonViewController implements PropertyChangeListener {
	
	private Employee currentUser;
	
	public FindPersonViewController(Employee currentUser) {
		this.currentUser = currentUser;
	}
	
	public void setCurrentUser(Employee currentUser){
		this.currentUser = currentUser;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}


}

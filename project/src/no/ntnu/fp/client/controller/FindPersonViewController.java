package no.ntnu.fp.client.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import no.ntnu.fp.client.gui.FindPersonView;
import no.ntnu.fp.common.model.Employee;

public class FindPersonViewController implements PropertyChangeListener {
	
	private Employee currentUser;
	private FindPersonView findPersonView;
	
	public FindPersonViewController(Employee currentUser, FindPersonView view) {
		this.currentUser = currentUser;
		this.findPersonView = view;
		findPersonView.setLocationRelativeTo(ClientApplication.getMainViewController().getMainView());
	}
	
	public void setCurrentUser(Employee currentUser){
		this.currentUser = currentUser;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
	}

}

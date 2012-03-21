package no.ntnu.fp.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import no.ntnu.fp.gui.MainView;
import no.ntnu.fp.model.Employee;

public class MainViewController implements PropertyChangeListener {
	
//	public static final String 
	
	private MainView mainView;
	private Employee currentUser;
	
	public MainViewController(MainView view) {
		mainView = view;
	}
	
	public void setCurrentUser(Employee currentUser){
		this.currentUser = currentUser;
		currentUser.addPropertyChangeListener(this);
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

package no.ntnu.fp.controller;

import java.beans.PropertyChangeSupport;

import no.ntnu.fp.gui.MainView;
import no.ntnu.fp.model.Employee;

public class MainViewController extends PropertyChangeSupport {
	
	private MainView mainView;
	private Employee currentUser;
	
	public MainViewController(MainView view) {
		super(view);
		this.mainView = view;
	}
	
	public void setCurrentUser(Employee currentUser){
		this.currentUser = currentUser;
		System.out.println(currentUser);
	}
	
	public Employee getCurrentUser() {
		return currentUser;
	}

}

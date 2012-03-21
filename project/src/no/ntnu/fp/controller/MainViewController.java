package no.ntnu.fp.controller;

import no.ntnu.fp.gui.MainView;
import no.ntnu.fp.model.Employee;

public class MainViewController {
	
	private MainView mainView;
	private Employee currentUser;
	
//	public MainViewController(MainView view, Employee currentUser) {
//		this.manView = view;
//		this.currentUser = currentUser;
//	}
	
	public void setCurrentUser(Employee currentUser){
		this.currentUser = currentUser;
	}

}

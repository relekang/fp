package no.ntnu.fp.client.controller;

import no.ntnu.fp.client.gui.EventView;
import no.ntnu.fp.client.gui.Login;
import no.ntnu.fp.client.gui.MainView;
import no.ntnu.fp.client.gui.FindPersonView;
import no.ntnu.fp.common.model.Employee;

import javax.swing.*;

import java.util.ArrayList;

public class ClientApplication {
	private static MainView mainFrame;
	private static EventView eventFrame;
	private static FindPersonView findPersonFrame; 
	private static Login login;

	private static EventViewController eventViewController;
	private static MainViewController mainViewController;
	private static FindPersonViewController findPersonViewController;
	
	private static Employee currentUser;

	public static void main(String args[]) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		
		login = new Login();
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.setVisible(true);
		login.pack();
	}
	
	public static void setCurrentUser(Employee employee) {
		login.setVisible(false);
		currentUser = employee;
		mainFrame = new MainView();
		
		mainViewController = new MainViewController(currentUser, mainFrame);
		eventViewController = new EventViewController(currentUser);
		findPersonViewController = new FindPersonViewController(currentUser);
	}

	public static void showEventView() {
		eventViewController.setVisible(true);
	}

	public static void setMainFrameVisible(boolean visibility) {
		mainFrame.setVisible(visibility);
	}

	public static void setEventFrameVisible(boolean visibility) {
		eventFrame.setVisible(visibility);
	}

	public static void setFindPersonFrameVisible(boolean visibility) {
		findPersonFrame.setVisible(visibility);
	}

	public static MainViewController getMainViewController() {
		return mainViewController;
	}

	public static Employee getCurrentUser() {
		return currentUser;
	}
	
}

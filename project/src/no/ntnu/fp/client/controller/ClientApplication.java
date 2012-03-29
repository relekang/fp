package no.ntnu.fp.client.controller;

import no.ntnu.fp.client.Connection;
import no.ntnu.fp.client.gui.EventView;
import no.ntnu.fp.client.gui.FindPersonView;
import no.ntnu.fp.client.gui.GuiConstants;
import no.ntnu.fp.client.gui.LoginView;
import no.ntnu.fp.client.gui.MainView;
import no.ntnu.fp.common.model.Employee;

import javax.swing.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static sun.applet.AppletResourceLoader.getImage;

public class ClientApplication {
	private static MainView mainFrame;
	private static EventView eventFrame;
	private static FindPersonView findPersonFrame; 
	private static LoginView login;

	private static EventViewController eventViewController;
	private static MainViewController mainViewController;
	private static FindPersonViewController findPersonViewController;
	
	private static Employee currentUser;

	public static void main(String args[]) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		UIManager.put("Panel.background", GuiConstants.SWING_FRAME_GRAY);
		login = new LoginView();
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.setVisible(true);
		login.pack();
	}
	
	public static void setCurrentUser(Employee employee) {
		currentUser = employee;
	}
	
	public static void init() {
		login.setVisible(false);
		login = null;
		mainFrame = new MainView();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        eventFrame = new EventView();
		findPersonFrame = new FindPersonView();
		
		mainViewController = new MainViewController(currentUser, mainFrame);
		eventViewController = new EventViewController(currentUser, eventFrame);
		findPersonViewController = new FindPersonViewController(currentUser, findPersonFrame);
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
	
	public static EventViewController getEventViewController() {
		return eventViewController;
	}

	public static MainViewController getMainViewController() {
		return mainViewController;
	}

	public static Employee getCurrentUser() {
		return currentUser;
	}
	
	public static void logout() {
		try {
			Connection conn = new Connection();
			try {
				conn.send(new JSONObject().put("key", "logout"));
				conn.close();
			} catch(JSONException ex) {
				conn.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentUser = null;
		mainFrame.setVisible(false);
		mainFrame = null;
		eventFrame.setVisible(false);
		eventFrame = null;
		findPersonFrame.setVisible(false);
		findPersonFrame = null;
		
		login = new LoginView();
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.setVisible(true);
		login.pack();	
	}
	
}

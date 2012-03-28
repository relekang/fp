package no.ntnu.fp.client.controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import no.ntnu.fp.client.gui.CalendarPanel;
import no.ntnu.fp.client.gui.MainView;
import no.ntnu.fp.client.gui.OverviewCalendarPanel;
import no.ntnu.fp.common.Util;
import no.ntnu.fp.common.model.Day;
import no.ntnu.fp.common.model.Employee;
import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.common.model.Notification;

import javax.swing.*;

public class MainViewController implements PropertyChangeListener {

	private MainView mainView;
	private Employee currentUser;
	private ArrayList<Event> events;
	private ArrayList<Notification> notifications;
	private DefaultListModel weekModel;

	public MainViewController(Employee currentUser, MainView view) {
		this.mainView = view;
		this.currentUser = currentUser;
		mainView.setVisible(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mainView.setLocation(screenSize.width / 2 - (mainView.getWidth() / 2),
				screenSize.height / 2 - (mainView.getHeight() / 2));

		events = new ArrayList<Event>();
		weekModel = new DefaultListModel();
		mainView.setCalendarModel(weekModel);

		organizePropertyChangeListeners();
//		loadUserEvents(Util.getCalendar().get(Calendar.WEEK_OF_YEAR));
		setCurrentUser(currentUser);
	}

	private void organizePropertyChangeListeners() {
		CalendarPanel c = mainView.getCalendarPanel();
		OverviewCalendarPanel o = mainView.getOverviewCalendarPanel();
		o.addPCL(c);
		o.addPCL(this);
	}

	public void setMainView(MainView view) {
		mainView = view;
	}

	public void setCurrentUser(Employee currentUser) {
		this.currentUser = currentUser;
		currentUser.addPropertyChangeListener(this);
		mainView.getUserLabel()
				.setText("Signed in as " + currentUser.getName()+"lol");
		loadUserEvents(Util.getCalendar().get(Calendar.WEEK_OF_YEAR)-1);//TODO: works the first time when this is initialized 
		loadUserNotifications();
	}

	public void loadUserEvents(int week) {
		Util.print("Loading events for week: " + week);
		if (currentUser != null) {
			events = currentUser.getEvents(week);
			Collections.sort(events);
			Util.print("sorted: " + events);
			DefaultListModel weekModel = new DefaultListModel();
			for(int i = 0; i < 7; i++) {
				Calendar c = Util.getCalendar();
				c.set(Calendar.WEEK_OF_YEAR, week+1);
				c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY+i);
				weekModel.addElement(new Day(c.getTime()));
				Util.print("Day created in MainViewController: " + c.getTime());
			}
			Util.print(events);
			for(Event e : events) {
				Calendar c = Util.getCalendar();
				Util.print(c.getTime() );
				c.setTime(e.getDateFrom());
				if(c.get(Calendar.WEEK_OF_YEAR) == week) {
					int day = (c.get(Calendar.DAY_OF_WEEK)-2);//TODO: this (Calendar.DAY_OF_WEEK)-2 works for now
					if(day == -1)
						day = Calendar.SUNDAY;
					Util.print("Day in week: " + day);
					((Day)weekModel.get(day)).add(e);
				}
			}
			Util.print("weekmodel: " + weekModel);
			mainView.getCalendarPanel().setModel(weekModel);
		}
	}

	public void loadUserNotifications() {
		if (currentUser != null) {
			notifications = currentUser.getAllNotifications();
			for (Notification n : notifications) {
				mainView.getNotificationPanel().addNotification(n);
			}
		}
	}

	public Employee getCurrentUser() {
		return currentUser;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Util.print("propertychange in MainViewController: " + evt);
		String property = evt.getPropertyName();
		if (property.equals(OverviewCalendarPanel.SELECTED_DAY_CHANGED)) {
			loadUserEvents(((Calendar) evt.getNewValue()).get(Calendar.WEEK_OF_YEAR));
		}
	}

	public MainView getMainView() {
		return mainView;
	}

}

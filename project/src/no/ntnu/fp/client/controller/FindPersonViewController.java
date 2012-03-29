package no.ntnu.fp.client.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.ntnu.fp.client.gui.CalendarPanel;
import no.ntnu.fp.client.gui.FindPersonView;
import no.ntnu.fp.client.gui.OverviewCalendarPanel;
import no.ntnu.fp.common.Util;
import no.ntnu.fp.common.model.Day;
import no.ntnu.fp.common.model.Employee;
import no.ntnu.fp.common.model.Event;

public class FindPersonViewController implements PropertyChangeListener, ListSelectionListener {
	
	private Employee selectedUser;
	private FindPersonView findPersonView;
	private ArrayList<Event> events;
	
	public FindPersonViewController(Employee currentUser, FindPersonView view) {
		this.selectedUser = currentUser;
		this.findPersonView = view;
		findPersonView.setLocationRelativeTo(ClientApplication.getMainViewController().getMainView());
		organizeListeners();
	}
	
	private void organizeListeners() {
		findPersonView.getSearchField().addListSelectionListener(this);
		CalendarPanel c = findPersonView.getCalendarPanel();
		OverviewCalendarPanel o = findPersonView.getOverviewCalendarPanel();
		o.addPCL(c);
		o.addPCL(this);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		Util.print("EVENT: " + e);
		if(findPersonView.getSearchField().getSelectedValue() != null)
			setCurrentUser(findPersonView.getSearchField().getSelectedValue());
	}
	
	public void setCurrentUser(Employee selectedUser){
		this.selectedUser = selectedUser;
		findPersonView.getUserLabel().setText("Viewing " + selectedUser.getName() + "'s events");
		loadUserEvents(Util.getCalendar().get(Calendar.WEEK_OF_YEAR));//TODO: works the first time when this is initialized 
	}
	
	public void loadUserEvents(int week) {
		Util.print("Loading other users events for week: " + week);
		if (selectedUser != null) {
			events = selectedUser.getEvents(week);
			Collections.sort(events);
			Util.print("sorted: " + events);
			DefaultListModel weekModel = new DefaultListModel();
			for(int i = 0; i < 7; i++) {
				Calendar c = Util.getCalendar();
				c.set(Calendar.WEEK_OF_YEAR, week);//TODO:+1
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
			findPersonView.getCalendarPanel().setModel(weekModel);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Util.print("propertychange in FindViewController: " + evt);
		String property = evt.getPropertyName();
		if (property.equals(OverviewCalendarPanel.SELECTED_DAY_CHANGED)) {
			loadUserEvents(((Calendar) evt.getNewValue()).get(Calendar.WEEK_OF_YEAR));
		}
	}
}

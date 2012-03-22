package no.ntnu.fp.client.gui;

import no.ntnu.fp.client.gui.objects.CalendarDayBox;
import no.ntnu.fp.common.model.Employee;

import javax.swing.*;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;

import no.ntnu.fp.common.model.Event;

public class CalendarPanel extends JPanel implements PropertyChangeListener {

	private GridBagConstraints gbc = new GridBagConstraints();
	private JPanel weekPanel;
	private JScrollPane pane;
	private Employee user;
	private int currentWeek;
	private CalendarDayBox[] days;
	
	public CalendarPanel() {
		setLayout(new GridBagLayout());
		days = new CalendarDayBox[GuiConstants.DAYS.length];
		addCalendarHeaders();
		initWeekPanel();
	}
	
//	public CalendarPanel(Employee user) {
//		this.user = user;
//	}
	
	public void addEvents(ArrayList<Event> events) {
		for(Event e : events) {
			System.out.println(e);
		}
	}

	private void initWeekPanel() {
		weekPanel = new JPanel(new FlowLayout());
		weekPanel.setBackground(GuiConstants.STD_BACKGROUND);
		JPanel hourLabels = new JPanel();
		hourLabels.setLayout(new BoxLayout(hourLabels, BoxLayout.Y_AXIS));
		hourLabels.setBackground(GuiConstants.STD_BACKGROUND);
		for(int i = 0; i < GuiConstants.HOURS; i++) {
			JLabel lbl = new JLabel(i<10 ? "0"+i : ""+i);
			lbl.setFont(GuiConstants.HOUR_FONT);
			lbl.setPreferredSize(new Dimension(15, GuiConstants.HOUR_HEIGHT));
			lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, (GuiConstants.HOUR_HEIGHT-lbl.getHeight()-7), 0));
			lbl.setForeground(GuiConstants.STD_FOREGROUND);
			hourLabels.add(lbl);
		}
		weekPanel.add(hourLabels);
		
		gbc.gridy = 1;
		for(int i = 0; i < GuiConstants.DAYS.length; i++) {
			days[i] = new CalendarDayBox(i);
			gbc.gridx = i;
			weekPanel.add(days[i]);
		}
		
		gbc.gridx = 0;
		gbc.gridwidth = 7;		
		pane = new JScrollPane(weekPanel);
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pane.getVerticalScrollBar().setUnitIncrement(GuiConstants.STD_SCROLL_SPEED);
//		pane.getVerticalScrollBar().setValue(7 * Constants.HOUR_HEIGHT); TODO: se om dette kan fikses
		pane.setMinimumSize(new Dimension(933, GuiConstants.DAYBOX_HEIGHT));
		pane.setViewportBorder(GuiConstants.EMPTY_BORDER_1);
		add(pane, gbc);
	}
	

	private void addCalendarHeaders() {
		gbc.gridwidth = 1;
		gbc.weightx = 1.0;
		gbc.gridy = 0;
		for(int i = 0; i < GuiConstants.DAYS.length; i++) {
			gbc.gridx = i;
			JLabel lbl = new JLabel(GuiConstants.DAYS[i]);
			lbl.setFont(GuiConstants.WEEKDAY_FONT);
			add(lbl, gbc);
		}
	}

	public Employee getUser() {
		return user;
	}

	public void setUser(Employee user) {
		this.user = user;
	}
	
	private void loadCurrentWeek() {
		//TODO
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		//TODO: fikse slik at dette fungerer
		String property = evt.getPropertyName();
		if(property.equals(OverviewCalendarPanel.SELECTED_DAY_CHANGED)) {
			currentWeek = ((Calendar)(evt.getNewValue())).get(Calendar.WEEK_OF_YEAR);
			loadCurrentWeek();
			System.out.println("week changed in CalendarPanel to: " + currentWeek);
		}
	}

}


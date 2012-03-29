package no.ntnu.fp.client.gui;

import no.ntnu.fp.client.gui.objects.CalendarDayBox;
import no.ntnu.fp.common.Util;
import no.ntnu.fp.common.model.Day;
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
    private CalendarDayBox[] days;
    private DefaultListModel model;
    private int currentYear;
    private int currentMonth;
    private int currentWeek;
    private int currentDay;

    public CalendarPanel() {
    	Calendar c = Util.getCalendar();
    	currentYear = c.get(Calendar.YEAR);
    	currentMonth = c.get(Calendar.MONTH);
    	currentWeek = c.get(Calendar.WEEK_OF_YEAR);//TODO: added -1 to get the correct initialization
    	currentDay = c.get(Calendar.DAY_OF_MONTH);
        setLayout(new GridBagLayout());
        addCalendarHeaders();
        initWeekPanel();
    }

    public void addEvents(ArrayList<Event> events) {
        for (Event e : events) {
        	Util.print("Event " + e + " " + e.getDateFrom() + " added to listModel in CalendarPanel");
            model.addElement(e);
        }
    }

    private void initWeekPanel() {
        weekPanel = new JPanel(new FlowLayout());
        weekPanel.setBackground(GuiConstants.STD_BACKGROUND);
        initHourLabels();
        initCalendarDayBoxes();
        initScrollPane();
    }

    private void initHourLabels() {
        JPanel hourLabels = new JPanel();
        hourLabels.setLayout(new BoxLayout(hourLabels, BoxLayout.Y_AXIS));
        hourLabels.setBackground(GuiConstants.STD_BACKGROUND);
        for (int i = 0; i < GuiConstants.HOURS; i++) {
            JLabel lbl = new JLabel(i < 10 ? "0" + i : "" + i);
            lbl.setFont(GuiConstants.HOUR_FONT);
            lbl.setPreferredSize(new Dimension(15, GuiConstants.HOUR_HEIGHT));
            lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, (GuiConstants.HOUR_HEIGHT - lbl.getHeight() - 7), 0));
            lbl.setForeground(GuiConstants.STD_FOREGROUND);
            hourLabels.add(lbl);
        }
        weekPanel.add(hourLabels);
    }

    private void initCalendarDayBoxes() {
        days = new CalendarDayBox[GuiConstants.DAYS.length];
        gbc.gridy = 1;
        for (int i = 0; i < GuiConstants.DAYS.length; i++) {
            Calendar c = Util.getCalendar();
            c.set(Calendar.WEEK_OF_YEAR, currentWeek);//TODO: currentWeek+1 - seams to work, but not sure..
            c.set(currentYear, currentMonth, currentDay);
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY+i);
            days[i] = new CalendarDayBox(i, c);
            gbc.gridx = i;
            weekPanel.add(days[i]);
            days[i].setParent(this);
        }
    }

    private void initScrollPane() {
        gbc.gridx = 0;
        gbc.gridwidth = 7;
        pane = new JScrollPane(weekPanel);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pane.getVerticalScrollBar().setUnitIncrement(GuiConstants.STD_SCROLL_SPEED);
        for(int i = 0; i < 7; i++)
        	(pane.getVerticalScrollBar()).setValue((pane.getVerticalScrollBar()).getValue()+GuiConstants.HOUR_HEIGHT);
        pane.setMinimumSize(new Dimension(933, GuiConstants.DAYBOX_HEIGHT));
        pane.setViewportBorder(GuiConstants.EMPTY_BORDER_1);
        add(pane, gbc);
    }

    private void addCalendarHeaders() {
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.gridy = 0;
        for (int i = 0; i < GuiConstants.DAYS.length; i++) {
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

    public void loadCurrentWeek() {
        Util.print("week changed in CalendarPanel to: " + currentWeek);
        weekPanel.removeAll();
        weekPanel.revalidate();
        initHourLabels();
        for(int i = 0; i < 7; i++) {
	        Calendar c = Util.getCalendar();
	        c.set(Calendar.WEEK_OF_YEAR, currentWeek);//TODO:hack med currentWeek+1
	        c.set(currentYear, currentMonth, currentDay);
	        c.set(Calendar.DAY_OF_WEEK, 2+i);
	        days[i].removeAll();
	        days[i] = new CalendarDayBox(i, c);
	        days[i].paintEvents();
	        weekPanel.add(days[i]);
//	        days[i].setSelected(false);
        }    
//        days[currentDay%7].setSelected(true);
        revalidate();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String property = evt.getPropertyName();
        Util.print(((Calendar)evt.getNewValue()).getTime());
        if (property.equals(OverviewCalendarPanel.SELECTED_DAY_CHANGED)) {
        	currentYear = ((Calendar) (evt.getNewValue())).get(Calendar.YEAR);
        	currentMonth= ((Calendar) (evt.getNewValue())).get(Calendar.MONTH);
            currentWeek = ((Calendar) (evt.getNewValue())).get(Calendar.WEEK_OF_YEAR);
            currentDay = ((Calendar) (evt.getNewValue())).get(Calendar.DAY_OF_MONTH);
            loadCurrentWeek();
        }
    }

    public void setModel(DefaultListModel newModel) {
        this.model = newModel;
        if (model.size() == 7) {
            for (int i = 0; i < 7; i++) {
                days[i].setModel((Day) this.model.get(i));
            }
        }
    }

}


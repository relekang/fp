package no.ntnu.fp.common.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import no.ntnu.fp.common.Util;

public class Day extends ArrayList<Event> implements Model{

    private PropertyChangeSupport pcs;
    private Calendar cal;

    public Day(ArrayList<Event> list, Date date){
    	this(date);
        for(Event e:list)
            add(e);
    }
    
    public Day(Date date) {
    	cal = Util.getCalendar();
        cal.setTime(date);
        pcs = new PropertyChangeSupport(this);
    }

    @Override
    public boolean save() {
        return false;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    
    public void setDate(Calendar date) {
    	this.cal = date;
    }

    public Date getDate() {
        return cal.getTime();
    }
}

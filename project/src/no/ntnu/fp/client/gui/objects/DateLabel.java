package no.ntnu.fp.client.gui.objects;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Calendar;
import java.util.Date;

public class DateLabel extends JLabel{
    private Calendar cal;
    private int month;


    public DateLabel(Date date, int month){
        super();
        cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(date);
        this.month = month;
        setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
        setBorder(new EmptyBorder(4, 4, 4, 4));
        if(cal.get(Calendar.MONTH) + 1 != this.month){
            setForeground(Color.GRAY);
        }
    }

    public Calendar getCalendar() {
        return cal;
    }

    public int getYear(){
    	return cal.get(Calendar.YEAR);
    	
    }
    public int getMonth(){
    	return cal.get(Calendar.MONTH);
    	
    }
    public int getDay(){
    	return cal.get(Calendar.DATE);
    	
    }

    
   
}

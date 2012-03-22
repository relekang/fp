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

public class DateLabel extends JLabel implements MouseListener{
    private Calendar cal;
    private int month;
    private PropertyChangeSupport pcs;
    

    public DateLabel(Date date, int month){
        super();
        cal = Calendar.getInstance();
        cal.setTime(date);
        this.month = month;
        setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
        setBorder(new EmptyBorder(4, 4, 4, 4));
        pcs = new PropertyChangeSupport(this);
        this.addMouseListener(this);
        if(cal.get(Calendar.MONTH) + 1 != this.month){
            setForeground(Color.GRAY);
        }
    }
    public void addMouseListener(PropertyChangeListener listener){
    	
    	pcs.addPropertyChangeListener(listener);
        
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
    
 
	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
	        System.out.println(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH));
	      pcs.firePropertyChange("date selected", "", cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH));  
	 }

	    @Override
	    public void mousePressed(MouseEvent mouseEvent)  { }

	    @Override
	    public void mouseReleased(MouseEvent mouseEvent) { }

	    @Override
	    public void mouseEntered(MouseEvent mouseEvent)  { }

	    @Override
	    public void mouseExited(MouseEvent mouseEvent)   { }
    
   
}

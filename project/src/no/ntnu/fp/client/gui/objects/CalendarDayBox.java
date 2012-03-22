package no.ntnu.fp.client.gui.objects;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import no.ntnu.fp.client.controller.ClientApplication;
import no.ntnu.fp.client.gui.GuiConstants;
import no.ntnu.fp.common.model.Day;
import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.common.model.Room;

@SuppressWarnings("serial")
public class CalendarDayBox extends JPanel implements MouseListener, MouseMotionListener, PropertyChangeListener{

	private int y, dy;
	private Calendar date; 
	private CalendarCanvas canvas;
	private PropertyChangeSupport pcs;
	
	private List<Event> events = new ArrayList<Event>();
    private Day day;

    public CalendarDayBox(int reprDay, Calendar date) {
		this(reprDay);
		this.date = date;
	}
	
	public CalendarDayBox(int reprDay) {
		pcs = new PropertyChangeSupport(this);
		switch(reprDay) {
		case 0: 
			setBorder(BorderFactory.createEmptyBorder(-5, 0, -5, -5));
		case 6:
			setBorder(BorderFactory.createEmptyBorder(-5, -5, -5, 0));
		default:
			setBorder(BorderFactory.createEmptyBorder(-5, -5, -5, -5));
		}
		initCanvas();
	}

	private void initCanvas() {
		canvas = new CalendarCanvas();
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		add(canvas);
	}

    public void setModel(Day day) {
        this.day = day;
    }

    private class CalendarCanvas extends JPanel {
		boolean mouseIsPressed = false;
		private Color foreground = GuiConstants.DRAG_NEW_EVENT;
		
		public CalendarCanvas() {
			setBorder(BorderFactory.createEmptyBorder(-5, -5, -5, -5));
			setPreferredSize(new Dimension(GuiConstants.CANVAS_WIDTH, GuiConstants.CANVAS_HEIGHT));
			setForeground(foreground);
			setBackground(GuiConstants.STD_BACKGROUND);
		}
				
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			drawForegroundLines(g);
			paintEventLabels(g);
			g.setColor(foreground);
			if(mouseIsPressed) {
				g.fillRoundRect(0, y, GuiConstants.CANVAS_WIDTH, dy-y, 10, 10);
			}
		}
		
		private void drawForegroundLines(Graphics g) {
			g.setColor(GuiConstants.STD_FOREGROUND);
			for(int i = 1; i < GuiConstants.HOURS; i++) {
				g.drawLine(0, i* GuiConstants.HOUR_HEIGHT, GuiConstants.CANVAS_WIDTH, i* GuiConstants.HOUR_HEIGHT);
			}
		}
		
		private void paintEventLabels(Graphics g) {
			for(EventLabel lbl : events) {
				g.setColor(lbl.getEventColor());
				g.fillRoundRect(0, lbl.getFromPixel(), GuiConstants.CANVAS_WIDTH-10, lbl.getToPixel()-lbl.getFromPixel(), 10, 10);
				g.setColor(lbl.getTextColor());
				lbl.drawRepresentation(g);
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		dy = e.getY() > y ? e.getY() : y;
		canvas.repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		canvas.mouseIsPressed = true;
		y = e.getY();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		dy = e.getY();
		canvas.mouseIsPressed = false;
		createNewEvent(e);
		canvas.repaint();
	}
	
	private void createNewEvent(MouseEvent e) {
		Event label = new Event("Lol");
		int[] from = EventLabel.getTimeFromPixel(label.getFromPixel());
		int[] to = EventLabel.getTimeFromPixel(label.getToPixel());
		Calendar calFrom = fixTime(from);
		Calendar calTo = fixTime(to);
		Event ev = new Event("", calFrom.getTime(), calTo.getTime());
		events.add(label);
		
	}
	
	private Calendar fixTime(int[] hourAndMin) {
		Calendar cal = Calendar.getInstance();//(Calendar)getDate().clone();
		cal.set(Calendar.HOUR_OF_DAY, hourAndMin[0]);
		cal.set(Calendar.MINUTE, hourAndMin[1]);
		return cal;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	public Date getDate() {
		return this.day.getDate();
	}

}
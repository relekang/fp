package no.ntnu.fp.client.gui.objects;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import no.ntnu.fp.client.gui.Constants;
import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.common.model.Room;

@SuppressWarnings("serial")
public class CalendarDayBox extends JPanel implements MouseListener, MouseMotionListener, PropertyChangeListener{

	private CalendarCanvas canvas;
	private int y, dy;
	
	private Date date; 
	
	private List<EventLabel> events = new ArrayList<EventLabel>();
	
	public CalendarDayBox(int reprDay, Date date) {
		this(reprDay);
		this.date = date;
	}
	
	public CalendarDayBox(int reprDay) {
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
	
	private class CalendarCanvas extends JPanel {
		boolean mouseIsPressed = false;
		private Color foreground = Constants.DRAG_NEW_EVENT;
		
		public CalendarCanvas() {
			setBorder(BorderFactory.createEmptyBorder(-5, -5, -5, -5));
			setPreferredSize(new Dimension(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT));
			setForeground(foreground);
			setBackground(Constants.STD_BACKGROUND);
		}
				
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			drawForegroundLines(g);
			paintEventLabels(g);
			g.setColor(foreground);
			if(mouseIsPressed) {
				g.fillRoundRect(0, y, Constants.CANVAS_WIDTH, dy-y, 10, 10);
			}
		}
		
		private void drawForegroundLines(Graphics g) {
			g.setColor(Constants.STD_FOREGROUND);
			for(int i = 1; i < Constants.HOURS; i++) {
				g.drawLine(0, i*Constants.HOUR_HEIGHT, Constants.CANVAS_WIDTH, i*Constants.HOUR_HEIGHT);
			}
		}
		
		private void paintEventLabels(Graphics g) {
			for(EventLabel lbl : events) {
				g.setColor(lbl.getEventColor());
				g.fillRoundRect(0, lbl.getFromPixel(), Constants.CANVAS_WIDTH-10, lbl.getToPixel()-lbl.getFromPixel(), 10, 10);
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
		Event ev = new Event("lorem ipsum loreum impsum, lorem");
		ev.setRoom(new Room("Sebrarommet", "EL", 10));
		EventLabel label = new EventLabel(y, dy, ev);
		events.add(label);
		canvas.repaint();
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

}
package no.ntnu.fp.gui.objects;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import no.ntnu.fp.gui.Constants;

public class CalendarDayBox extends JPanel implements MouseListener, MouseMotionListener, PropertyChangeListener{

	private MyCanvas canvas;
	
	private int y, dy;
	
	private List<EventLabel> events;
	
	public CalendarDayBox(int reprDay) {
		events = new ArrayList<EventLabel>();
		events.add(new EventLabel(100, 300));
		events.add(new EventLabel(500, 550));
		(events.get(1)).setEventColor(Constants.EVENT_ACCEPTED);
		(events.get(0)).setEventColor(Constants.EVENT_DECLINED);
		events.add(new EventLabel(600, 800));
		
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
//		Calendar cal = Calendar.getInstance();
		canvas = new MyCanvas();
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		add(canvas);
	}
	
	private class MyCanvas extends JPanel {
		boolean mouseIsPressed = false;
		private Color foreground = Constants.DRAG_NEW_EVENT;
		
		public MyCanvas() {
			setBorder(BorderFactory.createEmptyBorder(-5, -5, -5, -5));
			setPreferredSize(new Dimension(Constants.WIDTH_CANVAS, Constants.HEIGHT_CANVAS));
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
				g.fillRoundRect(0, y, Constants.WIDTH_CANVAS, dy-y, 10, 10);
			}
		}
		
		private void drawForegroundLines(Graphics g) {
			g.setColor(Constants.STD_FOREGROUND);
			for(int i = 1; i < Constants.HOURS; i++) {
				g.drawLine(0, i*Constants.HOUR_HEIGHT, Constants.WIDTH_CANVAS, i*Constants.HOUR_HEIGHT);
			}
		}
		
		private void paintEventLabels(Graphics g) {
			for(EventLabel l : events) {
				g.setColor(l.getEventColor());
				g.fillRoundRect(0, l.getFromPixel(), Constants.WIDTH_CANVAS-10, l.getToPixel()-l.getFromPixel(), 10, 10);
				g.setColor(Constants.STD_FOREGROUND);
				g.drawString("Testetest", 0, l.getFromPixel()+20);
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
		canvas.mouseIsPressed = false;
		EventLabel label = new EventLabel(y, dy-y);
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
package no.ntnu.fp.gui.objects;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import no.ntnu.fp.gui.Constants;

public class CalendarDayBox extends JPanel implements MouseListener, MouseMotionListener {

	private MyCanvas canvas;
	
	private int x = 0, dx = Constants.WIDTH_CANVAS;
	private int y, dy;
	
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
//		Calendar cal = Calendar.getInstance();
		canvas = new MyCanvas();
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		add(canvas);
	}
	
	private class MyCanvas extends JPanel {
		boolean mouseIsPressed = false;
		private Color c;
		
		public MyCanvas() {
			setPreferredSize(new Dimension(Constants.WIDTH_CANVAS, Constants.HEIGHT_CANVAS));
			c = new Color(179, 209, 232);
			setForeground(c);
			setBackground(Color.LIGHT_GRAY);
		}
				
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLUE);
			for(int i = 1; i < 24; i++) {
				g.drawLine(0, i*Constants.HOUR_HEIGHT, Constants.WIDTH_CANVAS, i*Constants.HOUR_HEIGHT);
			}
			g.setColor(c);
			if(mouseIsPressed) {
				g.fillRect(x, y, dx, dy-y);
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

}
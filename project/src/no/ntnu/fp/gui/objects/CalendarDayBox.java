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

public class CalendarDayBox extends JPanel implements MouseListener, MouseMotionListener {

	public static final int NUM_VISIBLE_CELLS = 8;
	public static final int NUM_HOURS = 24;
	public static final int WIDTH_CANVAS = 120;
	public static final int WIDTH_CELL = WIDTH_CANVAS;
	public static final int HEIGHT_CELL = 30;
	public static final int HEIGHT_CANVAS = 720;
	
	private MyCanvas canvas;
	
	private int x = 0, dx = WIDTH_CANVAS;
	private int y, dy;
	
	public CalendarDayBox() {
		setBorder(BorderFactory.createEmptyBorder());
		initCanvas();
	}

	private void initCanvas() {
		Calendar cal = Calendar.getInstance();
		canvas = new MyCanvas();
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		add(canvas);
	}
	
	private class MyCanvas extends JPanel {
		boolean mouseIsPressed = false;
		private Color c;
		public MyCanvas() {
			setPreferredSize(new Dimension(WIDTH_CANVAS, HEIGHT_CANVAS));
			c = new Color(179, 209, 232);
			setForeground(c);
			setBackground(Color.DARK_GRAY);
		}
				
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
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
//		x = e.getX();
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
package no.ntnu.fp.gui;

import no.ntnu.fp.gui.objects.CalendarDayBox;

import javax.swing.*;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CalendarPanel extends JPanel {

	private GridBagConstraints gbc = new GridBagConstraints();
	private JPanel weekPanel;
	private JScrollPane pane;
	
	public CalendarPanel() {
		setLayout(new GridBagLayout());
		addCalendarHeaders();
		initWeekPanel();
	}

	private void initWeekPanel() {
		weekPanel = new JPanel(new FlowLayout());
		weekPanel.setBackground(Constants.STD_BACKGROUND);
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.setBackground(Color.LIGHT_GRAY);
		for(int i = 0; i < Constants.HOURS; i++) {
			JLabel l = new JLabel(i<10 ? "0"+i : ""+i);
			l.setFont(Constants.HOUR_FONT);
			l.setPreferredSize(new Dimension(20, 50));
			l.setBorder(BorderFactory.createEmptyBorder(0, 0, (Constants.HOUR_HEIGHT-l.getHeight()-6), 0));
			l.setForeground(Constants.STD_FOREGROUND);
			l.setBackground(Constants.STD_BACKGROUND);
			p.add(l);
		}
		weekPanel.add(p);
		
		gbc.gridy = 1;
		for(int i = 0; i < Constants.DAYS.length; i++) {
			gbc.gridx = i;
			weekPanel.add(new CalendarDayBox(i));
		}
		
		gbc.gridx = 0;
		gbc.gridwidth = 7;		
		pane = new JScrollPane(weekPanel);
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pane.setMinimumSize(new Dimension(933, Constants.DAYBOX_HEIHGT));
		add(pane, gbc);
	}
	

	private void addCalendarHeaders() {
		gbc.gridwidth = 1;
		gbc.weightx = 1.0;
		gbc.gridy = 0;
		for(int i = 0; i < Constants.DAYS.length; i++) {
			gbc.gridx = i;
			JLabel l = new JLabel(Constants.DAYS[i]);
			l.setPreferredSize(new Dimension(970/70, 25));
			l.setFont(Constants.WEEKDAY_FONT);
			add(l, gbc);
		}
	}

}
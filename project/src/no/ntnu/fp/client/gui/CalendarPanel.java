package no.ntnu.fp.client.gui;

import no.ntnu.fp.client.gui.objects.CalendarDayBox;
import no.ntnu.fp.common.model.Employee;

import javax.swing.*;

import java.awt.*;

public class CalendarPanel extends JPanel {

	private GridBagConstraints gbc = new GridBagConstraints();
	private JPanel weekPanel;
	private JScrollPane pane;
	private Employee user;
	
	public CalendarPanel() {
		setLayout(new GridBagLayout());
		addCalendarHeaders();
		initWeekPanel();
	}
	
	public CalendarPanel(Employee user) {
		this();
		this.user = user;
	}

	private void initWeekPanel() {
		weekPanel = new JPanel(new FlowLayout());
		weekPanel.setBackground(Constants.STD_BACKGROUND);
		JPanel hourLabels = new JPanel();
		hourLabels.setLayout(new BoxLayout(hourLabels, BoxLayout.Y_AXIS));
		hourLabels.setBackground(Constants.STD_BACKGROUND);
		for(int i = 0; i < Constants.HOURS; i++) {
			JLabel lbl = new JLabel(i<10 ? "0"+i : ""+i);
			lbl.setFont(Constants.HOUR_FONT);
			lbl.setPreferredSize(new Dimension(15, Constants.HOUR_HEIGHT));
			lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, (Constants.HOUR_HEIGHT-lbl.getHeight()-7), 0));
			lbl.setForeground(Constants.STD_FOREGROUND);
			hourLabels.add(lbl);
		}
		weekPanel.add(hourLabels);
		
		gbc.gridy = 1;
		for(int i = 0; i < Constants.DAYS.length; i++) {
			gbc.gridx = i;
			weekPanel.add(new CalendarDayBox(i));
		}
		
		gbc.gridx = 0;
		gbc.gridwidth = 7;		
		pane = new JScrollPane(weekPanel);
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pane.getVerticalScrollBar().setUnitIncrement(Constants.STD_SCROLL_SPEED);
//		pane.getVerticalScrollBar().setValue(7 * Constants.HOUR_HEIGHT); TODO: se om dette kan fikses
		pane.setMinimumSize(new Dimension(933, Constants.DAYBOX_HEIGHT));
		pane.setViewportBorder(Constants.EMPTY_BORDER_1);
		add(pane, gbc);
	}
	

	private void addCalendarHeaders() {
		gbc.gridwidth = 1;
		gbc.weightx = 1.0;
		gbc.gridy = 0;
		for(int i = 0; i < Constants.DAYS.length; i++) {
			gbc.gridx = i;
			JLabel lbl = new JLabel(Constants.DAYS[i]);
//			l.setPreferredSize(new Dimension(970/70, 25));
			lbl.setFont(Constants.WEEKDAY_FONT);
			add(lbl, gbc);
		}
	}

	public Employee getUser() {
		return user;
	}

	public void setUser(Employee user) {
		this.user = user;
	}

}
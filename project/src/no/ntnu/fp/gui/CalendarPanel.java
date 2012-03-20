package no.ntnu.fp.gui;

import no.ntnu.fp.gui.objects.CalendarDayBox;

import javax.swing.*;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CalendarPanel extends JPanel {
	
	private GridBagConstraints gbc = new GridBagConstraints();
	private CalendarDayBox mondayBox, tuesdayBox, wednesdayBox, thursdayBox,
			fridayBox, saturdayBox, sundayBox;
	private JLabel monLabel, tueLabel, wedLabel, thuLabel, friLabel, satLabel,
			sunLabel;
	private JPanel weekPanel;
	private JScrollPane pane;
	
	public CalendarPanel() {
		setLayout(new GridBagLayout());
		addCalendarHeaders();
		addCalendarDayBoxes();
	}

	private void addCalendarDayBoxes() {
		weekPanel = new JPanel(new FlowLayout());
		gbc.gridx = 0;
		gbc.gridy = 1;
		mondayBox = new CalendarDayBox(0);
//		add(mondayBox, gbc);
		weekPanel.add(mondayBox);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		tuesdayBox = new CalendarDayBox(1);
//		add(tuesdayBox, gbc);
		weekPanel.add(tuesdayBox);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		wednesdayBox = new CalendarDayBox(2);
//		add(wednesdayBox, gbc);
		weekPanel.add(wednesdayBox);

		gbc.gridx = 3;
		gbc.gridy = 1;
		thursdayBox = new CalendarDayBox(3);
//		add(thursdayBox, gbc);
		weekPanel.add(thursdayBox);

		gbc.gridx = 4;
		gbc.gridy = 1;
		fridayBox = new CalendarDayBox(4);
//		add(fridayBox, gbc);
		weekPanel.add(fridayBox);

		gbc.gridx = 5;
		gbc.gridy = 1;
		saturdayBox = new CalendarDayBox(5);
//		add(saturdayBox, gbc);
		weekPanel.add(saturdayBox);

		gbc.gridx = 6;
		gbc.gridy = 1;
		sundayBox = new CalendarDayBox(6);
//		add(sundayBox, gbc);
		weekPanel.add(sundayBox);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 7;
		
		pane = new JScrollPane(weekPanel);
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pane.setMinimumSize(new Dimension(900, Constants.DAYBOX_HEIHGT));
		add(pane, gbc);
	}
	

	private void addCalendarHeaders() {
		Font f = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
		gbc.gridwidth = 1;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		monLabel = new JLabel("Monday");
		monLabel.setPreferredSize(new Dimension(970/70, 25));
		monLabel.setFont(f);
		add(monLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		tueLabel = new JLabel("Tuesday");
		tueLabel.setPreferredSize(new Dimension(970/70, 25));
		tueLabel.setFont(f);
		add(tueLabel, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		wedLabel = new JLabel("Wednesday");
		wedLabel.setPreferredSize(new Dimension(970/70, 25));
		wedLabel.setFont(f);
		add(wedLabel, gbc);

		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		thuLabel = new JLabel("Thursday");
		thuLabel.setPreferredSize(new Dimension(970/70, 25));
		thuLabel.setFont(f);
		add(thuLabel, gbc);

		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		friLabel = new JLabel("Friday");
		friLabel.setPreferredSize(new Dimension(970/70, 25));
		friLabel.setFont(f);
		add(friLabel, gbc);

		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		satLabel = new JLabel("Saturday");
		satLabel.setPreferredSize(new Dimension(970/70, 25));
		satLabel.setFont(f);
		add(satLabel, gbc);

		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		sunLabel = new JLabel("Sunday");
		sunLabel.setPreferredSize(new Dimension(970/70, 25));
		sunLabel.setFont(f);
		add(sunLabel, gbc);
	}

}
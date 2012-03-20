package no.ntnu.fp.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DateTimePicker extends JFrame{
	private JPanel datePickerPanel, timePickerPanel, mainPanel;
	private GridBagConstraints gbc;
	protected JTextField hourField, minuteField;
	
	public DateTimePicker() {
        mainPanel = new JPanel();
        datePickerPanel = new OverviewCalenderPanel();
        hourField = new JTextField("hour",3);
        minuteField = new JTextField("min",3);
        gbc = new GridBagConstraints();
		buildMainPanel();
		mainPanel.setVisible(true);

	    pack();
	}
	
	
	private void buildMainPanel(){
		mainPanel.setLayout(new GridBagLayout());
		gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4; gbc.gridheight=1;
		mainPanel.add(datePickerPanel, gbc);
		
		
		gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 1;
		mainPanel.add(hourField,gbc);
		gbc.gridx = 2; gbc.gridy = 3;
		mainPanel.add(minuteField,gbc);
		add(mainPanel);
		
	}
	
	public static void main(String[] args) {
		DateTimePicker test = new DateTimePicker();
		test.pack();
		test.setVisible(true);
		
	}
	
}

package no.ntnu.fp.client.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DateTimePicker extends JPanel implements MouseListener {

	private JPanel timePickerPanel, mainPanel;
	private OverviewCalendarPanel datePickerPanel;
	private GridBagConstraints gbc;
	private JLabel semicolon;
	protected JTextField hourField, minuteField;
	private int hour, min; 

	public int getHourField(){
		int temp;
		try{
			temp = Integer.parseInt(hourField.getText());
		}
		catch (Exception e) {
			return hour;
		}
		if(temp >=0 && temp < 24)
			hour = temp;
		return hour;
	}
	
	public int getMinField(){
		int temp;
		try{
			temp = Integer.parseInt(minuteField.getText());
		}
		catch (Exception e) {
			return min;
		}
		if(temp >=0 && temp < 60)
			min = temp;
		return min;
	}

	public JPanel getDatePickerPanel(){
		return datePickerPanel;
	}

	public DateTimePicker() {
        mainPanel = new JPanel();
        semicolon = new JLabel(":");
        datePickerPanel = new OverviewCalendarPanel();
        timePickerPanel = new JPanel();
        hourField = new JTextField("hour",3);
        minuteField = new JTextField("min",3);
        gbc = new GridBagConstraints();
		buildMainPanel();
		mainPanel.setVisible(true);
	}
	
	public OverviewCalendarPanel getOverviewCalendarPanel(){
		return datePickerPanel;
	}
	
	public JTextField getHourTextField(){
		return hourField;
	}
	
	public JTextField getMinuteTextField(){
		return minuteField;
	}
	
	private void buildMainPanel(){
		//mainpanel
		mainPanel.setLayout(new GridBagLayout());
		
		//timepickerpanel
		gbc.gridx = 0; gbc.gridy = 0;
		mainPanel.add(datePickerPanel, gbc);
		timePickerPanel.add(hourField);
		timePickerPanel.add(semicolon);
		timePickerPanel.add(minuteField);
		gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 7;
		mainPanel.add(timePickerPanel, gbc);
		add(mainPanel);
		
		hourField.addMouseListener(this);
		minuteField.addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(hourField.getText().equals("")){
			hourField.setText("hour");
			
		}
		if(minuteField.getText().equals("")){
			minuteField.setText("min");
			
		}
		if(e.getSource() == hourField && hourField.getText().equals("hour")){
			hourField.setText("");
			hourField.grabFocus();
		}
		if(e.getSource() == minuteField && minuteField.getText().equals("min")){
			minuteField.setText("");
			minuteField.requestFocus();
		}
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}

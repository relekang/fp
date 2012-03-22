package no.ntnu.fp.client.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DateTimePicker extends JPanel implements MouseListener, KeyListener{
	private JPanel datePickerPanel, timePickerPanel, mainPanel;
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
	

	public DateTimePicker(EventView view) {
        mainPanel = new JPanel();
        semicolon = new JLabel(":");
        datePickerPanel = new OverviewCalenderPanel();
        timePickerPanel = new JPanel();
        hourField = new JTextField("hour",3);
        minuteField = new JTextField("min",3);
        hourField.addKeyListener(view);
        minuteField.addKeyListener(view);
        
        gbc = new GridBagConstraints();
		buildMainPanel();
		mainPanel.setVisible(true);

	    
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
		hourField.addKeyListener(this);
		minuteField.addKeyListener(this);
		
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


	@Override
	public void mouseEntered(MouseEvent e) {
	
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

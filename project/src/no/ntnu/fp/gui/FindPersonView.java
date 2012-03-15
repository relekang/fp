package no.ntnu.fp.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class FindPersonView extends JFrame implements ActionListener{
    private JTextField searchField;
    private JButton backBTN, fwdBTN, compare;
    private JComboBox week;
    private GridBagConstraints gbc;
    private JPanel calenderPanel;
    private String[] weekArray = {"WEEK 10","WEEK 11","WEEK 12","WEEK 13","WEEK 14","WEEK 15","WEEK 16","WEEK 17"};
	
	public FindPersonView(){
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(15, 15, 15, 15);
		this.setLayout(new GridBagLayout());
//		user = EventController.getEmplyee();
//		eventPanel = new JPanel();
//		Panel.setLayout(new GridBagLayout());
		
		initialize();
		addElements();
		this.pack();
    }
	
	
	private void initialize(){
		calenderPanel = new CalendarPanel();
		backBTN = new JButton("Previous");
		fwdBTN = new JButton("Next");
		compare = new JButton("Compare");
		searchField = new JTextField(20);
		week = new JComboBox(weekArray);
	}
	
	private void addElements(){
		gbc.gridx = 0; gbc.gridy = 0;
		add(searchField,gbc);
		gbc.gridx = 4;
		add(backBTN,gbc);
		gbc.gridx = 5;
		add(week,gbc);
		gbc.gridx = 6;
		add(fwdBTN,gbc);
		gbc.gridx = 9;
		add(compare,gbc);
		
		
		
		gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 10;
		this.add(calenderPanel, gbc);
		searchField.addActionListener(this);
		backBTN.addActionListener(this);
		fwdBTN.addActionListener(this);
		compare.addActionListener(this);
	
	
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == searchField){
			// TODO Auto-generated method stub
			System.out.println("you searched for "+searchField.getText());
		}
		else if(arg0.getSource() == backBTN){
			if(week.getSelectedIndex()>=0)
			week.setSelectedIndex(week.getSelectedIndex()-1);
		}
		else if(arg0.getSource() == fwdBTN){
			if(week.getSelectedIndex()<weekArray.length-1)
			week.setSelectedIndex(week.getSelectedIndex()+1);
		}
		else if(arg0.getSource() == compare){
			
			System.out.println("compare");
			
		}
		
	}
	
}

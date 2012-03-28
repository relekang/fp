package no.ntnu.fp.client.gui;

import no.ntnu.fp.common.Util;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class FindPersonView extends JFrame implements ActionListener{
    private JTextField searchField;
    private JButton previousButton, nextButton, compare;
    private JComboBox week;
    private GridBagConstraints gbc, gbc1;
    private JPanel findPersonPanel, calenderPanel, buttonPanel;
    private String[] weekArray = {"WEEK 10","WEEK 11","WEEK 12","WEEK 13",
    							  "WEEK 14","WEEK 15","WEEK 16","WEEK 17"};
	
	public FindPersonView(){
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(15, 15, 15, 15);
		findPersonPanel = new JPanel();
		findPersonPanel.setLayout(new GridBagLayout());
//		user = EventController.getEmplyee();
//		eventPanel = new JPanel();
//		Panel.setLayout(new GridBagLayout());
		
		calenderPanel = new CalendarPanel();
		previousButton = new JButton("Previous");
		nextButton = new JButton("Next");
		compare = new JButton("Compare");
		searchField = new JTextField();
		week = new JComboBox(weekArray);
		searchField.setMinimumSize(new Dimension(200,30));
		addElements();
		this.setPreferredSize(new Dimension(this.getPreferredSize().width, (int) (this.getMinimumSize()).getHeight()));
		this.pack();
    }
	
	private void addElements(){
		gbc.gridx = 0;	gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.weightx = 0.5;
		gbc.anchor = GridBagConstraints.WEST;
		findPersonPanel.add(searchField,gbc);
		
		
		gbc.gridx = 3; gbc.gridy = 0; 
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.EAST;
		findPersonPanel.add(previousButton,gbc);
		
		gbc.gridx = 5; gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		findPersonPanel.add(week, gbc);
		
		gbc.gridx = 6; gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		findPersonPanel.add(nextButton, gbc);
		
//		searchField
		gbc.gridx = 8; gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.CENTER;
//		gbc.anchor = GridBagConstraints.EAST;
		findPersonPanel.add(compare, gbc);
		
		gbc.gridx = 0; gbc.gridy = 1; 
		gbc.gridwidth = 10;
		findPersonPanel.add(calenderPanel, gbc);
		
		week.setMaximumRowCount(5);
		
		searchField.addActionListener(this);
		previousButton.addActionListener(this);
		nextButton.addActionListener(this);
		compare.addActionListener(this);
		
		this.add(findPersonPanel);
	
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Fix logic and and other stuff for this view
		if(arg0.getSource() == searchField){
			Util.print("you searched for " + searchField.getText());
		}
		else if(arg0.getSource() == previousButton){
			if(week.getSelectedIndex()>0)
			week.setSelectedIndex(week.getSelectedIndex()-1);
		}
		else if(arg0.getSource() == nextButton){
			if(week.getSelectedIndex()<weekArray.length-1)
			week.setSelectedIndex(week.getSelectedIndex()+1);
		}
		else if(arg0.getSource() == compare){
			Util.print("compare");
		}
	}
}

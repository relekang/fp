package no.ntnu.fp.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Locale;
import no.ntnu.fp.model.Employee;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import no.ntnu.fp.model.Employee;

public class EventView extends JFrame{
	
	JTable participantList;
	JComponent roomBox, descriptionBox;
	JButton saveButton, cancelButton, deletebutton, acceptButton, declineButton;
	JTextField eventTitle, fromField, toField, participantsField;
	JPanel eventPanel;
	GridBagConstraints gbc;
	DefaultTableModel tableModel;
	ParticipantRenderer renderer;
	Employee user;
	JPopupMenu fromPop;
	
	public EventView(){
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(15, 15, 15, 15);
//		user = EventController.getEmplyee();
		eventPanel = new JPanel();
		eventPanel.setLayout(new GridBagLayout());
		createPanel();
		
		this.add(eventPanel);
		this.pack();
		

	}
	
	@SuppressWarnings("unused")
	private void createPanel(){
		
		//midlertidig eksempler
		Employee hans = new Employee("Hans", "heihei", new Date(1998,2,2), Employee.Gender.MALE);
		Employee geir = new Employee("Geir", "heihei", new Date(1998,2,2), Employee.Gender.MALE);
		Employee bjarne = new Employee("Bjarne", "heihei", new Date(1998,2,2), Employee.Gender.MALE);
		Employee arne = new Employee("Arne", "heihei", new Date(1998,2,2), Employee.Gender.MALE);
		//
		
		renderer = new ParticipantRenderer();
		tableModel = new DefaultTableModel();
		
		participantList = new JTable(tableModel);
		//participantList.setCellRenderer(renderer);
		
		tableModel.addRow(new Object[]{bjarne});
		tableModel.addRow(new Object[]{hans});
		tableModel.addRow(new Object[]{geir});
		tableModel.addRow(new Object[]{arne});
		
		participantList.setPreferredSize(new Dimension(300, 400));
		
		//skal sjekke om brukeren er eventmanager
		String[] rooms = {"Room", "211, P15", "R2"};
		eventTitle = new JTextField("Title", 23);
		fromField = new JTextField("From", 10);
		toField = new JTextField("to", 10);
		roomBox = new JComboBox(rooms);
		descriptionBox = new JTextArea("Description");
		participantsField = new JTextField("Participants", 23);
		
		roomBox.setPreferredSize(new Dimension(275, 25));
		descriptionBox.setPreferredSize(new Dimension(200, 100));
		
		if(false){
			saveButton = new JButton("Save");
			cancelButton = new JButton("Cancel");
			deletebutton = new JButton("Delete");
			
			gbc.gridx = 0;	gbc.gridy = 7;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			eventPanel.add(saveButton, gbc);
			
			gbc.gridx = 1;	gbc.gridy = 7;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			eventPanel.add(cancelButton, gbc);
			
			gbc.gridx = 2;	gbc.gridy = 7;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			eventPanel.add(deletebutton, gbc);
		}
		
		else{
			eventTitle.setEditable(false);
			fromField.setEditable(false);
			toField.setEditable(false);
			roomBox.setEnabled(false);
			
//			Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
//			
//			BorderFactory.createEmptyBorder();
//			eventTitle = new JLabel("Tittel", JLabel.CENTER);
//			eventTitle.setFont(font);
//			eventTitle.setPreferredSize(new Dimension(150, 20));
//			eventTitle.setBorder(BorderFactory.createLineBorder(Color.black));
//			
//			fromField = new JLabel("5.mars kl 13:37");
//			toField = new JLabel("6.mars 0:01");
//			roomBox = new JLabel("314, P15");
//			descriptionBox = new JTextArea("Kort beskrivelse");
//			descriptionBox.setPreferredSize(new Dimension(200, 100));
			acceptButton = new JButton("Accept");
			declineButton = new JButton("Decline");
//			participantsField = new JLabel();
//			
			gbc.gridx = 0; gbc.gridy = 7;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			eventPanel.add(acceptButton, gbc);
			
			gbc.gridx = 1; gbc.gridy = 7;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			eventPanel.add(declineButton, gbc);
//			
//			descriptionBox.setEnabled(false);
		}
		
		gbc.gridx = 0;	gbc.gridy = 0;
		gbc.gridwidth = 2;
		eventPanel.add(eventTitle, gbc);
		
		gbc.gridx = 0;	gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		eventPanel.add(fromField, gbc);
		
		gbc.gridx = 1;	gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		eventPanel.add(toField, gbc);
		
		gbc.gridx = 0;	gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		eventPanel.add(roomBox, gbc);
		
		gbc.gridx = 0;	gbc.gridy = 3;
		gbc.gridwidth = 2;
		eventPanel.add(participantsField, gbc);
		
		gbc.gridx = 0;	gbc.gridy = 4;
		gbc.gridheight = 2;
		gbc.gridwidth = 2;
		eventPanel.add(descriptionBox, gbc);
		
		gbc.gridx = 3;	gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.gridheight = 6;
		eventPanel.add(participantList, gbc);
		
//		saveButton.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				
//			}
//		});
//		
//		cancelButton.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//			}
//		});
//		
//		deletebutton.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//			}
//		});
	}
	
	

}

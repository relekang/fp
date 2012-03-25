package no.ntnu.fp.client.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import no.ntnu.fp.common.model.Employee;
import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.common.model.Room;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EventView extends JFrame {
	
	private JList participantList, participantPopList;
	private JTextArea descriptionBox;
	private JComboBox roomBox;
	private JButton saveButton, cancelButton, deleteButton, acceptButton, declineButton, deletePersonButton;
	private JTextField eventTitle, fromField, toField, participantsField;
	private JPanel eventPanel, listPanel, eventViewPanel, buttonPanel, participantPopPanel;
	private JLabel dash;
	private DateTimePicker calendarToPopPanel, calendarFromPopPanel;
	private GridBagConstraints gbc1, gbc2, gbc3;
	private DefaultListModel listModel, popListModel;
	private ParticipantRenderer renderer;
	private JPopupMenu fromPop, toPop, participantPop;
	private boolean shown;
	private Event model;
	
	public EventView(){
		
		gbc1 = new GridBagConstraints();
		gbc2 = new GridBagConstraints();
		gbc3 = new GridBagConstraints();
		
		gbc1.insets = new Insets(15, 15, 15, 15);
		gbc2.insets = new Insets(15, 15, 15, 15);
		gbc3.insets = new Insets(15, 15, 15, 15);
		
		eventPanel = new JPanel();
		eventPanel.setLayout(new GridBagLayout());
		listPanel = new JPanel();
		listPanel.setLayout(new GridBagLayout());
		eventViewPanel = new JPanel(new GridBagLayout());
		eventViewPanel.setLayout(new GridBagLayout());
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		calendarToPopPanel = new DateTimePicker();
		calendarFromPopPanel = new DateTimePicker();
		participantPopPanel = new JPanel();
		acceptButton = new JButton("Accept");
		declineButton = new JButton("Decline");
		saveButton = new JButton("Save");
		cancelButton = new JButton("Cancel");
		deleteButton = new JButton("Delete");
		deletePersonButton = new JButton("Remove person");
		
		
		
		createPanel();
		
		eventViewPanel.add(eventPanel);
		eventViewPanel.add(listPanel);
		
		this.add(eventViewPanel);
		this.pack();
	}
	
	public JList getParticipantList(){
		return participantList;
	}
	
	public JButton getAcceptButton(){
		return acceptButton;
	}
	
	public JButton getDeclineButton(){
		return declineButton;
	}
	
	public JButton getSaveButton(){
		return saveButton;
	}
	
	public JButton getCancelButton(){
		return cancelButton;
	}
	
	public JButton getDeleteButton(){
		return deleteButton;
	}
	
	public JButton getDeletePersonButton(){
		return deletePersonButton;
	}
	
	public JTextField getTitleField(){
		return eventTitle;
	}
	
	public void setTitleField(String title){
		eventTitle.setText(title);
	}
	
	public JTextField getFromField(){
		return fromField;
	}
	
	public void setFromField(String fromTime){
		fromField.setText(fromTime);
	}
	
	public JTextField getToField(){
		return toField;
	}
	
	public void setToField(String toTime){
		toField.setText(toTime);
	}
	
	public JComboBox getRoomBox(){
		return roomBox;
	}
	
	public void setRoomBox(Object room){
		roomBox.setSelectedItem(room);
	}
	
	public JTextField getParticipantField(){
		return participantsField;
	}
	
	public void setParticipantField(String participant){
		participantsField.setText(participant);
	}
	
	public JTextArea getDescriptionArea(){
		return descriptionBox;
	}
	
	public void setDescriptionArea(String description){
		descriptionBox.setText(description);
	}
	
	public DateTimePicker getCalendarFromPopPanel(){
		return calendarFromPopPanel;
	}
	
	public DateTimePicker getCalendarToPopPanel(){
		return calendarToPopPanel;
	}
	
	public JPopupMenu getFromPop(){
		return fromPop;
	}
	
	public JPopupMenu getToPop(){
		return toPop;
	}
	
	public JPopupMenu getParticipantPop(){
		return participantPop;
	}
	
	public DefaultListModel getListModel(){
		return listModel;
	}
	
	public DefaultListModel getPopListModel(){
		return popListModel;
	}
	
	
	
	private void createPanel(){
		
		
		renderer = new ParticipantRenderer();
		listModel = new DefaultListModel();
		
		participantList = new JList(listModel);
		participantList.setCellRenderer(renderer);
		
		participantList.setPreferredSize(new Dimension(300, 375));
		
		
		
		popListModel = new DefaultListModel();
		participantPopList = new JList(popListModel);
		
		participantPopList.setPreferredSize(new Dimension(315, 100));
		participantPopPanel.add(participantPopList);
		
//		String[] rooms = {"Room", "411, P15", "R2"};
        //TODO FIX ROOMS
		Room[] rooms = {new Room(1,"Drivhuset", "its", 800)};
		eventTitle = new JTextField("Title", 26);
		fromField = new JTextField("From", 10);
		toField = new JTextField("To", 10);
		roomBox = new JComboBox(rooms);
		descriptionBox = new JTextArea("Description");
		participantsField = new JTextField("Participants", 26);
		roomBox.setPreferredSize(new Dimension(290, 25));
		descriptionBox.setPreferredSize(new Dimension(290, 150));
		dash = new JLabel("-");
		dash.setSize(5, 1);
		
		fromPop = new JPopupMenu();
		fromPop.add(calendarFromPopPanel);
		
		toPop = new JPopupMenu();
		toPop.add(calendarToPopPanel);
		
		participantPop = new JPopupMenu();
		participantPop.add(participantPopPanel);
		
		//skal sjekke om brukeren er eventmanager
		if(true){
			
			gbc3.gridx = 0;	gbc3.gridy = 0;
			gbc3.gridwidth = 1;
			gbc3.gridheight = 1;
			buttonPanel.add(saveButton, gbc3);
			
			gbc3.gridx = 1;	gbc3.gridy = 0;
			gbc3.gridwidth = 1;
			gbc3.gridheight = 1;
			buttonPanel.add(cancelButton, gbc3);
			
			gbc3.gridx = 2;	gbc3.gridy = 0;
			gbc3.gridwidth = 1;
			gbc3.gridheight = 1;
			buttonPanel.add(deleteButton, gbc3);
			
			gbc2.gridx = 2;	gbc2.gridy = 3;
			gbc2.gridheight = 1;
			gbc2.gridwidth = 1;
			listPanel.add(deletePersonButton, gbc2);
			
			
			ArrayList<Employee> tempEmployeeArrayList = Employee.getAllEmployees(); 
			for(int i = 0; i < tempEmployeeArrayList.size(); i++){
				popListModel.addElement(tempEmployeeArrayList.get(i));
			}
			
			
			saveButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

//					setVisible(false);
//					
//					
//					int listSize = participantList.getModel().getSize();
//					ArrayList<Employee> participantsArray = new ArrayList<Employee>();
//					
//					for (int i = 0; i < listSize; i++) {
//						participantsArray.add((Employee) participantList.getModel().getElementAt(i));
//					}
				}
			});
		}
		
		else{
			eventTitle.setEditable(false);
			fromField.setEditable(false);
			toField.setEditable(false);
			roomBox.setEnabled(false);
			descriptionBox.setEditable(false);
			participantsField.setVisible(false);
			
			java.net.URL accept = getClass().getResource("/resources/icons/accept.png");
			ImageIcon acceptIcon = new ImageIcon(accept);
			acceptButton.setIcon(acceptIcon);
			
			java.net.URL decline = getClass().getResource("/resources/icons/decline.png");
			ImageIcon declineIcon = new ImageIcon(decline);
			declineButton.setIcon(declineIcon);

			gbc3.gridx = 0; gbc3.gridy = 0;
			gbc3.gridwidth = 1;
			gbc3.gridheight = 1;
			buttonPanel.add(acceptButton, gbc3);
			
			gbc1.gridx = 2; gbc1.gridy = 0;
			gbc1.gridwidth = 1;
			gbc1.gridheight = 1;
			buttonPanel.add(declineButton, gbc3);
			
			
			acceptButton.addActionListener(new ActionListener(){
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
				}
			});
			
			declineButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
		}
		
		gbc1.gridx = 0;	gbc1.gridy = 7;
		gbc1.gridheight = 1;
		gbc1.gridwidth = 3;
		eventPanel.add(buttonPanel, gbc1);
		
		gbc1.gridx = 0;	gbc1.gridy = 0;
		gbc1.gridwidth = 3;
		eventPanel.add(eventTitle, gbc1);
		
		gbc1.gridx = 0;	gbc1.gridy = 1;
		gbc1.gridwidth = 1;
		gbc1.gridheight = 1;
		gbc1.anchor = GridBagConstraints.EAST;
		eventPanel.add(fromField, gbc1);
		gbc1.gridx = 1;	gbc1.gridy = 1;
		eventPanel.add(dash,gbc1);
		
		gbc1.gridx = 2;	gbc1.gridy = 1;
		gbc1.gridwidth = 1;
		gbc1.gridheight = 1;
		eventPanel.add(toField, gbc1);
		
		gbc1.gridx = 0;	gbc1.gridy = 2;
		gbc1.gridwidth = 3;
		gbc1.gridheight = 1;
		gbc1.anchor = GridBagConstraints.CENTER;
		eventPanel.add(roomBox, gbc1);
		
		gbc1.gridx = 0;	gbc1.gridy = 3;
		gbc1.gridwidth = 3;
		eventPanel.add(participantsField, gbc1);
		
		gbc1.gridx = 0;	gbc1.gridy = 4;
		gbc1.gridheight = 2;
		gbc1.gridwidth = 3;
		eventPanel.add(descriptionBox, gbc1);
		
		gbc2.gridx = 0;	gbc2.gridy = 0;
		gbc2.gridheight = 3;
		gbc2.gridwidth = 3;
		listPanel.add(participantList, gbc2);
	}
	
	public JList getParticipantPopList(){
		return participantPopList;
	}
	
	public void removeParticipant(int i) {
		listModel.remove(i);
	}
	
	public void addParticipant(Employee person) {
		listModel.addElement(person);
	}
}

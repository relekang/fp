package no.ntnu.fp.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;

import no.ntnu.fp.client.gui.objects.AutoCompleteSearchField;
import no.ntnu.fp.common.Util;
import no.ntnu.fp.common.model.Employee;
import no.ntnu.fp.common.model.Room;

import javax.swing.BorderFactory;
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

public class EventView extends JFrame {
	
	public static int STD_COLUMNS = 26;
	
	private JList participantList, participantPopList;
	private JTextArea descriptionBox;
	private JComboBox roomBox;
	private JButton saveButton, cancelButton, deleteButton, acceptButton, declineButton, deletePersonButton;
	private JTextField eventTitle, fromField, toField, participantsField;
	private JPanel eventPanel, listPanel, eventViewPanel, buttonPanel, participantPopPanel;
	private DateTimePicker calendarToPopPanel, calendarFromPopPanel;
	private GridBagConstraints gbc1, gbc2, gbc3;
	private DefaultListModel listModel, popListModel;
	private ParticipantRenderer renderer;
	private JPopupMenu fromPop, toPop, participantPop;
	private Employee currentUser;
	private AutoCompleteSearchField<Employee> searchField;
	
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
		
		this.setContentPane(eventViewPanel);
		this.setResizable(false);
		this.pack();
	}
	
	private void createPanel(){
		renderer = new ParticipantRenderer();
		listModel = new DefaultListModel();
		
		participantList = new JList(listModel);
		participantList.setCellRenderer(renderer);
		participantList.setPreferredSize(new Dimension(300, 375));
		participantList.setMinimumSize(new Dimension(300, 375));
		
		popListModel = new DefaultListModel();
		participantPopList = new JList(popListModel);
		
		participantPopList.setPreferredSize(new Dimension(315, 100));
		participantPopPanel.add(participantPopList);
		
        //TODO FIX ROOMS
		Room[] rooms = {new Room(1,"Drivhuset", "its", 800)};
		eventTitle = new JTextField(STD_COLUMNS);
		eventTitle.setPreferredSize(new Dimension(290, 20));
		eventTitle.setMinimumSize(new Dimension(290, 20));
		
		fromField = new JTextField(STD_COLUMNS);
		fromField.setPreferredSize(new Dimension(290, 20));
		fromField.setMinimumSize(new Dimension(290, 20));
		
		toField = new JTextField(STD_COLUMNS);
		toField.setPreferredSize(new Dimension(290, 20));
		toField.setMinimumSize(new Dimension(290, 20));
		
		participantsField = new JTextField(STD_COLUMNS);
		participantsField.setPreferredSize(new Dimension(290, 20));
		participantsField.setMinimumSize(new Dimension(290, 20));
		
		roomBox = new JComboBox(rooms);
		roomBox.setPreferredSize(new Dimension(290, 20));
		roomBox.setMinimumSize(new Dimension(290, 20));
		
		descriptionBox = new JTextArea();
		descriptionBox.setPreferredSize(new Dimension(315, 150));
		descriptionBox.setMinimumSize(new Dimension(315, 150));
		
		buttonPanel.setPreferredSize(new Dimension(290, 30));
		buttonPanel.setMinimumSize(new Dimension(290, 30));
		
		java.net.URL save = getClass().getResource("/resources/icons/save.png");
		saveButton.setIcon(new ImageIcon(save));
		
		java.net.URL cancel = getClass().getResource("/resources/icons/delete.png");
		cancelButton.setIcon(new ImageIcon(cancel));
		
		java.net.URL delete = getClass().getResource("/resources/icons/delete.png");
		deleteButton.setIcon(new ImageIcon(delete));
		
		java.net.URL accept = getClass().getResource("/resources/icons/accept.png");
		acceptButton.setIcon(new ImageIcon(accept));
		
		java.net.URL decline = getClass().getResource("/resources/icons/decline.png");
		declineButton.setIcon(new ImageIcon(decline));
		
		eventTitle.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		fromField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		toField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		participantsField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		descriptionBox.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		
		fromPop = new JPopupMenu();
		fromPop.add(calendarFromPopPanel);
		
		toPop = new JPopupMenu();
		toPop.add(calendarToPopPanel);
		
		participantPop = new JPopupMenu();
		participantPop.add(participantPopPanel);
			
		ArrayList<Employee> tempEmployeeArrayList = Employee.getAllEmployees();
		searchField = new AutoCompleteSearchField<Employee>(tempEmployeeArrayList, 8);
		searchField.setPreferredSize(participantsField.getPreferredSize());
		System.out.println(searchField);
		for(int i = 0; i < tempEmployeeArrayList.size(); i++){
			popListModel.addElement(tempEmployeeArrayList.get(i));
		}
		
		gbc1.ipady = 7;
		
		gbc1.anchor = GridBagConstraints.EAST;
		gbc1.gridx = 0;	
		gbc1.gridy = 0;
		gbc1.gridwidth = 1;
		eventPanel.add(new JLabel("Title: "), gbc1);

		gbc1.gridx = 0;	
		gbc1.gridy = 1;
		gbc1.gridwidth = 1;
		eventPanel.add(new JLabel("From: "), gbc1);

		gbc1.gridx = 0;	
		gbc1.gridy = 2;
		gbc1.gridwidth = 1;
		eventPanel.add(new JLabel("To: "), gbc1);

		gbc1.gridx = 0;
		gbc1.gridy = 3;
		gbc1.gridwidth = 1;
		eventPanel.add(new JLabel("Room: "), gbc1);

		gbc1.gridx = 0;	
		gbc1.gridy = 4;
		gbc1.gridwidth = 1;
		eventPanel.add(new JLabel("Participants: "), gbc1);

		gbc1.gridx = 0;
		gbc1.gridy = 5;
		gbc1.gridwidth = 1;
		eventPanel.add(new JLabel("Description: "), gbc1);
		
		gbc1.anchor = GridBagConstraints.WEST;
		gbc1.gridx = 1;
		gbc1.gridy = 7;
		gbc1.gridheight = 1;
		gbc1.gridwidth = 3;
		eventPanel.add(buttonPanel, gbc1);
		
		gbc1.gridx = 1;	
		gbc1.gridy = 0;
		gbc1.gridwidth = 2;
		eventPanel.add(eventTitle, gbc1);
		
		gbc1.gridx = 1;
		gbc1.gridy = 1;
		gbc1.gridwidth = 2;
		gbc1.gridheight = 1;
		eventPanel.add(fromField, gbc1);
		
		gbc1.gridx = 1;
		gbc1.gridy = 2;
		gbc1.gridwidth = 2;
		gbc1.gridheight = 1;
		eventPanel.add(toField, gbc1);
		
		gbc1.gridx = 1;	
		gbc1.gridy = 3;
		gbc1.gridwidth = 2;
		gbc1.gridheight = 1;
		eventPanel.add(roomBox, gbc1);
		
		gbc1.gridx = 1;
		gbc1.gridy = 4;
		gbc1.gridheight = 1;
		gbc1.gridwidth = 2;
		eventPanel.add(participantsField, gbc1);

		gbc1.gridx = 1;
		gbc1.gridy = 5;
		gbc1.gridheight = 2;
		gbc1.gridwidth = 3;
		eventPanel.add(descriptionBox, gbc1);
		
		gbc1.anchor = GridBagConstraints.CENTER;
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.gridheight = 3;
		gbc2.gridwidth = 3;
		listPanel.add(participantList, gbc2);
	}
	
	public void addSaveButton(){
		gbc3.gridx = 0;	gbc3.gridy = 0;
		gbc3.gridwidth = 1;
		gbc3.gridheight = 1;
		buttonPanel.add(saveButton, gbc3);
		
	}
	
	public void addAcceptButton(){
		gbc3.gridx = 0; gbc3.gridy = 0;
		gbc3.gridwidth = 1;
		gbc3.gridheight = 1;
		buttonPanel.add(acceptButton, gbc3);
	}
	
	public void addDeclineButton(){
		gbc3.gridx = 1; gbc3.gridy = 0;
		gbc3.gridwidth = 1;
		gbc3.gridheight = 1;
		buttonPanel.add(declineButton, gbc3);
	}
	
	public void addCancelButton(){
		gbc3.gridx = 1;	gbc3.gridy = 0;
		gbc3.gridwidth = 1;
		gbc3.gridheight = 1;
		buttonPanel.add(cancelButton, gbc3);
	}
	
	public void addDeleteButton(){
		gbc3.anchor = GridBagConstraints.WEST;
		gbc3.gridx = 2;	gbc3.gridy = 0;
		gbc3.gridwidth = 1;
		gbc3.gridheight = 1;
		buttonPanel.add(deleteButton, gbc3);
	}
	
	public void addDeletePersonButton(){
		gbc2.gridx = 2;	gbc2.gridy = 3;
		gbc2.gridheight = 1;
		gbc2.gridwidth = 1;
		listPanel.add(deletePersonButton, gbc2);
	}
	
	
	public JList getParticipantList(){return participantList;}
	public JButton getAcceptButton(){return acceptButton;}
	public JButton getDeclineButton(){return declineButton;}
	public JButton getSaveButton(){return saveButton;}
	public JButton getCancelButton(){return cancelButton;}
	public JButton getDeleteButton(){return deleteButton;}
	public JButton getDeletePersonButton(){return deletePersonButton;}
	public JTextField getTitleField(){return eventTitle;}
	public void setTitleField(String title){eventTitle.setText(title);}
	public JTextField getFromField(){return fromField;}
	public void setFromField(String fromTime){fromField.setText(fromTime);}
	public JTextField getToField(){return toField;}
	public void setToField(String toTime){toField.setText(toTime);}
	public JComboBox getRoomBox(){return roomBox;}
	public void setRoomBox(Object room){roomBox.setSelectedItem(room);}	
	public JTextField getParticipantField(){return participantsField;}	
	public void setParticipantField(String participant){participantsField.setText(participant);}
	public JTextArea getDescriptionArea(){return descriptionBox;}
	public void setDescriptionArea(String description){descriptionBox.setText(description);}
	public DateTimePicker getCalendarFromPopPanel(){return calendarFromPopPanel;}
	public DateTimePicker getCalendarToPopPanel(){return calendarToPopPanel;}
	public JPopupMenu getFromPop(){return fromPop;}
	public JPopupMenu getToPop(){return toPop;}
	public JPopupMenu getParticipantPop(){return participantPop;}
	public DefaultListModel getListModel(){return listModel;}
	public DefaultListModel getPopListModel(){return popListModel;}
	public JList getParticipantPopList(){return participantPopList;}
	public void removeParticipant(int i) {listModel.remove(i);}
	public void addParticipant(Employee person) {listModel.addElement(person);}
    public void removeAllParticipants() {listModel.clear();}
    public JPanel getButtonPanel() {return buttonPanel;}
    public JPanel getListPanel(){return listPanel;}
}

package no.ntnu.fp.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Calendar;

import no.ntnu.fp.client.gui.objects.AutoCompleteSearchField;
import no.ntnu.fp.common.model.Employee;
import no.ntnu.fp.common.model.Room;
import no.ntnu.fp.common.model.Employee.Gender;

import javax.swing.*;

public class EventView extends JFrame {
	
	public static int STD_COLUMNS = 28;
	public static Dimension STD_FIELD_DIM = new Dimension(310, 20);
	
	private JList participantList;
	private JTextArea descriptionBox;
	private JComboBox roomBox;
	private JButton saveButton, cancelButton, deleteButton, acceptButton, declineButton, deletePersonButton;
	private JTextField eventTitle, fromField, toField;
	private JPanel eventPanel;
	private DateTimePicker calendarToPopPanel, calendarFromPopPanel;
	private GridBagConstraints gbc;
	private DefaultListModel listModel;
	private JPopupMenu fromPop, toPop;
	private AutoCompleteSearchField<Employee> participantField;
    private JLabel[] labels = {new JLabel("Title:"), new JLabel("From:"), new JLabel("To:"), new JLabel("Room:"), new JLabel("Participants:"), new JLabel("Description:")};

	public EventView(){
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 6, 10, 6);	
		eventPanel = new JPanel(new GridBagLayout());
		initButtons();
		initLabels();
		createView();
		this.setContentPane(eventPanel);
		this.pack();
	}

	private void createView(){
		addTitleField();
		addDateFields();
		addRoomField();
		createParticipantSearchField();
        setParticipantFieldVisible(true);
		addDescriptionField();
		addParticipantList();
		addSaveButton();
		addCancelButton();
		addDeleteButton();
	}

    public void setParticipantFieldVisible(boolean b) {
        if(b){
            gbc.gridx = 0; gbc.gridy = 4;
            eventPanel.add(labels[4], gbc);
            addParticipantSearchField();
        } else {
            eventPanel.remove(labels[4]);
            eventPanel.remove(getParticipantField());
        }
    }
    
	private void initButtons() {
		acceptButton = new JButton("Accept");
		declineButton = new JButton("Decline");
		saveButton = new JButton("Save");
		cancelButton = new JButton("Cancel");
		deleteButton = new JButton("Delete");
		deletePersonButton = new JButton("Remove person");
		saveButton.setIcon(new ImageIcon(getClass().getResource("/resources/icons/save.png")));
		cancelButton.setIcon(new ImageIcon(getClass().getResource("/resources/icons/delete.png")));
		deleteButton.setIcon(new ImageIcon(getClass().getResource("/resources/icons/delete.png")));
		acceptButton.setIcon(new ImageIcon(getClass().getResource("/resources/icons/accept.png")));
		declineButton.setIcon(new ImageIcon(getClass().getResource("/resources/icons/decline.png")));
	}
	
	private void addParticipantList() {
		listModel = new DefaultListModel();
		participantList = new JList(listModel);

		participantList.setBackground(GuiConstants.SWING_FRAME_GRAY);
		participantList.setCellRenderer(new ParticipantRenderer());
		participantList.setPreferredSize(new Dimension(250, 340));
		participantList.setMinimumSize(new Dimension(250, 340));
		participantList.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weighty = 3;
		gbc.weightx = 3;
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.gridheight = 6;
		eventPanel.add(participantList, gbc);
		addDeletePersonButton();
	}
	
	private void initLabels() {
		gbc.ipady = 7;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;	
		for(int i = 0; i < 6; i++) {
			gbc.gridy = i;
			eventPanel.add(labels[i], gbc);
		}
	}
	
	private void addTitleField() {
		eventTitle = new JTextField(STD_COLUMNS);
		eventTitle.setPreferredSize(STD_FIELD_DIM);
		eventTitle.setMinimumSize(STD_FIELD_DIM);
		eventTitle.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;	
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		eventPanel.add(eventTitle, gbc);
	}
	
	private void addDateFields() {
		fromField = new JTextField(STD_COLUMNS);
		fromField.setPreferredSize(STD_FIELD_DIM);
		fromField.setMinimumSize(STD_FIELD_DIM);
		fromField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		fromPop = new JPopupMenu();
		calendarFromPopPanel = new DateTimePicker();
		fromPop.add(calendarFromPopPanel);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		eventPanel.add(fromField, gbc);
		
		toField = new JTextField(STD_COLUMNS);
		toField.setPreferredSize(STD_FIELD_DIM);
		toField.setMinimumSize(STD_FIELD_DIM);
		toField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		toPop = new JPopupMenu();
		calendarToPopPanel = new DateTimePicker();
		toPop.add(calendarToPopPanel);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		eventPanel.add(toField, gbc);
	}
	
	private void addRoomField() {
        Room[] rooms = {new Room(1,"Drivhuset", "its", 800)};
		roomBox = new JComboBox(rooms);
		roomBox.setPreferredSize(STD_FIELD_DIM);
		roomBox.setMinimumSize(STD_FIELD_DIM);	
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		eventPanel.add(roomBox, gbc);
	}

	private void createParticipantSearchField() {
		participantField = new AutoCompleteSearchField<Employee>(Employee.getAllEmployees(), 5);

		participantField.setMinimumSize(STD_FIELD_DIM);
		participantField.setPreferredSize(STD_FIELD_DIM);
		participantField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
	}
	private void addParticipantSearchField() {
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		eventPanel.add(participantField, gbc);
	}

	private void addDescriptionField() {
		descriptionBox = new JTextArea(3, STD_COLUMNS);
		descriptionBox.setPreferredSize(new Dimension(290, 100));
		descriptionBox.setMinimumSize(new Dimension(290, 100));
		descriptionBox.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		eventPanel.add(descriptionBox, gbc);
	}
	
	public void addAcceptButton(){
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		eventPanel.add(acceptButton, gbc);
	}
	
	public void addDeclineButton(){
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 3;	
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		eventPanel.add(declineButton, gbc);
	}
	
	public void addSaveButton(){
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;	
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		eventPanel.add(saveButton, gbc);
		
	}
	
	public void addCancelButton(){
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 2;	
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		eventPanel.add(cancelButton, gbc);
	}
	
	public void addDeleteButton(){
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 3;	
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		eventPanel.add(deleteButton, gbc);
	}
	
	public void addDeletePersonButton(){
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 4;	
		gbc.gridy = 6;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		eventPanel.add(deletePersonButton, gbc);
	}

    public void setRoomChoices(ArrayList<Room> list){
        roomBox.removeAllItems();
        for(Room r:list){
            roomBox.addItem(r);
        }
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
	public AutoCompleteSearchField<Employee> getParticipantField(){return participantField;}	
	public JTextArea getDescriptionArea(){return descriptionBox;}
	public void setDescriptionArea(String description){descriptionBox.setText(description);}
	public DateTimePicker getCalendarFromPopPanel(){return calendarFromPopPanel;}
	public DateTimePicker getCalendarToPopPanel(){return calendarToPopPanel;}
	public JPopupMenu getFromPop(){return fromPop;}
	public JPopupMenu getToPop(){return toPop;}
	public DefaultListModel getListModel(){return listModel;}
	public void removeParticipant(int i) {listModel.remove(i);}
	public void addParticipant(Employee person) {listModel.addElement(person);}
    public void removeAllParticipants() {listModel.clear();}

    
}
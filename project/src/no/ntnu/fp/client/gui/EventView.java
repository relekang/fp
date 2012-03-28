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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class EventView extends JFrame {
	
	public static int STD_COLUMNS = 28;
	public static Dimension STD_FIELD_DIM = new Dimension(310, 20);
	
	private JList participantList;//, participantPopList;
	private JTextArea descriptionBox;
	private JComboBox roomBox;
	private JButton saveButton, cancelButton, deleteButton, acceptButton, declineButton, deletePersonButton;
	private JTextField eventTitle, fromField, toField;//, participantsField;
	private JPanel eventPanel;//, participantPopPanel;//listPanel, buttonPanel, eventViewPanel 
	private DateTimePicker calendarToPopPanel, calendarFromPopPanel;
	private GridBagConstraints gbc;
	private DefaultListModel listModel;//, popListModel;
	private JPopupMenu fromPop, toPop;//, participantPop;
	private AutoCompleteSearchField<Employee> participantField;
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
//		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		EventView view = new EventView();
		view.setVisible(true);
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.pack();
	}
	
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
		addParticipantSearchField();
		addDescriptionField();
		addParticipantList();
//		addAcceptButton();
//		addDeclineButton();
		addSaveButton();
		addCancelButton();
		addDeleteButton();
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
//		participantList.setBackground(GuiConstants.SWING_FRAME_GRAY);
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
		String[] labels = {"Title:", "From:", "To:", "Room:", "Participants:", "Description:"};
		for(int i = 0; i < 6; i++) {
			gbc.gridy = i;
			eventPanel.add(new JLabel(labels[i]), gbc);
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
//		TODO:fix rooms
//		ArrayList<Employee> tempEmployeeArrayList = Employee.getAllEmployees();
//		searchField = new AutoCompleteSearchField<Employee>(tempEmployeeArrayList, 8);
//		searchField.setPreferredSize(participantsField.getPreferredSize());
//		System.out.println(searchField);
//		for(int i = 0; i < tempEmployeeArrayList.size(); i++){
//			popListModel.addElement(tempEmployeeArrayList.get(i));
//		}
		gbc.gridx = 1;	
		gbc.gridy = 3;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		eventPanel.add(roomBox, gbc);
	}

	private void addParticipantSearchField() {
//		popListModel = new DefaultListModel();
//		participantPopList = new JList(popListModel);
//		participantPopList.setPreferredSize(new Dimension(315, 100));
//		participantPopPanel = new JPanel();
//		participantPopPanel.add(participantPopList);
//		participantPop = new JPopupMenu();
//		participantPop.add(participantPopPanel);
//		participantsField = new JTextField(STD_COLUMNS);
		
//		ArrayList<Employee> l = new ArrayList<Employee>();
//		char[] chars = {'v', 'a', 'g', 'q', 'k', 'h', 'i', 'i','v', 'a', 'g', 'q', 'k', 'h', 'i', 'i',
//				'v', 'a', 'g', 'q', 'k', 'h', 'i', 'i','v', 'a', 'g', 'q', 'k', 'h', 'i', 'i'};		
//		for(int i = 0; i < chars.length; i++) {
//			l.add(new Employee(""+chars[i]+""+chars[i%2]+""+chars[i%4]+""+chars[(chars.length-1-i)%5]+
//					""+chars[i]+""+chars[chars.length-1-i]+""+chars[i]+""+chars[chars.length-1-i], 
//					"g@mail.com", Calendar.getInstance().getTime(), Gender.MALE));
//		}
		participantField = new AutoCompleteSearchField<Employee>(STD_COLUMNS, 5);
		
		participantField.setMinimumSize(STD_FIELD_DIM);
		participantField.setPreferredSize(STD_FIELD_DIM);
		participantField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
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
//	public void setParticipantField(String participant){participantsField.setText(participant);}
	public JTextArea getDescriptionArea(){return descriptionBox;}
	public void setDescriptionArea(String description){descriptionBox.setText(description);}
	public DateTimePicker getCalendarFromPopPanel(){return calendarFromPopPanel;}
	public DateTimePicker getCalendarToPopPanel(){return calendarToPopPanel;}
	public JPopupMenu getFromPop(){return fromPop;}
	public JPopupMenu getToPop(){return toPop;}
	public DefaultListModel getListModel(){return listModel;}
//	public JPopupMenu getParticipantPop(){return participantPop;}
//	public DefaultListModel getPopListModel(){return popListModel;}
//	public JList getParticipantPopList(){return participantPopList;}
	public void removeParticipant(int i) {listModel.remove(i);}
	public void addParticipant(Employee person) {listModel.addElement(person);}
    public void removeAllParticipants() {listModel.clear();}
//    public JPanel getButtonPanel() {return buttonPanel;}
//    public JPanel getListPanel(){return listPanel;}
}
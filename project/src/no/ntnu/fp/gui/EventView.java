package no.ntnu.fp.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;

import java.util.Date;

import no.ntnu.fp.controller.EventViewController;
import no.ntnu.fp.model.Employee;
import no.ntnu.fp.model.Room;

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

public class EventView extends JFrame implements ComponentListener, MouseListener, KeyListener{
	
	JList participantList, participantPopList;
	JTextArea descriptionBox;
	JComboBox roomBox;
	JButton saveButton, cancelButton, deleteButton, acceptButton, declineButton, deletePersonButton;
	JTextField eventTitle, fromField, toField, participantsField;
	JPanel eventPanel, listPanel, eventViewPanel, buttonPanel, calenderToPopPanel,calenderFromPopPanel, participantPopPanel;
	JLabel dash;
	GridBagConstraints gbc1, gbc2, gbc3;
	DefaultListModel listModel, popListModel;
	ParticipantRenderer renderer;
	Employee user;
	JPopupMenu fromPop, toPop, participantPop;
	ArrayList<String> popList, popListFound;
	boolean shown;
	
	public EventView(){
		gbc1 = new GridBagConstraints();
		gbc2 = new GridBagConstraints();
		gbc3 = new GridBagConstraints();
		
		gbc1.insets = new Insets(15, 15, 15, 15);
		gbc2.insets = new Insets(15, 15, 15, 15);
		gbc3.insets = new Insets(15, 15, 15, 15);
		
//		user = EventController.getEmployee();
		
		eventPanel = new JPanel();
		eventPanel.setLayout(new GridBagLayout());
		listPanel = new JPanel();
		listPanel.setLayout(new GridBagLayout());
		eventViewPanel = new JPanel(new GridBagLayout());
		eventViewPanel.setLayout(new GridBagLayout());
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		calenderToPopPanel = new DateTimePicker(this);
		calenderFromPopPanel = new DateTimePicker(this);
		participantPopPanel = new JPanel();
		
		createPanel();
		
		eventViewPanel.add(eventPanel);
		eventViewPanel.add(listPanel);
		
		this.add(eventViewPanel);
		this.pack();
	}
	
	@SuppressWarnings("unused")
	private void createPanel(){
		
		//TODO midlertidig eksempler
		Employee hans = new Employee("Hans", "heihei", new Date(1998,2,2), Employee.Gender.MALE);
		Employee geir = new Employee("Geir", "heihei", new Date(1998,2,2), Employee.Gender.MALE);
		Employee bjarne = new Employee("Bjarne", "heihei", new Date(1998,2,2), Employee.Gender.MALE);
		Employee arne = new Employee("Arne", "heihei", new Date(1998,2,2), Employee.Gender.MALE);
		//
		
		renderer = new ParticipantRenderer();
		listModel = new DefaultListModel();
		
		listModel.addElement(arne);
		listModel.addElement(hans);
		listModel.addElement(geir);
		listModel.addElement(bjarne);

		
		participantList = new JList(listModel);
		participantList.setCellRenderer(renderer);
		
		participantList.setPreferredSize(new Dimension(300, 375));
		
		popList = new ArrayList<String>();
		popList.add("arne");	popList.add("bjarne");	popList.add("ole");	popList.add("mats");
		popListFound = new ArrayList<String>();
		
		popListModel = new DefaultListModel();
		participantPopList = new JList(popListModel);
		
		for (int i = 0; i < popList.size(); i++) {
			popListModel.addElement(popList.get(i));
		}
		
		participantPopList.setPreferredSize(new Dimension(315, 100));
		participantPopPanel.add(participantPopList);
		
		
		String[] rooms = {"Room", "411, P15", "R2"};
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
		
		//Add listeners to DateTimePicker
		
		//TODO creating popups for from, to and participant field
		fromPop = new JPopupMenu();
		fromPop.add(calenderFromPopPanel);
		
		toPop = new JPopupMenu();
		toPop.add(calenderToPopPanel);
		
		participantPop = new JPopupMenu();
		participantPop.add(participantPopPanel);
		
		
		this.addComponentListener(this);
		
		//skal sjekke om brukeren er eventmanager
		if(true){
			saveButton = new JButton("Save");
			cancelButton = new JButton("Cancel");
			deleteButton = new JButton("Delete");
			deletePersonButton = new JButton("Remove person");
			
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
			
			fromField.addMouseListener(this);
			toField.addMouseListener(this);
			
			participantsField.addMouseListener(this);
			
			participantsField.addKeyListener(this);
			
			participantPopList.addListSelectionListener(new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent e) {
//					if(participantPopList.getSelectedIndex() =! -1){
						participantsField.setText((String) participantPopList.getSelectedValue());
//					}
				}
			});
			
			saveButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					setVisible(false);
					
					
					int listSize = participantList.getModel().getSize();
					ArrayList<Employee> participantsArray = new ArrayList<Employee>();
					
					for (int i = 0; i < listSize; i++) {
						participantsArray.add((Employee) participantList.getModel().getElementAt(i));
					}
					
//					EventViewController.saveEvent(eventTitle.getText(), toField.getText(), 
//												   fromField.getText(),descriptionBox.getText(), 
//												   participantsArray, admin, ID);

					//TODO: Should save event to database
				}
			});
			
			cancelButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
			
			deleteButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					
					//TODO: Should delete event from database
				}
			});
			
			deletePersonButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int temp = participantList.getSelectedIndex();
					
					if(temp > -1){
						participantList.setSelectedIndex(temp - 1);
						removeParticipant(temp);
					}
//					else
//					{
						//list.setSelectedIndex(-1);
						//persons.setModel(new Person());
						//removePerson(temp);
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
			
			acceptButton = new JButton("Accept");
			declineButton = new JButton("Decline");
			
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
		//gbc1.anchor = GridBagConstraints.WEST;
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
	
	public void setTitle(String title){
		eventTitle.setText(title);
	}
	
	public String getTitle(){
		return eventTitle.getText();
	}
	
	public void setToDate(String toDate){
		toField.setText(toDate);
	}
	
	public String getToDate(){
		return toField.getText();
	}
	
	public void setFromDate(String fromDate){
		fromField.setText(fromDate);
	}
	
	public String getFromDate(){
		return fromField.getText();
	}
	
	public void setRoom(Room room){
		roomBox.setSelectedItem(room);
	}
	
	public Room getRoom(){
		return (Room) roomBox.getSelectedItem();
	}
	
	public void setDescription(String description){
		descriptionBox.setText(description);
	}
	
	public String getDescription(){
		return descriptionBox.getText();
	}
	
	public void removeParticipant(int i) {
		listModel.remove(i);
	}
	
	public void addParticipant(Employee person) {
		listModel.addElement(person);
	}
	
	@Override
	public void componentHidden(ComponentEvent arg0) {
		
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		
		if(shown){
			fromPop.show(fromField, 0, 30);
			toPop.show(toField, 0, 30);
			participantPop.show(participantsField, 0, 30);
		}
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		
		if(shown){
			fromPop.show(fromField, 0, 30);
			toPop.show(toField, 0, 30);
			participantPop.show(participantsField, 0, 30);
		}
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		shown = true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == fromField){
			
			fromPop.show(fromField, 0, 30);
			fromField.setText("");
		}
		else if(e.getSource() == toField){
			toPop.show(toField, 0, 30);
			toField.setText("");
		}
		else if (e.getSource() == participantsField) {
			participantPop.show(participantsField, 0, 30);
			participantsField.setText("");
			participantsField.grabFocus();
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
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
			if (e.getSource() != participantsField) {
				fromField.setText(((DateTimePicker) calenderFromPopPanel).getHourField()+":"+((DateTimePicker) calenderFromPopPanel).getMinField());
				toField.setText(((DateTimePicker) calenderToPopPanel).getHourField()+":"+((DateTimePicker) calenderToPopPanel).getMinField());
			}
			else{
				for (int i = 0; i < popList.size(); i++) {
					if(participantsField.getText().length() <= 1){
						if(popList.get(i).charAt(participantsField.getText().length() - 1) == participantsField.getText().charAt(participantsField.getText().length() - 1)){
							popListFound.add(popList.get(i));
						}
					}
				}
				for (int y = 0; y < popListFound.size(); y++) {
					for (int i = 0; i < popListModel.size(); i++) {
						if(popListModel.get(i) != popListFound.get(y)){
							popListModel.remove(i);
						}
					}
				}
			}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


}

package no.ntnu.fp.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import no.ntnu.fp.model.Employee;
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

public class EventView extends JFrame implements ComponentListener, MouseListener{
	
	JList participantList;
	JTextArea descriptionBox;
	JComboBox roomBox;
	JButton saveButton, cancelButton, deleteButton, acceptButton, declineButton, deletePersonButton;
	JTextField eventTitle, fromField, toField, participantsField;
	JPanel eventPanel, listPanel, eventViewPanel, buttonPanel, calenderToPopPanel,calenderFromPopPanel;
	JLabel dash;
	GridBagConstraints gbc1, gbc2, gbc3;
	DefaultListModel listModel;
	ParticipantRenderer renderer;
	Employee user;
	JPopupMenu fromPop, toPop;
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
		calenderToPopPanel = new DateTimePicker();
		calenderFromPopPanel = new DateTimePicker();
		
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
		
		//TODO creating popups for from and to field
		fromPop = new JPopupMenu();
		fromPop.add(calenderFromPopPanel);
		
		
		
		toPop = new JPopupMenu();
		toPop.add(calenderToPopPanel);
		
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
			
			saveButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					
					
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
			
			
			acceptButton.addActionListener(new ActionListener() {
				
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
		gbc1.anchor = GridBagConstraints.CENTER
		;
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
			fromPop.setLocation(fromField.getLocationOnScreen().x, fromField.getLocationOnScreen().y+30);
			toPop.setLocation(toField.getLocationOnScreen().x, toField.getLocationOnScreen().y+30);
		}
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		
		if(shown){
			fromPop.setLocation(fromField.getLocationOnScreen().x, fromField.getLocationOnScreen().y+30);
			toPop.setLocation(toField.getLocationOnScreen().x, toField.getLocationOnScreen().y+30);
		}
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		System.out.println("test");
		shown = true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == fromField){
			fromPop.setLocation(fromField.getLocationOnScreen().x, fromField.getLocationOnScreen().y+30);
			fromPop.setVisible(true);
			fromField.setText("");
		}
		else if(e.getSource() == toField){
			toPop.setVisible(true);
			toPop.setLocation(toField.getLocationOnScreen().x, toField.getLocationOnScreen().y+30);
			toField.setText("");
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}

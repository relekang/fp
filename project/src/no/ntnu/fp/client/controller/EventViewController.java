package no.ntnu.fp.client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.ntnu.fp.client.gui.EventView;
import no.ntnu.fp.common.model.Employee;
import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.common.model.Room;
import no.ntnu.fp.server.storage.db.EventHandler;

public class EventViewController implements PropertyChangeListener, KeyListener, MouseListener, ActionListener {
	
	private EventView eventView;
	private Employee currentUser;
	private Calendar fromDate, toDate;
	private Event event;
	private ArrayList<String> popList, popListFound;
	
	
	public EventViewController(Employee currentUser, EventView view){
		this.currentUser = currentUser;
		this.eventView = view;
		
		event = new Event(currentUser);

        toDate = Calendar.getInstance();
        fromDate = Calendar.getInstance();
        
        toDate.setTime(event.getDateTo());
        fromDate.setTime(event.getDateFrom());

		popList = new ArrayList<String>();
		popListFound = new ArrayList<String>();

		eventView = new EventView();
		eventView.setVisible(false);

		eventView.getCalendarToPopPanel().getHourTextField().addKeyListener(this);
		eventView.getCalendarToPopPanel().getMinuteTextField().addKeyListener(this);
		eventView.getCalendarFromPopPanel().getHourTextField().addKeyListener(this);
		eventView.getCalendarFromPopPanel().getMinuteTextField().addKeyListener(this);
		eventView.getSaveButton().addActionListener(this);
		eventView.getCancelButton().addActionListener(this);
		eventView.getDeleteButton().addActionListener(this);
		eventView.getAcceptButton().addActionListener(this);
		eventView.getDeclineButton().addActionListener(this);
		eventView.getDeletePersonButton().addActionListener(this);

		eventView.getFromField().addMouseListener(this);
		eventView.getToField().addMouseListener(this);

		eventView.getTitleField().addMouseListener(this);
		eventView.getDescriptionArea().addMouseListener(this);
		eventView.getParticipantField().addMouseListener(this);

		eventView.getParticipantField().addKeyListener(this);

		for (int i = 0; i < popList.size(); i++) {
			eventView.getPopListModel().addElement(popList.get(i));
		}
		
		eventView.getCalendarToPopPanel().getOverviewCalendarPanel().addPCL(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Calendar cal = (Calendar) evt.getNewValue();
				toDate.set(Calendar.YEAR, cal.get(Calendar.YEAR));
				toDate.set(Calendar.MONTH, cal.get(Calendar.MONTH));
				toDate.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+7);//TODO: Calendar.DAY_OF_MONTH)+7 gives the right result for now..
				event.setDateTo(toDate.getTime());
				eventView.getToField().setText(toDate.getTime().toString());
			}
		});

		eventView.getCalendarFromPopPanel().getOverviewCalendarPanel().addPCL(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Calendar cal = (Calendar) evt.getNewValue();
				fromDate.set(Calendar.YEAR, cal.get(Calendar.YEAR));
				fromDate.set(Calendar.MONTH, cal.get(Calendar.MONTH));
				fromDate.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+7);//TODO: Calendar.DAY_OF_MONTH)+7 gives the right result for now..
				event.setDateTo(fromDate.getTime());
				eventView.getFromField().setText(fromDate.getTime().toString());
			}
		});

		eventView.getParticipantPopList().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(eventView.getParticipantPopList().getSelectedIndex() != -1){
					eventView.getListModel().addElement(eventView.getParticipantPopList().getSelectedValue());
					eventView.getPopListModel().removeElement(eventView.getParticipantPopList().getSelectedValue());
					eventView.getParticipantField().grabFocus();
				}
			}
		});
		
		this.eventView.setLocationRelativeTo(ClientApplication.getMainViewController().getMainView());
    }

	public void setCurrentUser(Employee currentUser){
		this.currentUser = currentUser;
	}

	public void showEvent(Event event){
		eventView.setLocationRelativeTo(ClientApplication.getMainViewController().getMainView());
		if(currentUser != event.getAdmin()){
			eventView.getTitleField().setEditable(false);
			eventView.getFromField().setEditable(false);
			eventView.getToField().setEditable(false);
			eventView.getRoomBox().setEnabled(false);
			eventView.getDescriptionArea().setEditable(false);
			eventView.getParticipantField().setVisible(false);
			eventView.getButtonPanel().remove(eventView.getSaveButton());
			eventView.getButtonPanel().remove(eventView.getCancelButton());
			eventView.getButtonPanel().remove(eventView.getDeleteButton());
			eventView.getListPanel().remove(eventView.getDeletePersonButton());
			eventView.addAcceptButton();
			eventView.addDeclineButton();
		}
		else{
			eventView.getTitleField().setEditable(true);
			eventView.getFromField().setEditable(true);
			eventView.getToField().setEditable(true);
			eventView.getRoomBox().setEnabled(true);
			eventView.getDescriptionArea().setEditable(true);
			eventView.getParticipantField().setVisible(true);
			eventView.getButtonPanel().remove(eventView.getAcceptButton());
			eventView.getButtonPanel().remove(eventView.getDeclineButton());
			eventView.addSaveButton();
			eventView.addCancelButton();
			eventView.addDeleteButton();
			eventView.addDeletePersonButton();
		}
		setEvent(event);
		eventView.setVisible(true);
	}

	public void setVisible(boolean visible){
		eventView.setVisible(visible);
	}

	private void setEvent(Event event){
		this.event = event;
		eventView.getTitleField().setText(event.getTitle());
		eventView.setFromField(event.getDateFrom().toString());
		eventView.setToField(event.getDateTo().toString());
		eventView.setDescriptionArea(event.getDescription());

        eventView.removeAllParticipants();
		for (int i = 0; i < event.getParticipants().size(); i++) {
			eventView.addParticipant(event.getParticipants().get(i));
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
//		TODO:
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
	    JTextField endHourField = eventView.getCalendarToPopPanel().getHourTextField();
	    JTextField endMinuteField = eventView.getCalendarToPopPanel().getMinuteTextField();
	    JTextField startHourField = eventView.getCalendarFromPopPanel().getHourTextField();
	    JTextField startMinuteField = eventView.getCalendarFromPopPanel().getHourTextField();
	    JTextField participantField = eventView.getParticipantField();


	    if (e.getSource() == endHourField) {
	        toDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endHourField.getText()));
	    }
	    else if (e.getSource() == endMinuteField) {
	        toDate.set(Calendar.MINUTE, Integer.parseInt(endMinuteField.getText()));
	    }
	    if (e.getSource() == startHourField) {
	        fromDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startHourField.getText()));
	    }
	    else if (e.getSource() == startMinuteField) {
	        fromDate.set(Calendar.MINUTE, Integer.parseInt(startMinuteField.getText()));
	    }
	    else if(e.getSource() == participantField){
	        updateAutocompletionResults();
	    }
	    updateToAndFromFieldDisplay();
	}

	private void updateAutocompletionResults() {
	    String participantText = eventView.getParticipantField().getText();
	    eventView.getPopListModel().clear(); // Vi clearer hele lista og bare fylle den pŒ nytt hver gang..
	    for (String employee : popList) {
	        if (employee.contains(participantText))
	                eventView.getPopListModel().addElement(employee);
	    } 
	}

	private void updateToAndFromFieldDisplay() {
	    eventView.getToField().setText(toDate.getTime().toString());
	    eventView.getFromField().setText(fromDate.getTime().toString());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getSource() == eventView.getTitleField()) {
			if (eventView.getTitleField().getText().equals("Title")) {
				eventView.getTitleField().setText("");
			}
		}
		else if(e.getSource() == eventView.getFromField()){
			if(eventView.getFromField().isEditable())
				eventView.getFromPop().show(eventView.getFromField(), 0, 30);
			
			if (eventView.getFromField().getText().equals("From")) {
				eventView.getFromField().setText("");
			}
			eventView.getFromField().grabFocus();
		}
		else if(e.getSource() == eventView.getToField()){
			if(eventView.getToField().isEditable())
				eventView.getToPop().show(eventView.getToField(), 0, 30);
			
			if (eventView.getToField().getText().equals("To")) {
				eventView.getToField().setText("");
			}
			eventView.getToField().grabFocus();
		}
		else if (e.getSource() == eventView.getParticipantField()) {
			eventView.getParticipantPop().show(eventView.getParticipantField(), 0, 30);
			eventView.getParticipantField().setText("");
			eventView.getParticipantField().grabFocus();
		}
		else if(e.getSource() == eventView.getTitleField()){
			eventView.getTitleField().setText("");
		}
		else if(e.getSource() == eventView.getDescriptionArea()){
			eventView.getDescriptionArea().setText("");
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

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == eventView.getSaveButton()){
			this.setVisible(false);
			event.setTitle(eventView.getTitleField().getText());
			event.setDescription(eventView.getDescriptionArea().getText());
			ArrayList<Employee> participants = new ArrayList<Employee>();
			for (int i = 0; i < eventView.getListModel().size(); i++) {
				participants.add((Employee) eventView.getListModel().get(i));
			}
			event.setParticipants(participants);
			event.setRoom((Room) eventView.getRoomBox().getSelectedItem());
			System.out.println("Save called in eventViewController: " + event + " - " + event.getDateFrom()+  " : " + event.getDateTo());
	        event.save();
		}
		else if (e.getSource() == eventView.getCancelButton()) {
			this.setVisible(false);
		}
		else if (e.getSource() == eventView.getDeleteButton()) {
			this.setVisible(false);
            event.delete();
		}
		else if (e.getSource() == eventView.getDeletePersonButton()) {
			int temp = eventView.getParticipantList().getSelectedIndex();
			Employee tempEmployee = (Employee) eventView.getParticipantList().getSelectedValue(); 
			if(temp > -1){
				eventView.getPopListModel().addElement(tempEmployee);
				eventView.getParticipantList().setSelectedIndex(temp - 1);
				eventView.removeParticipant(temp);
			}

		}
	}
}

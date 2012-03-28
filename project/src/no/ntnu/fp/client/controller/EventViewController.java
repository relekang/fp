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

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.ntnu.fp.client.gui.EventView;
import no.ntnu.fp.common.Util;
import no.ntnu.fp.common.model.Employee;
import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.common.model.Room;

public class EventViewController implements PropertyChangeListener, KeyListener, MouseListener, ActionListener {
	
	private EventView eventView;
	private Employee currentUser;
	private Calendar fromDate, toDate;
	private Event event;

	public EventViewController(Employee currentUser, EventView view){
		this.currentUser = currentUser;
		this.eventView = view;
		
		event = new Event(currentUser);
        toDate = Util.getCalendar();
        fromDate = Util.getCalendar();
        toDate.setTime(event.getDateTo());
        fromDate.setTime(event.getDateFrom());

        eventView = new EventView();
		eventView.setVisible(false);
        eventView.setRoomChoices(Room.getRooms());
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
		eventView.getCalendarToPopPanel().getOverviewCalendarPanel().addPCL(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Calendar cal = (Calendar) evt.getNewValue();
				toDate.set(Calendar.YEAR, cal.get(Calendar.YEAR));
				toDate.set(Calendar.MONTH, cal.get(Calendar.MONTH));
				toDate.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+7);//TODO: Calendar.DAY_OF_MONTH)+7 gives the right result for now..
				event.setDateTo(toDate.getTime());
				eventView.getToField().setText(Util.dateTimeToString(toDate.getTime()));
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
				eventView.getFromField().setText(Util.dateTimeToString(fromDate.getTime()));
			}
		});
		
		eventView.getParticipantField().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int index = eventView.getParticipantField().getSelectedIndex();
				if(index > -1) {
                    if(!eventView.getListModel().contains(eventView.getParticipantField().getSelectedValue())){
                        eventView.getListModel().addElement(eventView.getParticipantField().getSelectedValue());
                        eventView.getParticipantField().removeElementAt(index);
                    }
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
		boolean isAdmin = currentUser.equals(event.getAdmin());
		Util.print("IS ADMIN:(Admin " + event.getAdmin().getId() + ") " + isAdmin);
		eventView.getTitleField().setEditable(isAdmin);
		eventView.getFromField().setEditable(isAdmin);
		eventView.getToField().setEditable(isAdmin);
		eventView.getRoomBox().setEnabled(isAdmin);
		eventView.getDescriptionArea().setEditable(isAdmin);
		eventView.getParticipantField().setVisible(isAdmin);
        eventView.setParticipantFieldVisible(isAdmin);
		if(!isAdmin) {
			eventView.remove(eventView.getSaveButton());
			eventView.remove(eventView.getCancelButton());
			eventView.remove(eventView.getDeleteButton());
			eventView.remove(eventView.getDeletePersonButton());
			eventView.addAcceptButton();
			eventView.addDeclineButton();
		} else {
			eventView.remove(eventView.getAcceptButton());
			eventView.remove(eventView.getDeclineButton());
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
		eventView.setFromField(Util.dateTimeToString(event.getDateFrom()));
		eventView.setToField(Util.dateTimeToString(event.getDateTo()));
		eventView.setDescriptionArea(event.getDescription());
        eventView.setRoomChoices(Room.getRooms());
        eventView.setRoomBox(event.getRoom());
        eventView.removeAllParticipants();
        for(Employee e:event.getParticipants()){
            eventView.getParticipantField().removeElement(e);
        }
		for (int i = 0; i < event.getParticipants().size(); i++) {
			eventView.addParticipant(event.getParticipants().get(i));
		}
	}
	


	@Override
	public void keyReleased(KeyEvent e) {
	    JTextField endHourField = eventView.getCalendarToPopPanel().getHourTextField();
	    JTextField endMinuteField = eventView.getCalendarToPopPanel().getMinuteTextField();
	    JTextField startHourField = eventView.getCalendarFromPopPanel().getHourTextField();
	    JTextField startMinuteField = eventView.getCalendarFromPopPanel().getHourTextField();

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
	    updateToAndFromFieldDisplay();
	}

	private void updateToAndFromFieldDisplay() {
	    eventView.getToField().setText(Util.dateTimeToString(toDate.getTime()));
	    eventView.getFromField().setText(Util.dateTimeToString(fromDate.getTime()));
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
		else if(e.getSource() == eventView.getTitleField()){
			eventView.getTitleField().setText("");
		}
		else if(e.getSource() == eventView.getDescriptionArea()){
			eventView.getDescriptionArea().setText("");
		}
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
			System.out.println("Save called in eventViewController: " + event + " - " + event.getDateFrom() + " : " + event.getDateTo());
	        event.save();
		}
		else if (e.getSource() == eventView.getCancelButton()) {
			this.setVisible(false);
		}
		else if (e.getSource() == eventView.getDeleteButton()) {
			this.setVisible(false);
            event.delete();
		}
		else if (e.getSource() == eventView.getAcceptButton()) {
			this.setVisible(false);
            currentUser.acceptEvent(event);
		}
		else if (e.getSource() == eventView.getDeclineButton()) {
			this.setVisible(false);
            currentUser.declineEvent(event);
		}
        else if (e.getSource() == eventView.getDeletePersonButton()) {
			int index = eventView.getParticipantList().getSelectedIndex();
			Employee tempEmployee = (Employee) eventView.getParticipantList().getSelectedValue(); 
			if(index > -1){
				eventView.getParticipantField().addElement(tempEmployee);
				eventView.getParticipantList().setSelectedIndex(index - 1);
				eventView.removeParticipant(index);
			}

		}
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void propertyChange(PropertyChangeEvent evt) {}

}

package no.ntnu.fp.client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.ntnu.fp.client.gui.EventView;
import no.ntnu.fp.common.model.Employee;
import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.common.model.Room;
import no.ntnu.fp.server.storage.db.EventHandler;

public class EventViewController implements PropertyChangeListener, KeyListener, MouseListener, ComponentListener, ActionListener {
	
	private EventView view;
	private Employee currentUser;
	private Event event;
	private String toDate, toHour, toMinute, toTime, fromDate, fromHour, fromMinute, fromTime;
	private ArrayList<String> popList, popListFound;
	
	
	public EventViewController(){

        event = new Event(currentUser);
		
		popList = new ArrayList<String>();
		popListFound = new ArrayList<String>();
		
		toHour = "0";
		toMinute = "0";
		toDate = "0.0.0000";
		
		fromHour = "0";
		fromMinute = "0";
		fromDate = "0.0.0000";
		
		view = new EventView();
		view.setVisible(false);
		view.addComponentListener(this);
		
		view.getCalendarToPopPanel().getHourTextField().addKeyListener(this);
		view.getCalendarToPopPanel().getMinuteTextField().addKeyListener(this);
		view.getCalendarFromPopPanel().getHourTextField().addKeyListener(this);
		view.getCalendarFromPopPanel().getMinuteTextField().addKeyListener(this);
		view.getSaveButton().addActionListener(this);
		view.getCancelButton().addActionListener(this);
		view.getDeleteButton().addActionListener(this);
		view.getAcceptButton().addActionListener(this);
		view.getDeclineButton().addActionListener(this);
		view.getDeletePersonButton().addActionListener(this);
		
		view.getFromField().addMouseListener(this);
		view.getToField().addMouseListener(this);
		
		view.getTitleField().addMouseListener(this);
		view.getDescriptionArea().addMouseListener(this);
		view.getParticipantField().addMouseListener(this);
		
		view.getParticipantField().addKeyListener(this);
		popList = new ArrayList<String>();
		
		

		for (int i = 0; i < popList.size(); i++) {
			view.getPopListModel().addElement(popList.get(i));
		}
		
		view.getCalendarToPopPanel().getOverviewCalendarPanel().addPCL(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Calendar cal = (Calendar) evt.getNewValue();
				toDate = "" + cal.get(Calendar.DAY_OF_MONTH) + "." + cal.get(Calendar.MONTH) + "." + cal.get(Calendar.YEAR);
				view.getToField().setText(toDate + "/" + toHour + toMinute);
			}
		});
		
		view.getCalendarFromPopPanel().getOverviewCalendarPanel().addPCL(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Calendar cal = (Calendar) evt.getNewValue();
				fromDate = "" + cal.get(Calendar.DAY_OF_MONTH) + "." + cal.get(Calendar.MONTH) + "." + cal.get(Calendar.YEAR);
				view.getFromField().setText(fromDate + "/" + fromHour + fromMinute);				
			}
		});
		
		view.getParticipantPopList().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(view.getParticipantPopList().getSelectedIndex() != -1){
					view.getListModel().addElement(view.getParticipantPopList().getSelectedValue());
					view.getPopListModel().removeElement(view.getParticipantPopList().getSelectedValue());
					view.getParticipantField().grabFocus();
				}
			}
		});
    }
	
	public void setCurrentUser(Employee currentUser){
		this.currentUser = currentUser;
	}
	
	public void showEvent(){
		
	}
	
	public void showEvent(Event event){
		this.event = event;
		view.setVisible(true);
	}
	
	public void setVisible(boolean visible){
		view.setVisible(visible);
	}
	
	public void setEvent(Event event){
		this.event = event;
		view.setTitle(event.getTitle());
		view.setFromField(event.getDateFrom().toString());
		view.setToField(event.getDateTo().toString());
		view.setDescriptionArea(event.getDescription());
		
		for (int i = 0; i < event.getParticipants().size(); i++) {
			view.addParticipant(event.getParticipants().get(i));
		}
	}
	
//	
//	public Event getEvent (int ID){
//		try {
//			return EventHandler.getEvent(ID);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return null;
//		
//	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == view.getCalendarToPopPanel().getHourTextField()) {
			toHour = "" + view.getCalendarToPopPanel().getHourField() + ":";
		}
		else if (e.getSource() == view.getCalendarToPopPanel().getMinuteTextField()) {
			toMinute = "" + view.getCalendarToPopPanel().getMinField();
		}
		if (e.getSource() == view.getCalendarFromPopPanel().getHourTextField()) {
			fromHour = "" + view.getCalendarFromPopPanel().getHourField() + ":";
		}
		else if (e.getSource() == view.getCalendarFromPopPanel().getMinuteTextField()) {
			fromMinute = "" + view.getCalendarFromPopPanel().getMinField();
		}	
		else if(e.getSource() == view.getParticipantField()){
			for (int i = 0; i < popList.size(); i++) {
				if(view.getParticipantField().getText().length() <= 1){
					if(popList.get(i).charAt(view.getParticipantField().getText().length() - 1) == view.getParticipantField().getText().charAt(view.getParticipantField().getText().length() - 1)){
						popListFound.add(popList.get(i));
					}
				}
			}
			for (int y = 0; y < popListFound.size(); y++) {
				for (int i = 0; i < view.getPopListModel().size(); i++) {
					if(i< view.getPopListModel().size()){
						if(view.getPopListModel().get(i) != popListFound.get(y)){
							view.getPopListModel().remove(i);
							i--;
						}
					}
				}
			}
		}
		view.getToField().setText(toDate + "/" + toHour + toMinute);
		view.getFromField().setText(fromDate + "/ " + fromHour + fromMinute);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (e.getSource() == view.getTitleField()) {
			if (view.getTitleField().getText().equals("Title")) {
				view.getTitleField().setText("");
			}
		}
		else if(e.getSource() == view.getFromField()){
			view.getFromPop().show(view.getFromField(), 0, 30);
			if (view.getFromField().getText().equals("From")) {
				view.getFromField().setText("");
			}
			view.getFromField().grabFocus();
		}
		else if(e.getSource() == view.getToField()){
			view.getToPop().show(view.getToField(), 0, 30);
			if (view.getToField().getText().equals("To")) {
				view.getToField().setText("");
			}
			view.getToField().grabFocus();
		}
		else if (e.getSource() == view.getParticipantField()) {
			view.getParticipantPop().show(view.getParticipantField(), 0, 30);
			view.getParticipantField().setText("");
			view.getParticipantField().grabFocus();
		}
		else if(e.getSource() == view.getTitleField()){
			view.getTitleField().setText("");	
		}
		else if(e.getSource() == view.getDescriptionArea()){
			view.getDescriptionArea().setText("");	
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
public void componentHidden(ComponentEvent arg0) {
		
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		
//		view.getFromPop().show(view.getFromField(), 0, 30);
//		view.getToPop().show(view.getToField(), 0, 30);
//		view.getParticipantPop().show(view.getParticipantField(), 0, 30);
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		
//		view.getFromPop().show(view.getFromField(), 0, 30);
//		view.getToPop().show(view.getToField(), 0, 30);
//		view.getParticipantPop().show(view.getParticipantField(), 0, 30);
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == view.getSaveButton()){
			this.setVisible(false);
			event.setTitle(view.getTitle());
//			event.setDateFrom();
//			event.setDateTo(dateTo);
			event.setDescription(view.getDescriptionArea().getText());
			ArrayList<Employee> participants = new ArrayList<Employee>();
			for (int i = 0; i < view.getListModel().size(); i++) {
				participants.add((Employee) view.getListModel().get(i));
			}
			event.setParticipants(participants);
			event.setRoom((Room) view.getRoomBox().getSelectedItem());
			
	        event.save();
		}
		else if (e.getSource() == view.getCancelButton()) {
			this.setVisible(false);
		}
		else if (e.getSource() == view.getDeleteButton()) {
			this.setVisible(false);
		}
		else if (e.getSource() == view.getDeletePersonButton()) {
			int temp = view.getParticipantList().getSelectedIndex();
			Employee tempEmployee = (Employee) view.getParticipantList().getSelectedValue(); 
			if(temp > -1){
				view.getPopListModel().addElement(tempEmployee);
				view.getParticipantList().setSelectedIndex(temp - 1);
				view.removeParticipant(temp);
			}
		}
	}
	
	

}

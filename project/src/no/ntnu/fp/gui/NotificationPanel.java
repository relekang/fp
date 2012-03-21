package no.ntnu.fp.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;

import no.ntnu.fp.model.Employee;
import no.ntnu.fp.model.Notification;
import no.ntnu.fp.model.Notification.NotificationType;

import javax.swing.*;

public class NotificationPanel extends JPanel{
    JList list;
    DefaultListModel listModel;
    
    private GridBagConstraints gbc;
    
    public NotificationPanel(){
    	gbc = new GridBagConstraints();
        list = new JList();
        
        listModel = new DefaultListModel();
        list.setModel(listModel);
        
        list.setCellRenderer(new NotificationRenderer());
        
        addNotification(new Notification(1, "2012-03-19 12:04:36", "Invitation to event Z", 1, NotificationType.INVITATION));
        addNotification(new Notification(2, "2012-03-20 13:05:37", "Invitation accepted", 0, NotificationType.ACCEPTED));
        addNotification(new Notification(3, "2012-03-20 13:05:37", "Invitation declined", 0, NotificationType.DECLINED));
        addNotification(new Notification(4, "2012-03-20 13:05:37", "Event X changed", 0, NotificationType.CHANGE));
        addNotification(new Notification(5, "2012-03-20 13:05:37", "Event Y deleted", 0, NotificationType.DELETION));
        
        add(list);
        
    }
    
    public void removeNotification(int i) {
		listModel.remove(i);
	}
	
	public void addNotification(Notification notification) {
		listModel.addElement(notification);
	}
    
    public static void main(String[] args) {
    	NotificationPanel npTest = new NotificationPanel();
		
		JFrame frame = new JFrame();
		frame.setContentPane(npTest);
		
		frame.pack();
		frame.setVisible(true);
    }
}
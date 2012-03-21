package no.ntnu.fp.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import no.ntnu.fp.model.Employee;
import no.ntnu.fp.model.Event;
import no.ntnu.fp.model.Notification;
import no.ntnu.fp.model.Notification.NotificationType;

import javax.swing.*;

public class NotificationPanel extends JPanel{
    JList list;
    DefaultListModel listModel;
    JLabel label;
    
    private GridBagConstraints gbc;
    
    public NotificationPanel(){
    	gbc = new GridBagConstraints();
    	
    	this.setLayout(new GridBagLayout());
    	
        label = new JLabel("Notifications");
    	list = new JList();
        
        listModel = new DefaultListModel();
        list.setModel(listModel);
        
        list.setCellRenderer(new NotificationRenderer());
        list.setFixedCellWidth(200);
		list.setFixedCellHeight(20);
        
//        (int id, Event event, String timestamp, int is_invitation, NotificationType type, Employee employee, 
//        		boolean titleChanged, boolean timeChanged, boolean roomChanged, boolean descriptionChanged){
        
        /*Employee testGuy = new Employee();*/
        /*testGuy.setName("Test McYo");*/
        
        addNotification(new Notification(1, new Event("Event"/*, testGuy*/), "2012-03-19 12:04:36", 1, NotificationType.INVITATION/*, testGuy*/));
        addNotification(new Notification(2, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:37", 0, NotificationType.ACCEPTED));
        addNotification(new Notification(3, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:38", 0, NotificationType.DECLINED));
        addNotification(new Notification(4, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:40", 0, NotificationType.DELETION));
        addNotification(new Notification(5, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:39", 0, NotificationType.CHANGE, true, false, false, false));
        addNotification(new Notification(6, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:39", 0, NotificationType.CHANGE, false, true, false, false));
        addNotification(new Notification(7, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:39", 0, NotificationType.CHANGE, false, true, false, true));
        addNotification(new Notification(8, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:39", 0, NotificationType.CHANGE, true, true, true, false));
        
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.weighty = 0;
        gbc.gridwidth = 1;
        add(label, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weighty = 1.0;
        gbc.gridwidth = 2;
        add(list, gbc);
        
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
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
    }
}
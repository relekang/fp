package no.ntnu.fp.client.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import no.ntnu.fp.client.controller.ClientApplication;
import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.common.model.Notification;
import no.ntnu.fp.common.model.Notification.NotificationType;

import javax.swing.*;

public class NotificationPanel extends JPanel{
    private JList list;
    private DefaultListModel listModel;
    private JLabel label;
    private JButton viewNotification;
    private GridBagConstraints gbc;
    
    private JPopupMenu popup;
    
    public NotificationPanel(){
    	gbc = new GridBagConstraints();
    	this.setLayout(new GridBagLayout());
    	
        label = new JLabel("Notifications");
        label.setFont(GuiConstants.NOTIFICATIONPANE_FONT);
    	list = new JList();
        
        listModel = new DefaultListModel();
        list.setModel(listModel);
        
        list.setCellRenderer(new NotificationRenderer());
//        list.setPreferredSize(new Dimension(200, 200));
//        list.setMaximumSize(new Dimension(200, 300));
        list.setFixedCellWidth(200);
		list.setFixedCellHeight(20);

		viewNotification = new JButton("View notification");
		viewNotification.setSize(new Dimension(120, 20));
		viewNotification.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: Add support for showing correct event
				getSelectedIndex();
				
			}
		});
		
        /*Employee testGuy = new Employee();*/
        /*testGuy.setName("Test McYo");*/
        
//        addNotification(new Notification(1, new Event("Event"/*, testGuy*/), "2012-03-19 12:04:36", 1, NotificationType.INVITATION/*, testGuy*/));
//        addNotification(new Notification(2, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:37", 0, NotificationType.ACCEPTED));
//        addNotification(new Notification(3, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:38", 0, NotificationType.DECLINED));
//        addNotification(new Notification(4, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:40", 0, NotificationType.DELETION));
//        addNotification(new Notification(5, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:39", 0, NotificationType.CHANGE, true, false, false, false));
//        addNotification(new Notification(6, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:39", 0, NotificationType.CHANGE, false, true, false, false));
//        addNotification(new Notification(7, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:39", 0, NotificationType.CHANGE, false, true, false, true));
//        addNotification(new Notification(8, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:39", 0, NotificationType.CHANGE, true, true, true, false));

        popup = new JPopupMenu();
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.weighty = 0.0;
        gbc.gridwidth = 1;
        add(label, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weighty = 1.0;
        gbc.gridwidth = 2;
        add(list, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.weighty = 0.0;
        gbc.gridwidth = 1;
        add(viewNotification, gbc);
        
    }
    
    private int getSelectedIndex() {
		int selectedIndex = 0;
		if (list.getSelectedValue() != null) {
			selectedIndex = list.getSelectedIndex();
		}
		else selectedIndex = 0;
		return selectedIndex;
	}
    
    public void removeNotification(int i) {
		listModel.remove(i);
	}
	
	public void addNotification(Notification notification) {
		System.out.println("adding to notificationpanel: " + notification);
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
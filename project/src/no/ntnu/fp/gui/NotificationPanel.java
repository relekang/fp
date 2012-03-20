package no.ntnu.fp.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;

import no.ntnu.fp.model.Employee;
import no.ntnu.fp.model.Notification;

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
        
        addNotification(new Notification(1, "2012-03-19 12:04:36", "Test", 0));
        addNotification(new Notification(2, "2012-03-20 13:05:37", "Test nummer 2", 0));
        
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
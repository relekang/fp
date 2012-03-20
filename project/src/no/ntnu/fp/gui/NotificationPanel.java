package no.ntnu.fp.gui;

import java.awt.GridBagConstraints;

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
        add(list);
        
    }
}
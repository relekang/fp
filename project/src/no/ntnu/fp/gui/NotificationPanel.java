package no.ntnu.fp.gui;

import no.ntnu.fp.model.Notification;

import javax.swing.*;

public class NotificationPanel extends JPanel{
    JList list;
    DefaultListModel listModel;
    public NotificationPanel(){
        list = new JList();
        listModel = new DefaultListModel();
        list.setModel(listModel);
        add(list);
    }

}

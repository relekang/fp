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
        createDummyData();
        add(list);
    }

    private void createDummyData() {
        for(int i=0;i<6;i++){
            Notification n = new Notification("Test " + i);
            listModel.addElement(n);
        }
    }
}

package no.ntnu.fp.client.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import no.ntnu.fp.common.Util;
import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.common.model.Notification;
import no.ntnu.fp.common.model.Notification.NotificationType;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class NotificationPanel extends JPanel implements MouseListener{

	private JList list;
    private DefaultListModel listModel;
    private JLabel label;
    private GridBagConstraints gbc;
    private JFrame popupFrame;
    private NotificationPopup popup;
    
    public NotificationPanel(){
    	gbc = new GridBagConstraints();
    	this.setLayout(new GridBagLayout());
    	
    	popupFrame = new JFrame();
    	popupFrame.setLocationRelativeTo(getParent());
    	
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

		list.addMouseListener(this);
		
//        NotificationPopup notificationPopup = new NotificationPopup(new Notification(1, Event.getDummyEvent("Event"), "22.03.2012 15:17", NotificationType.CHANGE));

        gbc.gridx = 0; gbc.gridy = 0; gbc.weighty = 0.0;
        gbc.gridwidth = 1;
        add(label, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weighty = 1.0;
        gbc.gridwidth = 2;
        add(list, gbc);
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
		Util.print("adding to notificationpanel: " + notification);
		listModel.addElement(notification);
	}
    
	public JList getList() {
		return list;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		Notification selectedValue = (Notification) listModel.get(getSelectedIndex()); 
		popup = new NotificationPopup(selectedValue);
		popup.getViewEventButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				popupFrame.setVisible(false);
			}
		}); 
		popupFrame.setContentPane(popup);
		popupFrame.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		popupFrame.setLocation(screenSize.width / 2 - (popupFrame.getWidth() / 2),
				screenSize.height / 2 - (popupFrame.getHeight() / 2));
		
		popupFrame.setVisible(true);
			
	}

	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

}
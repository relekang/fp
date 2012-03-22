package no.ntnu.fp.client.gui;

import java.awt.GridBagConstraints;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.common.model.Notification;
import no.ntnu.fp.common.model.Notification.NotificationType;

public class NotificationPopup extends JPanel {
	private JPanel popupPanel;
	private JTextArea textArea;
	private JButton viewEvent;
	private JLabel iconLabel;
	private GridBagConstraints gbc;
	
	private ImageIcon acceptIcon;
	private ImageIcon declineIcon;
	private ImageIcon invitationIcon;
	private ImageIcon deleteIcon;
	private ImageIcon changeIcon;
	
	public NotificationPopup(Notification notification) {
		acceptIcon = new ImageIcon(getClass().getResource("/resources/icons/32x32/accept.png"));
		declineIcon = new ImageIcon(getClass().getResource("/resources/icons/32x32/decline.png"));
		invitationIcon = new ImageIcon(getClass().getResource("/resources/icons/32x32/question.png"));
		deleteIcon = new ImageIcon(getClass().getResource("/resources/icons/32x32/bin.png"));
		changeIcon = new ImageIcon(getClass().getResource("/resources/icons/32x32/error.png"));
		
		popupPanel = new JPanel();
		
		if (notification.getType() == NotificationType.INVITATION) iconLabel.setIcon(invitationIcon);
		else if (notification.getType() == NotificationType.DELETION) iconLabel.setIcon(deleteIcon);
		else if (notification.getType() == NotificationType.ACCEPTED) iconLabel.setIcon(acceptIcon);
		else if (notification.getType() == NotificationType.DECLINED) iconLabel.setIcon(declineIcon);
		else if (notification.getType() == NotificationType.CHANGE) iconLabel.setIcon(changeIcon);
		
		textArea = new JTextArea(notification.getDescription());
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
	}
	
	public static void main(String[] args) {
    	NotificationPopup popupTest = new NotificationPopup(new Notification(1, new Event("Event"), "22.03.2012 15:17", 0, NotificationType.ACCEPTED));
		
		JFrame frame = new JFrame();
		frame.setContentPane(popupTest);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
    }
}

package no.ntnu.fp.client.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import no.ntnu.fp.client.controller.ClientApplication;
import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.common.model.Notification;
import no.ntnu.fp.common.model.Notification.NotificationType;

public class NotificationPopup extends JPanel {
	private JTextArea textArea;
	private JButton viewEventBtn;
	private JLabel iconLabel;
	private GridBagConstraints gbc;
	
	private ImageIcon acceptIcon;
	private ImageIcon declineIcon;
	private ImageIcon invitationIcon;
	private ImageIcon deleteIcon;
	private ImageIcon changeIcon;
	
	public NotificationPopup(Notification notification) {
		gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		
		acceptIcon = new ImageIcon(getClass().getResource("/resources/icons/32x32/accept.png"));
		declineIcon = new ImageIcon(getClass().getResource("/resources/icons/32x32/decline.png"));
		invitationIcon = new ImageIcon(getClass().getResource("/resources/icons/32x32/question.png"));
		deleteIcon = new ImageIcon(getClass().getResource("/resources/icons/32x32/bin.png"));
		changeIcon = new ImageIcon(getClass().getResource("/resources/icons/32x32/error.png"));
		
		iconLabel = new JLabel();
		
		if (notification.getType() == NotificationType.INVITATION) iconLabel.setIcon(invitationIcon);
		else if (notification.getType() == NotificationType.DELETION) iconLabel.setIcon(deleteIcon);
		else if (notification.getType() == NotificationType.ACCEPTED) iconLabel.setIcon(acceptIcon);
		else if (notification.getType() == NotificationType.DECLINED) iconLabel.setIcon(declineIcon);
		else if (notification.getType() == NotificationType.CHANGE) iconLabel.setIcon(changeIcon);
		
		textArea = new JTextArea(notification.getDescription());
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setBackground(GuiConstants.SWING_FRAME_GRAY); // Takk til emiltayl.
		
		viewEventBtn = new JButton("View event");
		viewEventBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: Add support for showing correct event
				ClientApplication.showEventView();
			}

		});
		
		gbc.gridx = 0; gbc.gridy = 0; gbc.weighty = 1.0;
        gbc.gridwidth = 1;
		this.add(iconLabel, gbc);
		
		gbc.gridx = 0; gbc.gridy = 1; gbc.weighty = 1.0;
        gbc.gridheight = 2;
		this.add(textArea, gbc);
		
		gbc.gridx = 0; gbc.gridy = 3; gbc.weighty = 1.0;
        gbc.gridwidth = 1;
		this.add(viewEventBtn, gbc);
		
		this.setMaximumSize(new Dimension(120, 120));
		this.setPreferredSize(new Dimension(120, 120));
	}

	public static void main(String[] args) {
    	NotificationPopup popupTest = new NotificationPopup(new Notification(1, new Event("Event"), "22.03.2012 15:17", 0, NotificationType.ACCEPTED/*, true, true, true, true*/));
		
		JFrame frame = new JFrame();
		frame.setContentPane(popupTest);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
    }
}

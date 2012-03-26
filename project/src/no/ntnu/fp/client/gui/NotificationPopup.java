package no.ntnu.fp.client.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
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

	public NotificationPopup(final Notification notification) {
		gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());

		iconLabel = new JLabel();
		NotificationType type = notification.getType();
		if (type == NotificationType.INVITATION)
			iconLabel.setIcon(GuiConstants.INVITATION_ICON_BIG);
		else if (type == NotificationType.DELETION)
			iconLabel.setIcon(GuiConstants.DELETE_ICON_BIG);
		else if (type == NotificationType.ACCEPTED)
			iconLabel.setIcon(GuiConstants.ACCEPT_ICON_BIG);
		else if (type == NotificationType.DECLINED)
			iconLabel.setIcon(GuiConstants.DECLINE_ICON_BIG);
		else if (type == NotificationType.CHANGE)
			iconLabel.setIcon(GuiConstants.CHANGE_ICON_BIG);

		// TODO: Should get timestamp from database
		Calendar cal = notification.getTimestamp();
		String date = makeDoubleDigit(cal.get(Calendar.DAY_OF_MONTH)) + ".";
		String month = makeDoubleDigit((cal.get(Calendar.MONTH) + 1)) + ".";
		String year = makeDoubleDigit((cal.get(Calendar.YEAR))) + " ";
		String hour = makeDoubleDigit(cal.get(Calendar.HOUR_OF_DAY)) + ":";
		String min = makeDoubleDigit(cal.get(Calendar.MINUTE)) + "";

		String timestamp = date + month + year + hour + min;

		textArea = new JTextArea(timestamp + " "
				+ notification.getDescription());
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setBackground(GuiConstants.SWING_FRAME_GRAY); // Takk til
																// emiltayl.

		viewEventBtn = new JButton("View event");
		viewEventBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: Add support for showing correct event
				Event temp = notification.getEvent();
				ClientApplication.getEventViewController().showEvent(temp);
			}
		});

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 2;
		this.add(iconLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weighty = 1.0;
		gbc.gridwidth = 3;
		gbc.gridheight = 2;
		gbc.weightx = 1.0;
		this.add(textArea, gbc);

		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.weighty = 1.0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		this.add(viewEventBtn, gbc);

		// TODO: Sizes might need to be adjusted from the following (test with long event titles)
		int descriptionLength = notification.getDescription().length();
		// About15 characters per line, about 16px per line
		int descriptionHeight = ((int) (descriptionLength / 15)) * 16 + 16;
		// About 60 px for the icon and space around it, and 40 for button and space around that
		int popupSize = descriptionHeight + 60 + 40; 
		this.setMaximumSize(new Dimension(200, popupSize));
		this.setPreferredSize(new Dimension(200, popupSize));
		textArea.setMinimumSize(new Dimension(180, popupSize - 100));

	}

	public JButton getViewEventButton() {
		return viewEventBtn;
	}

	private String makeDoubleDigit(int number) {
		if (number >= 0 && number < 10)
			return "0" + number;
		else
			return "" + number;
	}

}
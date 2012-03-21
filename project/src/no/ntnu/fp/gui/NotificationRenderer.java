package no.ntnu.fp.gui;

import java.awt.Component;
import java.util.Calendar;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import no.ntnu.fp.model.Notification;
import no.ntnu.fp.model.Notification.NotificationType;

public class NotificationRenderer extends DefaultListCellRenderer implements ListCellRenderer {

	ImageIcon acceptIcon;
	ImageIcon declineIcon;
	ImageIcon invitationIcon;
	ImageIcon deleteIcon;
	ImageIcon changeIcon;
	
	public NotificationRenderer() {
		
		acceptIcon = new ImageIcon(getClass().getResource("/resources/icons/accept.png"));
		declineIcon = new ImageIcon(getClass().getResource("/resources/icons/decline.png"));
		invitationIcon = new ImageIcon(getClass().getResource("/resources/icons/question.png"));
		deleteIcon = new ImageIcon(getClass().getResource("/resources/icons/delete.png"));
		changeIcon = new ImageIcon(getClass().getResource("/resources/icons/error.png"));
	}
	
	public Component getListCellRendererComponent(JList list, Object selectedValue,
		int index, boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		
		JLabel label = (JLabel) super.getListCellRendererComponent(list, selectedValue, index, isSelected, cellHasFocus);
		Notification selected = (Notification) selectedValue;
		
		Calendar cal = selected.getTimestamp();
		String date = makeDoubleDigit(cal.get(Calendar.DAY_OF_MONTH)) + ".";
		String month = makeDoubleDigit((cal.get(Calendar.MONTH) + 1)) + " ";
		String hour = makeDoubleDigit(cal.get(Calendar.HOUR_OF_DAY)) + ":";
		String min = makeDoubleDigit(cal.get(Calendar.MINUTE)) + "";
		
		String timestamp = date + month + hour + min;
		
		label.setText(timestamp + ": " + selected.getDescription());
		
		
		if (selected.getType() == NotificationType.INVITATION) label.setIcon(invitationIcon);
		else if (selected.getType() == NotificationType.DELETION) label.setIcon(deleteIcon);
		else if (selected.getType() == NotificationType.ACCEPTED) label.setIcon(acceptIcon);
		else if (selected.getType() == NotificationType.DECLINED) label.setIcon(declineIcon);
		else if (selected.getType() == NotificationType.CHANGE) label.setIcon(changeIcon);
		
		return label;
	}
	
	private String makeDoubleDigit(int number) {
		if (number > 0 && number < 10) return "0" + number;
		else return "" + number;
	}
	
}

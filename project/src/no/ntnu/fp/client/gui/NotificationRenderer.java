package no.ntnu.fp.client.gui;

import java.awt.Component;
import java.util.Calendar;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import no.ntnu.fp.common.Util;
import no.ntnu.fp.common.model.Notification;
import no.ntnu.fp.common.model.Notification.NotificationType;

public class NotificationRenderer extends DefaultListCellRenderer implements
		ListCellRenderer {

	public NotificationRenderer() {
	}

	public Component getListCellRendererComponent(JList list,
			Object selectedValue, int index, boolean isSelected,
			boolean cellHasFocus) {

		JLabel label = (JLabel) super.getListCellRendererComponent(list,
				selectedValue, index, isSelected, cellHasFocus);
		label.setBackground(GuiConstants.SWING_FRAME_GRAY);
		Notification selected = (Notification) selectedValue;

		Calendar cal = Util.getCalendar();
        cal.clear();
        cal.setTime(selected.getTimestamp());
		String date = makeDoubleDigit(cal.get(Calendar.DAY_OF_MONTH)) + ".";
		String month = makeDoubleDigit((cal.get(Calendar.MONTH) + 1)) + ", ";
		String hour = makeDoubleDigit(cal.get(Calendar.HOUR_OF_DAY)) + ":";
		String min = makeDoubleDigit(cal.get(Calendar.MINUTE)) + "";

		String timestamp = date + month + hour + min;
		label.setText(timestamp + ": " + selected.getDescription());

		if (selected.getType() == NotificationType.INVITATION)
			label.setIcon(GuiConstants.INVITATION_ICON_SMALL);
		else if (selected.getType() == NotificationType.DELETION)
			label.setIcon(GuiConstants.DELETE_ICON_SMALL);
		else if (selected.getType() == NotificationType.ACCEPTED)
			label.setIcon(GuiConstants.ACCEPT_ICON_SMALL);
		else if (selected.getType() == NotificationType.DECLINED)
			label.setIcon(GuiConstants.DECLINE_ICON_SMALL);
		else if (selected.getType() == NotificationType.CHANGE)
			label.setIcon(GuiConstants.CHANGE_ICON_SMALL);
		return label;
	}

	private String makeDoubleDigit(int number) {
		if (number >= 0 && number < 10)
			return "0" + number;
		else
			return "" + number;
	}

}

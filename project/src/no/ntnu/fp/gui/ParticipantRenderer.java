package no.ntnu.fp.gui;

import java.awt.Component;
import no.ntnu.fp.model.Employee;
import no.ntnu.fp.model.Employee.Status;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ParticipantRenderer extends DefaultListCellRenderer implements ListCellRenderer{
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus){
				
		JLabel print = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		
		
		if(((Employee) value).getAccepted() == Status.ACCEPTED){
			java.net.URL imgURL = getClass().getResource("/resources/icons/accept.png");
			ImageIcon icon = new ImageIcon(imgURL);
			print.setIcon(icon);
	
		}
		if(((Employee) value).getAccepted() == Status.DECLINED){
			java.net.URL imgURL = getClass().getResource("/resources/icons/declined.png");
			ImageIcon icon = new ImageIcon(imgURL);
			print.setIcon(icon);
			
		}
		
		print.setText( ((Employee) value).getName());	
		java.net.URL delIMG = getClass().getResource("/resources/icons/delete.png");
		ImageIcon delete = new ImageIcon(delIMG);
		print.setIcon(delete);
		
		return this;
		
	}
	
}

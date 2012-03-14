package no.ntnu.fp.gui;

import java.awt.Color;
import java.awt.Component;
import no.ntnu.fp.model.Employee;
import no.ntnu.fp.model.Employee.Status;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class ParticipantRenderer extends DefaultListCellRenderer implements ListCellRenderer{
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus){
				
		//JLabel print = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		JPanel panel = new JPanel();
		JLabel label = new JLabel();
		JButton button = new JButton();
		if(((Employee) value).getAccepted() == Status.ACCEPTED){
			java.net.URL imgURL = getClass().getResource("/resources/icons/accept.png");
			ImageIcon icon = new ImageIcon(imgURL);
			panel.setBackground(Color.BLACK); 
			
			

			setVisible(true); 
			
			label.setIcon(icon);
			label.setText( ((Employee) value).getName());	
			label.setForeground(Color.GREEN);			
			panel.add(label);
			
	
		}

		
		java.net.URL delIMG = getClass().getResource("/resources/icons/delete.png");
		ImageIcon delete = new ImageIcon(delIMG);
		button.setIcon(delete);
		button.setBackground(Color.black);
		panel.add(button);
		return panel;
		
	}
	
}

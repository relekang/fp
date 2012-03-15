package no.ntnu.fp.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import no.ntnu.fp.model.Employee;
import no.ntnu.fp.model.Employee.Status;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class ParticipantRenderer extends JPanel implements ListCellRenderer{
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus){
				
		//JLabel print = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		
		JLabel label = new JLabel();
		JButton button = new JButton();
		if(((Employee) value).getAccepted() == Status.ACCEPTED){
			java.net.URL imgURL = getClass().getResource("/resources/icons/accept.png");
			ImageIcon icon = new ImageIcon(imgURL);
			panel.setBackground(Color.BLACK); 
			
			

			setVisible(true); 
			gbc.gridx = 0;
			gbc.gridwidth = 3;
			label.setIcon(icon);
			label.setText( ((Employee) value).getName());	
			label.setForeground(Color.GREEN);			
			panel.add(label,gbc);
			
	
		}
		else if(((Employee) value).getAccepted() == Status.DECLINED){
			java.net.URL imgURL = getClass().getResource("/resources/icons/decline.png");
			ImageIcon icon = new ImageIcon(imgURL);
			panel.setBackground(Color.BLACK); 
			
			
			
			setVisible(true); 
			gbc.gridx = 0;
			gbc.gridwidth = 3;
			label.setIcon(icon);
			label.setText( ((Employee) value).getName());	
			label.setForeground(Color.RED);			
			panel.add(label,gbc);
			
			
		}
		else{
			java.net.URL imgURL = getClass().getResource("/resources/icons/pending.png");
			ImageIcon icon = new ImageIcon(imgURL);
			panel.setBackground(Color.BLACK); 
			
			
			
			setVisible(true); 
			label.setIcon(icon);
			label.setText( ((Employee) value).getName());	
			label.setForeground(Color.ORANGE);			
			gbc.gridx = 0;
			gbc.gridwidth = 3;
			panel.add(label,gbc);
			
		}
		
		java.net.URL delIMG = getClass().getResource("/resources/icons/delete.png");
		ImageIcon delete = new ImageIcon(delIMG);
		button.setIcon(delete);
		button.setBackground(Color.black);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("moooo");
				
			}
		});
		gbc.gridx = 4;
		panel.add(button,gbc);
		return panel;
		
	}

	
	
}

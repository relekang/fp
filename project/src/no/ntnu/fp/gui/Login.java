package no.ntnu.fp.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Login extends JPanel {
	
	protected JLabel usernameLabel;
	protected JLabel passwordLabel;

	protected  JTextField usernameTextField;
	protected JPasswordField passwordfield;
	
	protected JButton loginButton;
	
	//constructor	
	public Login(){
		
		
		GridBagConstraints c = new GridBagConstraints();
    	setLayout(new GridBagLayout());
    	setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	
    	// adds usernamelabel and textfield
    	usernameLabel = new JLabel("Username:");
    	c.gridheight = 1;
    	c.gridwidth = 1;
    	c.gridx = 0;
    	c.gridy = 0;
    	add(usernameLabel, c);
    	
    	usernameTextField = new JTextField();
    	usernameTextField.setPreferredSize(new Dimension(400,25));
    	c.gridx = 1;
    	c.gridy = 0;
    	add(usernameTextField,c);
    	
    	
    	
    	//adds passwordlabel and textfield
    	passwordLabel = new JLabel("Password:");
    	c.gridx = 0;
    	c.gridy = 1;
    	add(passwordLabel,c);
    	
    	passwordfield = new JPasswordField();
    	passwordfield.setPreferredSize(new Dimension(400,25));
    	c.gridx = 1;
    	c.gridy = 1;
    	add(passwordfield,c);
  
    	
    	//adds login button
    	loginButton = new JButton("Log in");
    	c.gridx = 1;
    	c.gridy = 2;
    	add(loginButton,c);
    	
    	
    	
    	//Adds listeners
    	usernameTextField.addActionListener(new LoginAction());
    	passwordfield.addActionListener(new LoginAction());
    	loginButton.addActionListener(new LoginAction());
    
    	
	}
	
	
	class LoginAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Fix log in, so that both fields are required
			System.out.println("login");
		}
		
	}
	
	
	//Main
	public static void main(String[] args) throws UnsupportedLookAndFeelException{
		
		UIManager.setLookAndFeel(UIManager.getLookAndFeel());
		
		JFrame loginframe = new JFrame("Log in");
		loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginframe.getContentPane().add(new Login());
		loginframe.pack();
        loginframe.setVisible(true);
		
		
		
		
		
	}
	
	
	
	

}

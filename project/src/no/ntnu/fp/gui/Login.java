package no.ntnu.fp.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Login extends JPanel implements ActionListener, KeyListener{
	
	protected JLabel usernameLabel;
	protected JLabel passwordLabel;

	protected  JTextField usernameTextField;
	protected JPasswordField passwordField;
	
	protected JButton loginButton;
	protected JPopupMenu popup;
//	protected boolean loggedIn;
	protected MainView mv;
	
//	public boolean isLoggedIn() {
//		return loggedIn;
//	}
//
//
//	public void logOut() {
//		loggedIn = false;
//	}


	//constructor	
	public Login(MainView mv){
		
		this.mv = mv;
		popup = new JPopupMenu();
		
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
    	
    	passwordField = new JPasswordField();
    	passwordField.setPreferredSize(new Dimension(400,25));
    	c.gridx = 1;
    	c.gridy = 1;
    	add(passwordField,c);
  
    	
    	//adds login button
    	loginButton = new JButton("Log in");
    	c.gridx = 1;
    	c.gridy = 2;
    	add(loginButton,c);
    	
    	usernameTextField.addActionListener(this);
    	passwordField.addActionListener(this);
    	loginButton.addActionListener(this);
    	loginButton.addKeyListener(this);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 10 || e.getKeyCode() == 32) {
			String username = usernameTextField.getText();
			String password = new String(passwordField.getPassword());
			if(username.equalsIgnoreCase("w")&&password.equals("w")){
				usernameTextField.setText("");
				passwordField.setText("");
				mv.logIn();
				
			}
			else{
				System.out.println("Feil brukernavn/passord-kombinasjon");
				System.out.println("Hint: w/w");
			}		
			
		}
		else System.out.println(e);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String username = usernameTextField.getText();
		String password = new String(passwordField.getPassword());
		if(username.equalsIgnoreCase("w")&&password.equals("w")){
			usernameTextField.setText("");
			passwordField.setText("");
			mv.logIn();
			
		}
		else{
			System.out.println("Feil brukernavn/passord-kombinasjon");
			System.out.println("Hint: w/w");
		}
	}
	
	
//	//Main
//	public static void main(String[] args) throws UnsupportedLookAndFeelException{
//		
//		UIManager.setLookAndFeel(UIManager.getLookAndFeel());
//		
//		JFrame loginframe = new JFrame("Log in");
//		loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		loginframe.getContentPane().add(new Login());
//		loginframe.pack();
//        loginframe.setVisible(true);
//		
//		
//		
//		
//		
//	}
	
	
	
	

}

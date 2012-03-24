package no.ntnu.fp.client.gui;

import no.ntnu.fp.client.Authentication;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JPanel implements ActionListener, KeyListener{
	
	protected JLabel usernameLabel, passwordLabel, errorMsgLabel;

	protected JTextField usernameTextField;
	protected JPasswordField passwordField;
	
	protected JButton loginButton;
	protected MainView mv;

	//constructor	
	public Login(MainView mv){
		
		this.mv = mv;
		
		GridBagConstraints c = new GridBagConstraints();
    	setLayout(new GridBagLayout());
    	setBorder(GuiConstants.EMPTY_BORDER_10);

        errorMsgLabel = new JLabel();
        c.gridheight = 1;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
    	errorMsgLabel.setBorder(GuiConstants.EMPTY_BORDER_10);
        add(errorMsgLabel, c);

    	// adds usernamelabel and textfield
    	usernameLabel = new JLabel("Username:");
    	c.gridheight = 1;
    	c.gridwidth = 1;
    	c.gridx = 0;
    	c.gridy = 1;
    	add(usernameLabel, c);
    	
    	usernameTextField = new JTextField();
    	usernameTextField.setMinimumSize(new Dimension(260, 25));
    	usernameTextField.setPreferredSize(new Dimension(260,25));
    	c.gridx = 1;
    	c.gridy = 1;
    	add(usernameTextField,c);
    	
    	
    	
    	//adds passwordlabel and textfield
    	passwordLabel = new JLabel("Password:");
    	c.gridx = 0;
    	c.gridy = 2;
    	add(passwordLabel, c);
    	
    	passwordField = new JPasswordField();
    	passwordField.setMinimumSize(new Dimension(260, 25));
    	passwordField.setPreferredSize(new Dimension(260,25));
    	c.gridx = 1;
    	c.gridy = 2;
    	add(passwordField,c);
  
    	
    	//adds login button
    	loginButton = new JButton("Log in");
    	c.gridx = 1;
    	c.gridy = 3;
    	add(loginButton, c);
    	
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

	}		
	
	@Override
	public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == 10 || e.getKeyCode() == 32) loginAction();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		loginAction();
	}

    public void loginAction(){
            String username = usernameTextField.getText();
            String password = new String(passwordField.getPassword());
            try {
                if(Authentication.authenticate(username, password)){
                    usernameTextField.setText("");
                    passwordField.setText("");
                    mv.logIn();
                }
                else{
                    errorMsgLabel.setText("Wrong username or password");
                }
            } catch (SQLException exception) {
                System.err.println(exception);
            }
    }
}

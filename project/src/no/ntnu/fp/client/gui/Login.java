package no.ntnu.fp.client.gui;

import no.ntnu.fp.client.Authentication;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener, KeyListener{
	
	private JPanel background;
	protected JLabel usernameLabel, passwordLabel, errorMsgLabel;
	protected JTextField usernameTextField;
	protected JPasswordField passwordField;
	protected JButton loginButton;

	public Login(){
		background = new JPanel(new GridBagLayout());
		background.setBorder(GuiConstants.EMPTY_BORDER_10);
		GridBagConstraints c = new GridBagConstraints();
        
		errorMsgLabel = new JLabel();
        c.gridheight = 1;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
    	errorMsgLabel.setBorder(GuiConstants.EMPTY_BORDER_10);
        background.add(errorMsgLabel, c);

    	// adds usernamelabel and textfield
    	usernameLabel = new JLabel("Username:");
    	c.gridheight = 1;
    	c.gridwidth = 1;
    	c.gridx = 0;
    	c.gridy = 1;
    	background.add(usernameLabel, c);
    	
    	usernameTextField = new JTextField();
    	usernameTextField.setMinimumSize(new Dimension(260, 25));
    	usernameTextField.setPreferredSize(new Dimension(260,25));
    	c.gridx = 1;
    	c.gridy = 1;
    	background.add(usernameTextField,c);
    	
    	
    	
    	//adds passwordlabel and textfield
    	passwordLabel = new JLabel("Password:");
    	c.gridx = 0;
    	c.gridy = 2;
    	background.add(passwordLabel, c);
    	
    	passwordField = new JPasswordField();
    	passwordField.setMinimumSize(new Dimension(260, 25));
    	passwordField.setPreferredSize(new Dimension(260,25));
    	c.gridx = 1;
    	c.gridy = 2;
    	background.add(passwordField,c);
  
    	//adds login button
    	loginButton = new JButton("Log in");
    	c.gridx = 1;
    	c.gridy = 3;
    	background.add(loginButton, c);
    	loginButton.setVisible(true);
    	
    	usernameTextField.addActionListener(this);
    	passwordField.addActionListener(this);
    	loginButton.addActionListener(this);
    	loginButton.addKeyListener(this);
    	setContentPane(background);
    	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    	Dimension dd = getPreferredSize();
    	setLocation((int)(d.getWidth()/2-dd.getWidth()/2), (int)(d.getHeight()/2-dd.getHeight()/2));
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}		
	
	@Override
	public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == 10 || e.getKeyCode() == 32) 
        	loginAction();
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
                }
                else{
                    errorMsgLabel.setText("Wrong username or password");
                }
            } catch (SQLException exception) {
                System.err.println(exception);
            }
    }

    public void autoLogin(){
        usernameTextField.setText("rolf");
        passwordField.setText("rolf");
        long t0, t1;

        t0 =  System.currentTimeMillis();

        do{
            t1 = System.currentTimeMillis();
        }
        while ((t1 - t0) < (1000));
        actionPerformed(new ActionEvent(loginButton, ActionEvent.ACTION_PERFORMED, ""));

    }
}

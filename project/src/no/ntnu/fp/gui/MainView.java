package no.ntnu.fp.gui;

import no.ntnu.fp.controller.ClientApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame{
    private JPanel mainPanel, calenderPanel, overviewPanel, notificationPanel, loginPanel;
    private JButton createEventBtn, findPersonBtn, signOutBTN;
    private GridBagConstraints gbc;
    public MainView(){
        gbc = new GridBagConstraints();

        mainPanel = new JPanel();
        calenderPanel = new CalendarPanel();
        overviewPanel = new OverviewCalenderPanel();
        notificationPanel = new NotificationPanel();
        loginPanel = new Login();
//      loginPanel.mv = this;
        
        buildMainPanel();
//		add(loginPanel);
        add(mainPanel);

        pack();
    }
    
    
    
    
    private void buildMainPanel() {
        mainPanel.setLayout(new GridBagLayout());
        createEventBtn = new JButton("Create event");
        findPersonBtn = new JButton("Find person");
        signOutBTN = new JButton("Sign out");
        
        
        
        gbc.gridx = 6; gbc.gridy = 0; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.EAST;
        signOutBTN.addActionListener(new SignOutEventButtonListener());
        mainPanel.add(signOutBTN, gbc);
        
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.CENTER;
        createEventBtn.addActionListener(new CreateEventButtonListener());
        mainPanel.add(createEventBtn, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        findPersonBtn.addActionListener(new FindPersonButtonListener());
        mainPanel.add(findPersonBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;gbc.gridheight = 2;
        mainPanel.add(overviewPanel, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.gridwidth = 5;gbc.gridheight = 4;
        mainPanel.add(calenderPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;gbc.gridheight = 2;
        mainPanel.add(notificationPanel, gbc);

    }
    
    public void logOut(){
    	((Login) loginPanel).logOut();
    	mainPanel.setVisible(false);
    	add(loginPanel);
    	pack();
    	
    }

    class CreateEventButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ClientApplication.setEventFrameVisible(true);
        }
    }
    class SignOutEventButtonListener implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent actionEvent) {
    		logOut();
    	}
    }
    class FindPersonButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ClientApplication.setFindPersonFrameVisible(true);
        }
    }
}

package no.ntnu.fp.client.gui;

import no.ntnu.fp.client.controller.ClientApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainView extends JFrame implements PropertyChangeListener {
	
	private JPanel mainPanel;
	private CalendarPanel calendarPanel;
	private OverviewCalendarPanel overviewPanel;
	private NotificationPanel notificationPanel;
	private Login loginPanel;
	private JButton createEventBtn, findPersonBtn, signOutBtn;
	private GridBagConstraints gbc;

	public MainView() {
		gbc = new GridBagConstraints();

		mainPanel = new JPanel();
		loginPanel = new Login(this);

		buildMainPanel();

		mainPanel.setVisible(false);
		mainPanel.setPreferredSize(new Dimension(mainPanel.getPreferredSize().width, (int)(mainPanel.getMinimumSize()).getHeight()));
		add(loginPanel);
		pack();
	}
	
	public CalendarPanel getCalendarPanel() {
		return calendarPanel;
	}

	private void buildMainPanel() {
		overviewPanel = new OverviewCalendarPanel();
		calendarPanel = new CalendarPanel(overviewPanel);
		notificationPanel = new NotificationPanel();
		notificationPanel.setBorder(GuiConstants.EMPTY_BORDER_5);
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBorder(GuiConstants.EMPTY_BORDER_10);
		createEventBtn = new JButton("Create event");
		findPersonBtn = new JButton("Find person");
		signOutBtn = new JButton("Sign out");

		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.EAST;
		signOutBtn.addActionListener(new SignOutEventButtonListener());
		mainPanel.add(signOutBtn, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		createEventBtn.addActionListener(new CreateEventButtonListener());
		mainPanel.add(createEventBtn, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		findPersonBtn.addActionListener(new FindPersonButtonListener());
		mainPanel.add(findPersonBtn, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 2;
		mainPanel.add(overviewPanel, gbc);

		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 5;
		gbc.gridheight = 4;
		mainPanel.add(calendarPanel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.gridheight = 2;
//		gbc.anchor = GridBagConstraints.SOUTH;
		mainPanel.add(notificationPanel, gbc);

	}

	public void logIn() {
		mainPanel.setVisible(true);
		add(mainPanel);
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screenSize.width / 2 - (this.getWidth() / 2),
				screenSize.height / 2 - (this.getHeight() / 2));

		remove(loginPanel);

	}

	public void logOut() {
		// ((Login) loginPanel).logOut();
		mainPanel.setVisible(false);
		remove(mainPanel);
		add(loginPanel);
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screenSize.width / 2 - (this.getWidth() / 2),
				screenSize.height / 2 - (this.getHeight() / 2));

		((Login) loginPanel).usernameTextField.requestFocus(true);

	}

	class CreateEventButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			ClientApplication.showEventView();
		}
	}

	class SignOutEventButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			logOut();
		}
	}

	class FindPersonButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			ClientApplication.setFindPersonFrameVisible(true);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}
}

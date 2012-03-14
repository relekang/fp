package no.ntnu.fp.gui;

import com.apple.eawt.Application;
import no.ntnu.fp.controller.ClientApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame{
    private JPanel mainPanel, calenderPanel, overviewPanel, notificationPanel;
    private JButton createEventBtn, findPersonBtn;
    private GridBagConstraints gbc;
    public MainView(){
        gbc = new GridBagConstraints();

        mainPanel = new JPanel();
        calenderPanel = new CalendarPanel();
        overviewPanel = new OverviewCalenderPanel();
        notificationPanel = new NotificationPanel();

        buildMainPanel();
        add(mainPanel);

        pack();
    }

    private void buildMainPanel() {
        mainPanel.setLayout(new GridBagLayout());
        createEventBtn = new JButton("Create event");
        findPersonBtn = new JButton("Find person");

        gbc.gridx = 0; gbc.gridy = 0;
        createEventBtn.addActionListener(new CreateEventButtonListener());
        mainPanel.add(createEventBtn, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        mainPanel.add(findPersonBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;gbc.gridheight = 2;
        mainPanel.add(overviewPanel, gbc);

        gbc.gridx = 2; gbc.gridy = 0; gbc.gridwidth = 5;gbc.gridheight = 4;
        mainPanel.add(calenderPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;gbc.gridheight = 2;
        mainPanel.add(notificationPanel, gbc);

    }

    class CreateEventButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ClientApplication.setEventFrameVisible(true);
        }
    }
}

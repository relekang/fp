package no.ntnu.fp.gui;

import javax.swing.*;
import java.awt.*;

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


    public static void main (String args[]){
        JFrame frame = new MainView();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}

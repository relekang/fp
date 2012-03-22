package no.ntnu.fp.gui;

import no.ntnu.fp.gui.objects.DateLabel;
import javax.swing.*;

import java.awt.*;
import java.util.Calendar;

public class OverviewCalenderPanel extends JPanel {
    private GridBagConstraints gbc;
    private DateLabel[][] cal;
    private JLabel monLabel, tueLabel, wedLabel, thuLabel, friLabel, satLabel, sunLabel;
    private JButton nextbutton, previousButton;
    
    public OverviewCalenderPanel() {
    	gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        addCalendarHeaders();
        buildCalendar(3);
    }

    private void addCalendarHeaders() {
    	java.net.URL leftURL = getClass().getResource("/resources/icons/arrow_left.png");
    	java.net.URL rightURL = getClass().getResource("/resources/icons/arrow_right.png");
		ImageIcon leftIcon = new ImageIcon(leftURL);
		ImageIcon rightIcon = new ImageIcon(rightURL);
        
		Font f = new Font("Dialog", Font.PLAIN, 10);
        nextbutton = new JButton();
        previousButton = new JButton();
        
        nextbutton.setIcon(rightIcon);
        previousButton.setIcon(leftIcon);
        
        gbc.gridx = 0;	gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        this.add(previousButton, gbc);
        
        gbc.gridx = 4;	gbc.gridy = 0;
        gbc.gridwidth = 4;
        this.add(nextbutton, gbc);
        
        gbc.gridx = 0;	gbc.gridy = 1;
        gbc.gridwidth = 1;
        monLabel = new JLabel("mon");
        monLabel.setFont(f);
        add(monLabel, gbc);

        gbc.gridx = 1;	gbc.gridy = 1;
        tueLabel = new JLabel("tue");
        tueLabel.setFont(f);
        add(tueLabel, gbc);

        gbc.gridx = 2;	gbc.gridy = 1;
        wedLabel = new JLabel("wed");
        wedLabel.setFont(f);
        add(wedLabel, gbc);

        gbc.gridx = 3;	gbc.gridy = 1;
        thuLabel = new JLabel("thu");
        thuLabel.setFont(f);
        add(thuLabel, gbc);

        gbc.gridx = 4;	gbc.gridy = 1;
        friLabel = new JLabel("fri");
        friLabel.setFont(f);
        add(friLabel, gbc);

        gbc.gridx = 5;	gbc.gridy = 1;
        satLabel = new JLabel("sat");
        satLabel.setFont(f);
        add(satLabel, gbc);

        gbc.gridx = 6;	gbc.gridy = 1;
        sunLabel = new JLabel("sun");
        sunLabel.setFont(f);
        add(sunLabel, gbc);
    }

    private void buildCalendar(int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.DAY_OF_WEEK, 2);
        cal = new DateLabel[5][7];
        for (int i = 0; i < cal.length; i++) {
            for (int ii = 0; ii < cal[i].length; ii++) {
                cal[i][ii] = new DateLabel(c.getTime(), month);
                gbc.gridx = ii;
                gbc.gridy = i + 2;
                add(cal[i][ii], gbc);
                c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);

            }
        }
    }
}

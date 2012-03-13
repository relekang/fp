package no.ntnu.fp.gui;

import no.ntnu.fp.gui.objects.DateLabel;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class OverviewCalenderPanel extends JPanel {
    private GridBagConstraints gbc = new GridBagConstraints();
    private DateLabel[][] cal;
    private JLabel monLabel, tueLabel, wedLabel, thuLabel, friLabel, satLabel, sunLabel;


    public OverviewCalenderPanel() {
        setLayout(new GridBagLayout());
        addCalendarHeaders();
        buildCalendar(3);
    }

    private void addCalendarHeaders() {
        Font f = new Font("Dialog", Font.PLAIN, 10);
        gbc.gridx=0;gbc.gridy=0;
        monLabel = new JLabel("mon");
        monLabel.setFont(f);
        add(monLabel);

        gbc.gridx=1;gbc.gridy=0;
        tueLabel = new JLabel("tue");
        tueLabel.setFont(f);
        add(tueLabel);

        gbc.gridx=2;gbc.gridy=0;
        wedLabel = new JLabel("wed");
        wedLabel.setFont(f);
        add(wedLabel);

        gbc.gridx=4;gbc.gridy=0;
        thuLabel = new JLabel("thu");
        thuLabel.setFont(f);
        add(thuLabel);

        gbc.gridx=5;gbc.gridy=0;
        friLabel = new JLabel("fri");
        friLabel.setFont(f);
        add(friLabel);

        gbc.gridx=6;gbc.gridy=0;
        satLabel = new JLabel("sat");
        satLabel.setFont(f);
        add(satLabel);

        gbc.gridx=7;gbc.gridy=0;
        sunLabel = new JLabel("sun");
        sunLabel.setFont(f);
        add(sunLabel);
    }

    private void buildCalendar(int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
//        c.set(Calendar.MONTH, month-1);
        c.set(Calendar.DAY_OF_WEEK, 2);
        cal = new DateLabel[5][7];
        for (int i = 0; i < cal.length; i++) {
            for (int ii = 0; ii < cal[i].length; ii++) {
                cal[i][ii] = new DateLabel(c.getTime(), month);
                gbc.gridx = ii;
                gbc.gridy = i + 1;
                add(cal[i][ii], gbc);

                c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);

            }
        }
    }
}

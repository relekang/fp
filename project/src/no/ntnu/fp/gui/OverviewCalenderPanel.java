package no.ntnu.fp.gui;

import no.ntnu.fp.gui.objects.DateLabel;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class OverviewCalenderPanel extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();
    DateLabel[][] cal;

    public OverviewCalenderPanel() {
        setLayout(new GridBagLayout());
        buildPanel(3);
    }

    private void buildPanel(int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println(c.toString());
        int first_day_of_week = c.get(Calendar.DAY_OF_WEEK);
        System.out.println(first_day_of_week);
        cal = new DateLabel[5][7];
        c.set(Calendar.DAY_OF_WEEK, 2);
        for (int i = 0; i < cal.length; i++) {
            for (int ii = 0; ii < cal[i].length; ii++) {
                cal[i][ii] = new DateLabel(c.getTime(), month);
                gbc.gridx = ii;
                gbc.gridy = i;
                add(cal[i][ii], gbc);

                int d = c.get(Calendar.DAY_OF_MONTH) + 1;
                c.set(Calendar.DAY_OF_MONTH, d);

            }
        }
    }
}

package no.ntnu.fp.gui;

import no.ntnu.fp.gui.objects.CalendarDayBox;

import javax.swing.*;
import java.awt.*;

public class CalendarPanel extends JPanel{
    private GridBagConstraints gbc = new GridBagConstraints();
    private CalendarDayBox mondayBox, tuesdayBox, wednesdayBox, thursdayBox, fridayBox, saturdayBox, sundayBox;
    private JLabel monLabel, tueLabel, wedLabel, thuLabel, friLabel, satLabel, sunLabel;

    public CalendarPanel(){
        setLayout(new GridBagLayout());
        addCalendarHeaders();

        gbc.gridx=0;gbc.gridy=1;
        mondayBox = new CalendarDayBox();
        add(mondayBox, gbc);

        gbc.gridx=1;gbc.gridy=1;
        tuesdayBox = new CalendarDayBox();
        add(tuesdayBox, gbc);

        gbc.gridx=2;gbc.gridy=1;
        wednesdayBox = new CalendarDayBox();
        add(wednesdayBox, gbc);

        gbc.gridx=3;gbc.gridy=1;
        thursdayBox = new CalendarDayBox();
        add(thursdayBox, gbc);

        gbc.gridx=4;gbc.gridy=1;
        fridayBox = new CalendarDayBox();
        add(fridayBox, gbc);

        gbc.gridx=5;gbc.gridy=1;
        saturdayBox = new CalendarDayBox();
        add(saturdayBox, gbc);

        gbc.gridx=6;gbc.gridy=1;
        sundayBox = new CalendarDayBox();
        add(sundayBox, gbc);

    }
    private void addCalendarHeaders() {
        Font f = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
        gbc.gridx=0;gbc.gridy=0;
        monLabel = new JLabel("Monday");
        monLabel.setFont(f);
        add(monLabel);

        gbc.gridx=1;gbc.gridy=0;
        tueLabel = new JLabel("Tuesday");
        tueLabel.setFont(f);
        add(tueLabel);

        gbc.gridx=2;gbc.gridy=0;
        wedLabel = new JLabel("Wednesday");
        wedLabel.setFont(f);
        add(wedLabel);

        gbc.gridx=4;gbc.gridy=0;
        thuLabel = new JLabel("Thursday");
        thuLabel.setFont(f);
        add(thuLabel);

        gbc.gridx=5;gbc.gridy=0;
        friLabel = new JLabel("Friday");
        friLabel.setFont(f);
        add(friLabel);

        gbc.gridx=6;gbc.gridy=0;
        satLabel = new JLabel("Saturday");
        satLabel.setFont(f);
        add(satLabel);

        gbc.gridx=7;gbc.gridy=0;
        sunLabel = new JLabel("Sunday");
        sunLabel.setFont(f);
        add(sunLabel);
    }


}

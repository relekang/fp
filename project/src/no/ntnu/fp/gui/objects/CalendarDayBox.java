package no.ntnu.fp.gui.objects;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class CalendarDayBox extends JPanel{
    Canvas canvas;
    public CalendarDayBox(){
        Calendar cal = Calendar.getInstance();
        canvas = new Canvas();
        canvas.setBounds(0,0,160,400);
        setBackground(Color.DARK_GRAY);
        add(canvas);
    }
}

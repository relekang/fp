package no.ntnu.fp.gui.objects;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;

public class DateLabel extends JLabel implements MouseListener{
    private Calendar cal;
    private int month;

    public DateLabel(Date date, int month){
        super();
        cal = Calendar.getInstance();
        cal.setTime(date);
        this.month = month;
        setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
        setBorder(new EmptyBorder(4, 4, 4, 4));
        
        if(cal.get(Calendar.MONTH) + 1 != this.month){
            setForeground(Color.GRAY);
        }
        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        System.out.println(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent)  { }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) { }

    @Override
    public void mouseEntered(MouseEvent mouseEvent)  { }

    @Override
    public void mouseExited(MouseEvent mouseEvent)   { }
}

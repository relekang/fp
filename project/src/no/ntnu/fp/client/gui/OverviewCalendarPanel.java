package no.ntnu.fp.client.gui;

import no.ntnu.fp.client.gui.objects.DateLabel;
import no.ntnu.fp.common.Util;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Calendar;
import java.util.TimeZone;

public class OverviewCalendarPanel extends JPanel implements MouseListener{
	
	public final static String SELECTED_DAY_CHANGED = "selected_day_changed";
	
    private GridBagConstraints gbc;
    private DateLabel[][] dateLabels;
    private JLabel monLabel, tueLabel, wedLabel, thuLabel, friLabel, satLabel, sunLabel, monthLabel;
    private JButton nextButton, previousButton;
    private Calendar selected;
    private PropertyChangeSupport pcs;
    private int month;
    private Calendar c;

    public OverviewCalendarPanel() {
        c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, 1);

        pcs = new PropertyChangeSupport(this);
    	gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        addCalendarHeaders();
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        buildCalendar(c.get(Calendar.MONTH)+1);
        setMonth(c.get(Calendar.MONTH)+1);
    }
    
    public void addPCL(PropertyChangeListener listener) {
    	pcs.addPropertyChangeListener(listener);
    }

    private void addCalendarHeaders() {
    	java.net.URL leftURL = getClass().getResource("/resources/icons/arrow_left.png");
    	java.net.URL rightURL = getClass().getResource("/resources/icons/arrow_right.png");
		ImageIcon leftIcon = new ImageIcon(leftURL);
		ImageIcon rightIcon = new ImageIcon(rightURL);
        
		Font f = new Font("Dialog", Font.PLAIN, 10);
        nextButton = new JButton();
        previousButton = new JButton();

        monthLabel = new JLabel();

        nextButton.addActionListener(new ButtonListener());
        previousButton.addActionListener(new ButtonListener());

        nextButton.setIcon(rightIcon);
        previousButton.setIcon(leftIcon);
        
        gbc.gridx = 0;	gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(previousButton, gbc);

        gbc.gridx = 2;	gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(monthLabel, gbc);
        
        gbc.gridx = 5;	gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(nextButton, gbc);
        
        gbc.gridx = 0;	gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
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
        c.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        c.set(Calendar.DAY_OF_WEEK, 2);
        System.out.println(c.get(Calendar.DAY_OF_MONTH));
        dateLabels = new DateLabel[5][7];
        for (int i = 0; i < dateLabels.length; i++) {
            for (int ii = 0; ii < dateLabels[i].length; ii++) {
                dateLabels[i][ii] = new DateLabel(c.getTime(), month);
                dateLabels[i][ii].addMouseListener(this);
                gbc.gridx = ii;
                gbc.gridy = i + 2;
                add(dateLabels[i][ii], gbc);
                c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);

            }
        }
        
    }
    private void updateCalendar(int month){

        if(month == 13) {
            c.set(Calendar.MONTH, 12);
            c.set(Calendar.YEAR, c.get(Calendar.YEAR)+1);
        } else if(month == 0) {
            c.set(Calendar.MONTH, 1);
            c.set(Calendar.YEAR, c.get(Calendar.YEAR)-1);
        }
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.DAY_OF_WEEK, 2);
        for (DateLabel[] labels:dateLabels){
            for(DateLabel label:labels){
                label.setDate(c.getTime());
                c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
            }
        }
    }

    private void setBackgrounds(Color color) {
        for(DateLabel[] row:dateLabels){
            for(DateLabel label:row){
                label.setBackground(color);
            }
        }
    }

    public int getWeek(){
        return selected.get(Calendar.WEEK_OF_YEAR);
    }
    
    public void setSelectedLabel(DateLabel label) {
    	Calendar oldVal = this.selected;
    	selected = label.getCalendar();
    	pcs.firePropertyChange(SELECTED_DAY_CHANGED, oldVal, selected);
//    	label.setForeground(Color.BLUE);
    }
    
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        DateLabel label = (DateLabel) mouseEvent.getSource();
        setSelectedLabel(label);
        setBackgrounds(Color.gray);
        label.setBackground(Color.red);
        System.out.println(getWeek());
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent)  { }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) { }

    @Override
    public void mouseEntered(MouseEvent mouseEvent)  { }

    @Override
    public void mouseExited(MouseEvent mouseEvent)   { }





    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new OverviewCalendarPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }

    public int getMonth() {
        return month;
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(actionEvent.getSource() == nextButton){
                System.out.println("next");
                setMonth(getMonth() + 1);
            }
            else if(actionEvent.getSource() == previousButton){
                System.out.println("previous");
                setMonth(getMonth() - 1);
            }
        }
    }

    private void setMonth(int newMonth) {
        if(newMonth == 0) newMonth = 12;
        if(newMonth == 13) newMonth = 1;
        this.month = newMonth;
        updateCalendar(month);
        this.monthLabel.setText(Util.getMonthText(month) + c.get(Calendar.YEAR));
    }
}
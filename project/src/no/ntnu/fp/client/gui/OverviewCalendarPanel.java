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

public class OverviewCalendarPanel extends JPanel implements MouseListener{
	
	public final static String SELECTED_DAY_CHANGED = "selected_day_changed";
	
    private GridBagConstraints gbc;
    private DateLabel[][] dateLabels;
    private JLabel monLabel, tueLabel, wedLabel, thuLabel, friLabel, satLabel, sunLabel, monthLabel;
    private JButton nextButton, previousButton;
    private Calendar selected;
    private PropertyChangeSupport pcs;
    private Calendar c;

    public OverviewCalendarPanel() {
        c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
//        c.set(Calendar.MONTH, month);
        

        pcs = new PropertyChangeSupport(this);
    	gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        addCalendarHeaders();
//        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        buildCalendar(c.get(Calendar.MONTH));
        setMonth(c.get(Calendar.MONTH));
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
    	c.clear();
    	c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        c.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
        c.set(Calendar.WEEK_OF_MONTH, 1);
        dateLabels = new DateLabel[5][7];
        for (int i = 0; i < dateLabels.length; i++) {
            for (int ii = 0; ii < dateLabels[i].length; ii++) {
                dateLabels[i][ii] = new DateLabel(c.getTime(), month);
                dateLabels[i][ii].addMouseListener(this);
                gbc.gridx = ii;
                gbc.gridy = i + 2;
                dateLabels[i][ii].setFont(GuiConstants.DATELABEL_NORMAL);
                add(dateLabels[i][ii], gbc);
                c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);

            }
        }
        c.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        c.set(Calendar.MONTH, month);
    }
    
    private void updateCalendar(int month){
    	int year = c.get(Calendar.YEAR);
        c.clear();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.WEEK_OF_MONTH, 1);
        for (DateLabel[] labels:dateLabels){
            for(DateLabel label:labels){
                label.setDate(c.getTime());
                c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
            }
        }
        c.set(Calendar.MONTH, month);
        c.set(Calendar.YEAR, year);
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
        setFontOnAll(GuiConstants.DATELABEL_NORMAL);
        label.setFont(GuiConstants.DATELABEL_BOLD);
    	Calendar oldVal = this.selected;
    	selected = label.getCalendar();
    	pcs.firePropertyChange(SELECTED_DAY_CHANGED, oldVal, selected);
//    	label.setForeground(Color.BLUE);
    }

    private void setFontOnAll(Font font) {
        for(DateLabel[] row:dateLabels){
            for(DateLabel label:row){
                label.setFont(font);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        DateLabel label = (DateLabel) mouseEvent.getSource();
        setSelectedLabel(label);
        setBackgrounds(Color.gray);
        label.setBackground(Color.red);
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
        return c.get(Calendar.MONTH);
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(actionEvent.getSource() == nextButton){
                setMonth(getMonth()+1);
            }
            else if(actionEvent.getSource() == previousButton){
                setMonth(getMonth()-1);
            }
        }
    }

    private void setMonth(int newMonth) {
        c.set(Calendar.MONTH, newMonth);
        updateCalendar(c.get(Calendar.MONTH));
        this.monthLabel.setText(Util.getMonthText(c.get(Calendar.MONTH)) + c.get(Calendar.YEAR));
    }

}
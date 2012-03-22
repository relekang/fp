package no.ntnu.fp.client.gui;

import no.ntnu.fp.client.gui.objects.DateLabel;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Calendar;

public class OverviewCalendarPanel extends JPanel implements MouseListener{
	
	public final static String SELECTED_DAY_CHANGED = "selected_day_changed";
	
    private GridBagConstraints gbc;
    private DateLabel[][] dateLabels;
    private JLabel monLabel, tueLabel, wedLabel, thuLabel, friLabel, satLabel, sunLabel;
    private JButton nextButton, previousButton;
    private Calendar selected;
    private PropertyChangeSupport pcs;

    public OverviewCalendarPanel() {
    	pcs = new PropertyChangeSupport(this);
    	gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        addCalendarHeaders();
        buildCalendar(Calendar.getInstance().get(Calendar.MONTH)+1);
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

        nextButton.setIcon(rightIcon);
        previousButton.setIcon(leftIcon);
        
        gbc.gridx = 0;	gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        this.add(previousButton, gbc);
        
        gbc.gridx = 4;	gbc.gridy = 0;
        gbc.gridwidth = 4;
        this.add(nextButton, gbc);
        
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
        System.out.println(c.get(Calendar.DAY_OF_MONTH));
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
}
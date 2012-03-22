package no.ntnu.fp.server.gui;

import no.ntnu.fp.server.storage.db.EmployeeHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class EmployeeListPanel extends JPanel{
    JTable table;
    public EmployeeListPanel(){
//        setBounds(0,0,180,180);
        setBorder(new EmptyBorder(5,5,5,5));
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Employees");
        add(titleLabel, BorderLayout.NORTH);
        updateTable();
    }
    public void updateTable(){
        String[] columnNames = {"ID" ,"Title",
                "From",
                "To",
                "Room"};
        EmployeeHandler eHandler = null;
//        try {
//            eHandler = new EmployeeHandler();
//            ArrayList<Event> list = eHandler.fetchAllEmployee();
//            Object[][] data = new String[list.size()][5];
//            for (int i = 0; i< list.size(); i++){
//                Event e = list.get(i);
//                data[i][0] = Integer.toString(e.getID());
//                data[i][1] = e.getTitle();
//                data[i][1] = e.getDateFrom().toString();
//                if(e.getDateTo() != null) data[i][2] = e.getDateTo().toString();
//                data[i][3] = e.getDescription();
//                data[i][4] = e.getRoom().getName();
//            }
//            table = new JTable(data,columnNames);
//            add(table, BorderLayout.SOUTH);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}

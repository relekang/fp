package no.ntnu.fp.server.gui;

import no.ntnu.fp.model.Event;
import no.ntnu.fp.storage.db.EventHandler;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;


public class EventListPanel extends JPanel{
    JTable table;
    public EventListPanel(){
        setBounds(0,0,180,180);
        setLayout(new BorderLayout());
        updateTable();
    }
    public void updateTable(){
        String[] columnNames = {"Title",
                "From",
                "To",
                "Room"};
        EventHandler eHandler = null;
        try {
            eHandler = new EventHandler();
            ArrayList<Event> list = eHandler.fetchAllEvents();
            Object[][] data = new String[list.size()][4];
            for (int i = 0; i< list.size(); i++){
                Event e = list.get(i);
                data[i][0] = e.getTitle();
//                data[i][1] = e.getDateFrom().toString();
//                data[i][2] = e.getDateTo().toString();
                data[i][3] = "Test";
            }
            table = new JTable(data,columnNames);
            add(table, BorderLayout.SOUTH);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

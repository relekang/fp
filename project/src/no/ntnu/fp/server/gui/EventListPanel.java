package no.ntnu.fp.server.gui;

import no.ntnu.fp.model.Event;
import no.ntnu.fp.storage.db.EventHandler;
import no.ntnu.fp.storage.db.RoomHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;


public class EventListPanel extends JPanel{
    JTable table;
    public EventListPanel(){
//        setBounds(0,0,180,180);
        setBorder(new EmptyBorder(5,5,5,5));
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Events");
        add(titleLabel, BorderLayout.NORTH);
        updateTable();
    }
    public void updateTable(){
        String[] columnNames = {"ID" ,"Title",
                "From",
                "To",
                "Room"};
        EventHandler eHandler = null;
        try {
            eHandler = new EventHandler();
            ArrayList<Event> list = eHandler.fetchAllEvents();
            Object[][] data = new String[list.size()][5];
            for (int i = 0; i< list.size(); i++){
                Event e = list.get(i);
                data[i][0] = Integer.toString(e.getID());
                data[i][1] = e.getTitle();
                data[i][1] = e.getDateFrom().toString();
                if(e.getDateTo() != null) data[i][2] = e.getDateTo().toString();
                data[i][3] = e.getDescription();
                data[i][4] = e.getRoom().getName();
            }
            table = new JTable(data,columnNames);
            add(table, BorderLayout.SOUTH);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

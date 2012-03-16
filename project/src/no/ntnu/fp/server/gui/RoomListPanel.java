package no.ntnu.fp.server.gui;

import no.ntnu.fp.model.Event;
import no.ntnu.fp.model.Room;
import no.ntnu.fp.storage.db.EventHandler;
import no.ntnu.fp.storage.db.RoomHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;


public class RoomListPanel extends JPanel{
    JTable table;
    public RoomListPanel(){
//        setBounds(0,0,180,180);
        setBorder(new EmptyBorder(5,5,5,5));
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Rooms");
        add(titleLabel, BorderLayout.NORTH);
        updateTable();
    }
    public void updateTable(){
        String[] columnNames = {"ID",
                "Name",
                "Location",
                "Capacity"};
        RoomHandler rHandler = null;
        try {
            rHandler = new RoomHandler();
            ArrayList<Room> list = rHandler.fetchAllRooms();
            Object[][] data = new String[list.size()][4];
            for (int i = 0; i< list.size(); i++){
                Room r = list.get(i);
                data[i][0] = Integer.toString(r.getRoomId());
                data[i][1] = r.getName();
                data[i][2] = r.getLocation();
                data[i][3] = Integer.toString(r.getCapacity());
            }
            table = new JTable(data,columnNames);
            add(table, BorderLayout.SOUTH);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

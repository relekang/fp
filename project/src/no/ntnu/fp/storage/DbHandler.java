package no.ntnu.fp.storage;

import no.ntnu.fp.model.Event;
import no.ntnu.fp.model.Room;

import java.sql.*;
import java.util.ArrayList;

public class DbHandler {
    private Connection conn;
    private String username;
    private String password;
    
    public DbHandler(){
        username = "";
        password = "";
    }
    
    public DbHandler(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    public void connect() throws SQLException {

        this.conn = DriverManager.getConnection(
                "jdbc:default:fellesprosjekt",
                username,
                password);
    }

    public void close() throws SQLException {
        conn.close();
    }

    public ArrayList<Event> fetchAllEvent(){
        ArrayList<Event> events = new ArrayList<Event>();
        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM EVENT");

            while (rs.next()) {
                Event event = new Event(rs.getString("title"));
                event.setDateFrom(rs.getDate("date_from"));
                event.setDateTo(rs.getDate("date_to"));
                event.setRoom(new Room(rs.getInt("room_id")));
                event.setDescription(rs.getString("description"));
                events.add(event);
            }
            close();
            return events;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
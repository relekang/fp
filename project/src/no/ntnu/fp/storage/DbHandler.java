package no.ntnu.fp.storage;

import no.ntnu.fp.model.Event;
import no.ntnu.fp.model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DbHandler {
    private Connection conn;
    private String username;
    private String password;

    private String mysqlDriver;
    private String url;
    
    public DbHandler() throws SQLException {
        username = "sondrele_fp";
        password = "fellesp1";
        url="mysql://mysql.stud.ntnu.no";
    }
    
    public DbHandler(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    public boolean connect() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);
        conn = DriverManager.getConnection(url, props);
        return true;
    }


    public void close() throws SQLException {
        conn.close();
    }

    public ArrayList<Event> fetchAllEvents(){
        ArrayList<Event> events = new ArrayList<Event>();
        try {
            if(!connect())
                return events;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM EVENT");

            while (rs.next()) {
                Event event = new Event(rs.getString("title"));
                event.setDateFrom(rs.getDate("date_from"));
                event.setDateTo(rs.getDate("date_to"));
                event.setRoom(new Room(rs.getInt("room_id")));
                event.setDescription(rs.getString(" description"));
                events.add(event);
            }
            rs.close();
            close();
            return events;

        } catch (SQLException e) {
            e.printStackTrace();
            return events;
        }

    }

    public Event fetchEvent(String arg){
        try {
            if(!connect())
                return null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM EVENT WHERE " + arg);
            Event event;
            while (rs.next()) {
                event = new Event(rs.getString("title"));
                event.setDateFrom(rs.getDate("date_from"));
                event.setDateTo(rs.getDate("date_to"));
                event.setRoom(new Room(rs.getInt("room_id")));
                event.setDescription(rs.getString(" description"));

            }
            rs.close();
            close();

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }


    public static void main (String args[]){
        try {
            DbHandler dbHandler = new DbHandler();
        System.out.print(dbHandler.fetchAllEvents().size());
        } catch (SQLException e){

        }
    }


}
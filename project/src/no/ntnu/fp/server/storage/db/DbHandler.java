package no.ntnu.fp.server.storage.db;

import no.ntnu.fp.common.Constants;
import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.common.model.Room;

import java.sql.*;
import java.util.Date;
import java.util.Calendar;
import java.util.Properties;

public class DbHandler {
    protected Connection conn;
    private String username;
    private String password;

    private static String url = Constants.getDbUrl();
    
    public DbHandler() throws SQLException {
        username = Constants.getDbUsername();
        password = Constants.getDbPassword();

    }

    public boolean connect() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Ooops, could not find db-driver");
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
    





    public static void main (String args[]){
        try {
            EventHandler eventHandler = new EventHandler();
            RoomHandler roomHandler = new RoomHandler();
            System.out.println("Created DbHandler instance");
            Event e = new Event("Superevent", Calendar.getInstance().getTime(), Calendar.getInstance().getTime());
            e.setDescription("LOL");
            Room room = new Room("R1", "Realfagsbygget", 600);
            System.out.println(roomHandler.createRoom(room));
            e.setRoom(room);
            System.out.println(eventHandler.createEvent(e));
            System.out.print(eventHandler.fetchAllEvents());
        } catch (SQLException e){
            System.out.println(e);
        }
    }


    public Connection getConnection() {
        return conn;
    }
}
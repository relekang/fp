package no.ntnu.fp.storage.db;

import no.ntnu.fp.model.Event;
import no.ntnu.fp.model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

public class DbHandler {
    protected Connection conn;
    private String username;
    private String password;

    private static String url = "jdbc:mysql://mysql.stud.ntnu.no/sondrele_fp";
    
    public DbHandler() throws SQLException {
        username = "sondrele_fp";
        password = "fellesp1";

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
            EventHandler dbHandler = new EventHandler();
            System.out.println("Created DbHandler instance");
            Event e = new Event("Superevent", Calendar.getInstance().getTime(), Calendar.getInstance().getTime());
            e.setDescription("LOL");
            e.setRoom(new Room(0));
            System.out.println(dbHandler.createEvent(e));
            System.out.print(dbHandler.fetchAllEvents());
        } catch (SQLException e){
            System.out.println(e);
        }
    }


}
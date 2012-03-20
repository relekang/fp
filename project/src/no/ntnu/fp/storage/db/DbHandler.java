package no.ntnu.fp.storage.db;

import no.ntnu.fp.model.Event;
import no.ntnu.fp.model.Room;

import java.sql.*;
import java.util.Date;
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
    
    public Date dateFromString(String input){
        Calendar cal = Calendar.getInstance();
        int[] date = new int[3];
        for(int i = 0; i < date.length; i++){
            date[i] = Integer.parseInt(input.split("-")[i]);
        }
        cal.set(Calendar.YEAR, date[0]);
        cal.set(Calendar.MONTH, date[1]);
        cal.set(Calendar.DAY_OF_MONTH, date[2]);
        System.out.println(cal.getTime().toString());
        return cal.getTime();
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
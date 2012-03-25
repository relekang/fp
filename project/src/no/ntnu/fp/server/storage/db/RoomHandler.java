package no.ntnu.fp.server.storage.db;

import no.ntnu.fp.common.Util;
import no.ntnu.fp.common.model.Room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class RoomHandler extends DbHandler {

    public RoomHandler() throws SQLException {
        super();
    }

    public ArrayList<Room> fetchAllRooms() throws SQLException {
        ArrayList<Room> rooms = new ArrayList<Room>();

        if(!connect())
            return rooms;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM ROOM");

        while (rs.next()) {
            Room room = new Room(rs.getInt("id"), rs.getString("name"), rs.getString("location"), rs.getInt("capacity"));
            rooms.add(room);
        }
        rs.close();
        close();
        return rooms;

    }

    public Room fetchRoom(String arg){
        try {
            if(!connect())
                return null;
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM ROOM WHERE " + arg;
            ResultSet rs = stmt.executeQuery(query);
            Util.print(query);
            Room room = null;
            while (rs.next()) {
                room = new Room(rs.getInt("id"), rs.getString("name"), rs.getString("location"), rs.getInt("capacity"));

            }
            rs.close();
            close();

            return room;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    public Room createRoom(Room room) throws SQLException {
        if(!connect())
            return null;


        String query = "INSERT INTO `ROOM` (`id`, `name`, `location`, `capacity`) VALUES (NULL, '%s', '%s', %d);";
        query = String.format(query, room.getName(), room.getLocation(), room.getCapacity());
        Util.print(query);
        Statement stm = conn.createStatement();
        boolean rs = stm.execute(query);
        close();
        return room;
    }

    public static Room getRoom(int roomId) throws SQLException {
        RoomHandler roomHandler = new RoomHandler();
        return roomHandler.fetchRoom(String.format("id = %d", roomId));
    }
}

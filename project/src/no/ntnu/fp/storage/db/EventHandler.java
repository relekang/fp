package no.ntnu.fp.storage.db;

import no.ntnu.fp.model.Event;
import no.ntnu.fp.model.Room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class EventHandler extends DbHandler {

    public EventHandler() throws SQLException {
        super();
    }

    public ArrayList<Event> fetchAllEvents() throws SQLException {
        ArrayList<Event> events = new ArrayList<Event>();

        if(!connect())
            return events;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM EVENT");

        while (rs.next()) {
            Event event = new Event(rs.getInt("id") , rs.getString("title"), dateFromString(rs.getString("date_from")), dateFromString(rs.getString("date_to")));
            event.setRoom(RoomHandler.getRoom(rs.getInt("room_id")));
            event.setDescription(rs.getString("description"));
            events.add(event);
        }
        rs.close();
        close();
        return events;

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
                event.setDateFrom(dateFromString(rs.getString("date_from")));
                event.setDateTo(dateFromString(rs.getString("date_to")));
                event.setRoom(RoomHandler.getRoom(rs.getInt("room_id")));
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
    public Event createEvent(Event event) throws SQLException {
        if(!connect())
            return null;
        String query = "INSERT INTO `EVENT` (`id`,`room_id`, `date_from`, `date_to`, `title`, `description`, `type`, `canceled`) VALUES (NULL, %d, '%s', '%s', '%s', '%s', '%s', 0);";
        query = String.format(query, event.getRoom().getRoomId(), event.getSqlDateFrom(), event.getSqlDateTo(), event.getTitle(), event.getDescription(), "meeting"/*event.getTypeAsString()*/);
        System.out.println(query);
        Statement stm = conn.createStatement();
        boolean rs = stm.execute(query);
        System.out.println(rs);
        close();
        return event;
    }
    public static Event getEvent(int eventId) throws SQLException {
        EventHandler eventHandler = new EventHandler();
        return eventHandler.fetchEvent(String.format("id = %d", eventId));
    }
}

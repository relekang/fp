package no.ntnu.fp.server.storage.db;

import no.ntnu.fp.common.Util;
import no.ntnu.fp.common.model.Employee;
import no.ntnu.fp.common.model.Event;

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
        String query = "SELECT * FROM EVENT INNER JOIN EMPLOYEE_ATTEND_EVENT ON EVENT.id = EMPLOYEE_ATTEND_EVENT.event_id AND is_admin = 1;";
        Util.print(query);
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            Event event = new Event(rs.getInt("id") , rs.getString("title"), Util.dateTimeFromString(rs.getString("date_from")), Util.dateTimeFromString(rs.getString("date_to")), EmployeeHandler.getEmployee(rs.getInt("employee_id")));
            event.setRoom(RoomHandler.getRoom(rs.getInt("room_id")));
            event.setDescription(rs.getString("description"));
            events.add(event);
        }
        rs.close();
        close();
        return events;

    }

    public ArrayList<Event> fetchEvents(String arg) throws SQLException {
        ArrayList<Event> events = new ArrayList<Event>();

        if(!connect())
            return events;
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM EVENT INNER JOIN EMPLOYEE_ATTEND_EVENT ON EVENT.id = EMPLOYEE_ATTEND_EVENT.event_id AND is_admin = 1 WHERE " + arg;
        Util.print(query);
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Event event = new Event(rs.getInt("id") , rs.getString("title"), Util.dateTimeFromString(rs.getString("date_from")), Util.dateTimeFromString(rs.getString("date_to")), EmployeeHandler.getEmployee(rs.getInt("employee_id")));
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
            String query = "SELECT * FROM EVENT INNER JOIN EMPLOYEE_ATTEND_EVENT ON EVENT.id = EMPLOYEE_ATTEND_EVENT.event_id AND is_admin = 1 WHERE " + arg;
            Util.print(query);
            ResultSet rs = stmt.executeQuery(query);
            Event event;
            rs.next();
            event = new Event(rs.getInt("id") , rs.getString("title"), Util.dateTimeFromString(rs.getString("date_from")), Util.dateTimeFromString(rs.getString("date_to")), EmployeeHandler.getEmployee(rs.getInt("employee_id")));
            event.setRoom(RoomHandler.getRoom(rs.getInt("room_id")));
            event.setDescription(rs.getString("description"));
            rs.close();
            close();

            return event;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    public Event createEvent(Event event) throws SQLException {
        if(!connect())
            return null;
        String query = "INSERT INTO `EVENT` (`id`,`room_id`, `date_from`, `date_to`, `title`, `description`, `type`, `canceled`) VALUES (NULL, %d, '%s', '%s', '%s', '%s', '%s', %d);";
        query = String.format(query, event.getRoom().getId(), Util.dateTimeToString(event.getDateFrom()), Util.dateTimeToString(event.getDateTo()), event.getTitle(), event.getDescription(), "meeting"/*event.getTypeAsString()*/, event.getIsCanceledAsInt());
        Util.print(query);
        Statement stm = conn.createStatement();
        boolean rs = stm.execute(query);
        if(rs){
            query = "SELECT ID FROM EVENT ORDER BY ID DESC LIMIT BY 1";
            Util.print(query);
            ResultSet res = stm.executeQuery(query);
            res.next();
            int id = res.getInt("id");
            query = "INSERT INTO `EMPLOYEE_ATTEND_EVENT` (`employee_id`, `event_id`, `is_attending`, `is_admin`) VALUES (%d, %d, 1, 1);";
            query = String.format(query, event.getAdmin().getId(), id);
            Util.print(query);
            stm = conn.createStatement();
            rs = stm.execute(query);
            close();
        }
        return event;
    }
    public static Event getEvent(int eventId) throws SQLException {
        EventHandler eventHandler = new EventHandler();
        return eventHandler.fetchEvent(String.format("id = %d", eventId));
    }

    public Event updateEvent(Event event) throws SQLException {
        if(!connect())
            return null;
        String query = "UPDATE  `EVENT` SET `room_id` = %d, `date_from` = '%s', `date_to` = '%s', `title` = '%s', `description` = '%s', `type` = '%s', `canceled` = %d, WHERE  `id` =  %d LIMIT 1 ;";
        Util.print(query);
        query = String.format(query, event.getRoom().getId(), Util.dateTimeToString(event.getDateFrom()), Util.dateTimeToString(event.getDateTo()), event.getTitle(), event.getDescription(), event.getIsCanceledAsInt(), event.getID());
        Statement stm = conn.createStatement();
        boolean rs = stm.execute(query);
        close();
        return event;
    }

    public ArrayList<Event> fetchEventsForUser(String arg) throws SQLException {
        ArrayList<Event> events = new ArrayList<Event>();

        if(!connect())
            return events;
        Statement stmt = conn.createStatement();
        String query = "SELECT EVENT.id, room_id, date_from, date_to, title, description, type, canceled, employee_id  FROM EVENT INNER JOIN EMPLOYEE_ATTEND_EVENT ON (EVENT.id = EMPLOYEE_ATTEND_EVENT.event_id) WHERE (EMPLOYEE_ATTEND_EVENT.employee_id = %s);";
        query = String.format(query, arg);
        Util.print(query);
        ResultSet rs = stmt.executeQuery(query);


        while (rs.next()) {
            Event event = new Event(rs.getInt("id"), rs.getString("title"), Util.dateTimeFromString(rs.getString("date_from")), Util.dateTimeFromString(rs.getString("date_to")), EmployeeHandler.getEmployee(rs.getInt("employee_id")));
            event.setRoom(RoomHandler.getRoom(rs.getInt("room_id")));
            event.setDescription(rs.getString("description"));
            events.add(event);
        }
        rs.close();
        close();
        return events;
    }
}

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
            String query = "SELECT * FROM EVENT INNER JOIN EMPLOYEE_ATTEND_EVENT ON EVENT.id = EMPLOYEE_ATTEND_EVENT.event_id AND is_admin = 1 WHERE " + arg + ";";
            Util.print(query);
            ResultSet rs = stmt.executeQuery(query);
            Event event = null;
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
    public int createEvent(Event event) throws SQLException {
        if(!connect())
            return 0;
        String query = "INSERT INTO `EVENT` (`id`,`room_id`, `date_from`, `date_to`, `title`, `description`, `type`, `canceled`) VALUES (NULL, %d, '%s', '%s', '%s', '%s', '%s', %d);";
        query = String.format(query, event.getRoom().getId(), Util.dateTimeToString(event.getDateFrom()), Util.dateTimeToString(event.getDateTo()), event.getTitle(), event.getDescription(), "meeting"/*event.getTypeAsString()*/, event.getIsCanceledAsInt());
        Util.print(query);
        Statement stm = conn.createStatement();
        boolean rs = stm.execute(query);
        stm.close();
        stm = conn.createStatement();
        query = "SELECT ID FROM EVENT ORDER BY ID DESC LIMIT 1;";
        Util.print(query);
        ResultSet res = stm.executeQuery(query);
        res.next();
        int id = res.getInt("id");
        res.close();
        query = "INSERT INTO `EMPLOYEE_ATTEND_EVENT` (`employee_id`, `event_id`, `is_attending`, `is_admin`) VALUES (%d, %d, 1, 1);";
        query = String.format(query, event.getAdmin().getId(), id);
        Util.print(query);
        stm.close();
        stm = conn.createStatement();
        rs = stm.execute(query);
        close();
        return id;
    }
    public static Event getEvent(int eventId) throws SQLException {
        EventHandler eventHandler = new EventHandler();
        return eventHandler.fetchEvent(String.format("id = %d", eventId));
    }

    public Event updateEvent(Event event) throws SQLException {
        if(!connect())
            return null;
        String query = "UPDATE  `EVENT` SET `room_id` = %d, `date_from` = '%s', `date_to` = '%s', `title` = '%s', `description` = '%s', `type` = '%s', `canceled` = %d WHERE  `id` =  %d LIMIT 1 ;";
        query = String.format(query, event.getRoom().getId(), Util.dateTimeToString(event.getDateFrom()), Util.dateTimeToString(event.getDateTo()), event.getTitle(), event.getDescription(), "meeting", event.getIsCanceledAsInt(), event.getID());
        Util.print(query);
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
            event.setParticipants(fetchParticipantsForEvent(event.getID()));
            events.add(event);
        }
        rs.close();
        close();
        return events;
    }

    private ArrayList<Employee> fetchParticipantsForEvent(int eventId) throws SQLException {
        ArrayList<Employee> employees = new ArrayList<Employee>();

        if(!connect())
            return employees;
        Statement stmt = conn.createStatement();
        String query = "SELECT employee_id, is_attending FROM EMPLOYEE_ATTEND_EVENT WHERE event_id = %d;";
        query = String.format(query, eventId);
        Util.print(query);
        ResultSet rs = stmt.executeQuery(query);


        while (rs.next()) {
            Employee employee = new EmployeeHandler().fetchEmployee("id=" + rs.getInt("employee_id"));
            employees.add(employee);
        }
        rs.close();
        close();
        return employees;
    }

    public void addParticipants(Event event, int id) throws SQLException {
        if(!connect()) return;
        String query;
        Statement stm;
        int admin;
        for(Employee participant:event.getParticipants()){
            if(participant == event.getAdmin()) admin = 1;
            else admin = 0;
            query = "INSERT INTO `EMPLOYEE_ATTEND_EVENT` (`employee_id`, `event_id`, `is_attending`, `is_admin`) VALUES (%d, %d, 1, %d);";
            query = String.format(query, participant.getId(), id, admin);
            Util.print(query);
            stm = conn.createStatement();
            boolean rs = stm.execute(query);
            stm.close();
        }
            close();

    }

    public void updateParticipants(Event event) throws SQLException {
        if (!connect()) return;
        String query;
        Statement stm = conn.createStatement();
        int admin;
        ArrayList<Employee> participants = fetchParticipantsForEvent(event.getID());
        for (Employee participant : event.getParticipants()) { Util.print(participant.getName()); }
        for (Employee participant : participants)            { Util.print(participant.getName()); }
        for (Employee participant : event.getParticipants()) {
            if (participant == event.getAdmin()) admin = 1;
            else admin = 0;
            if (!participants.contains(participant)) {
                query = "INSERT IGNORE INTO `EMPLOYEE_ATTEND_EVENT` (`employee_id`, `event_id`, `is_attending`, `is_admin`) VALUES (%d, %d, 1, %d);";
                query = String.format(query, participant.getId(), event.getID(), admin);
                Util.print(query);

                stm.execute(query);

            }
        }
        for (Employee p : participants) {
            if (!event.getParticipants().contains(p)) {
                query = "DELETE FROM `EMPLOYEE_ATTEND_EVENT` WHERE `employee_id` = '%d' AND `event_id` = '%d' LIMIT 1";
                query = String.format(query, p.getId(), event.getID());
                stm.execute(query);
            }
        }
        stm.close();
        close();
    }

    public void delete(String arg) throws SQLException {
        if (!connect()) return;
        Statement stm = conn.createStatement();
        String query = "DELETE FROM `EVENT` WHERE %s LIMIT 1";
        query = String.format(query,arg);
        stm.execute(query);
        stm.close();
        close();
    }

    public void deleteParticipantFromEvent(Employee p, int event_id) throws SQLException {
        if (!connect()) return;
        Statement stm = conn.createStatement();
        String query = "DELETE FROM `EMPLOYEE_ATTEND_EVENT` WHERE `employee_id` = '%d' AND `event_id` = '%d' LIMIT 1";
        query = String.format(query, p.getId(), event_id);
        stm.execute(query);
        stm.close();
        close();
    }
}

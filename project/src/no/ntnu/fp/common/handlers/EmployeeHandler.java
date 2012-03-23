package no.ntnu.fp.common.handlers;

import no.ntnu.fp.common.model.Employee;
import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.common.model.Notification;

import java.util.ArrayList;

public class EmployeeHandler {
    public ArrayList<Event> getEvents(int weekNr){
        ArrayList<Event> events = new ArrayList<Event>();
        events.add(Event.getDummyEvent("Superevent"));
        return events;
    }

    public Event getEvent(int id){
        return Event.getDummyEvent("Superevent");
    }
    
    public static ArrayList<Employee> getAllEmployees(){
        ArrayList<Employee> list = new ArrayList<Employee>();
        list.add(Employee.getExampleEployee());
        return list;
    }

    public ArrayList<Notification> getAllNotifications(){
        ArrayList<Notification> list = new ArrayList<Notification>();
        new Notification(1, new Event("Event"/*, testGuy*/), "2012-03-19 12:04:36", 1, Notification.NotificationType.INVITATION/*, testGuy*/);
        new Notification(2, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:37", 0, Notification.NotificationType.ACCEPTED);
        new Notification(3, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:38", 0, Notification.NotificationType.DECLINED);
        new Notification(4, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:40", 0, Notification.NotificationType.DELETION);
        new Notification(5, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:39", 0, Notification.NotificationType.CHANGE, true, false, false, false);
        new Notification(6, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:39", 0, Notification.NotificationType.CHANGE, false, true, false, false);
        new Notification(7, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:39", 0, Notification.NotificationType.CHANGE, false, true, false, true);
        new Notification(8, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:39", 0, Notification.NotificationType.CHANGE, true, true, true, false);

        return list;
    }

}

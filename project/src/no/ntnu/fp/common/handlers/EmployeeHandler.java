package no.ntnu.fp.common.handlers;

import no.ntnu.fp.client.Connection;
import no.ntnu.fp.common.Constants;
import no.ntnu.fp.common.model.Employee;
import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.common.model.Notification;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class EmployeeHandler {

    private int id;

    public EmployeeHandler(int id){
        this.id = id;
    }
    public ArrayList<Event> getEvents(int weekNr){
        if(Constants.use_server) return getEventsFromServer();
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
        if(Constants.use_server) return getNotificationsFromServer();
        ArrayList<Notification> list = new ArrayList<Notification>();
        list.add(new Notification(1, new Event("Event"/*, testGuy*/), "2012-03-19 12:04:36", 1, Notification.NotificationType.INVITATION/*, testGuy*/));
        list.add(new Notification(2, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:37", 0, Notification.NotificationType.ACCEPTED));
        list.add(new Notification(3, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:38", 0, Notification.NotificationType.DECLINED));
        list.add(new Notification(4, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:40", 0, Notification.NotificationType.DELETION));
        list.add(new Notification(5, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:39", 0, Notification.NotificationType.CHANGE, true, false, false, false));
        list.add(new Notification(6, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:39", 0, Notification.NotificationType.CHANGE, false, true, false, false));
        list.add(new Notification(7, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:39", 0, Notification.NotificationType.CHANGE, false, true, false, true));
        list.add(new Notification(8, new Event("Event"/*, testGuy*/), "2012-03-20 13:05:39", 0, Notification.NotificationType.CHANGE, true, true, true, false));

        return list;
    }

    private ArrayList<Event> getEventsFromServer() {
        ArrayList<Event> events = new ArrayList<Event>();
        try {
            Connection conn = new Connection();
            try {
                conn.send(new JSONObject().put("key", "event").put("action", "all_for_user").put("argument", Integer.toString(this.id)));
                String message = conn.receive();
                System.out.println(message);
                JSONArray jsonArray = new JSONArray(message);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.optJSONObject(i);
                    Event e = new Event(object);
                    events.add(e);
                }
                conn.close();

            } catch (JSONException e) {
                conn.close();
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }
    private ArrayList<Notification> getNotificationsFromServer() {
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        try {
            Connection conn = new Connection();
            try {
                conn.send(new JSONObject().put("key", "notification").put("action", "all_for_user").put("argument", Integer.toString(this.id)));
                String message = conn.receive();
                System.out.println(message);
                JSONArray jsonArray = new JSONArray(message);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.optJSONObject(i);
                    Notification notification = new Notification(object);
                    notifications.add(notification);
                }
                conn.close();
            } catch (JSONException e) {
                conn.close();
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notifications;
    }

}

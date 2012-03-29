package no.ntnu.fp.common.model;


import no.ntnu.fp.common.Constants;
import no.ntnu.fp.common.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class Notification implements Model{
    private int ID;
    private final Calendar cal;
    private Event event;
    private PropertyChangeSupport pcs;
    private NotificationType type;
    private Calendar timestamp;

    public Notification(int id, Event event, String timestamp, NotificationType type){
        cal = Util.getCalendar();
    	this.ID = id;
        this.event = event;
        this.timestamp.setTime(Util.dateFromString(timestamp));
        this.type = type;
    }

    public Notification(JSONObject object) throws JSONException, SQLException {
        this(object.getInt("id"), new Event(object.getJSONObject("event"), Constants.IS_SERVER), object.getString("timestamp"), NotificationType.valueOf(object.getString("type")));
    }



    /**
     * returns the description string
     * @return description
     */
    public String getDescription() {
        String description = "";
        if (event == null) return description;
        switch(type) {
            case INVITATION:
                description = event.getAdmin().getFirstName() + " invited you to " + event.getTitle(); break;
            case DELETION:
                description = event.getAdmin().getFirstName() + " deleted " + event.getTitle(); break;
            case ACCEPTED:
                description = event.getAdmin().getFirstName() + " accepted your invitation to " + event.getTitle(); break;
            case DECLINED:
                description = event.getAdmin().getFirstName() + " declined your invitation to " + event.getTitle(); break;
            case CHANGE:
            	description = event.getAdmin().getFirstName() + " edited " + event.getTitle(); break;
        }
        return description;
    }
    
    /**
     * toString method, prints out the description.
     */
    @Override
    public String toString() {
        return getDescription();
    }

    public int getID() {
        return ID;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getTimestampString() {
        return Util.dateTimeToString(timestamp.getTime());
    }
    
    public Date getTimestamp() {
    	return timestamp.getTime();
    }
    
    public NotificationType getType() {
    	return type;
    }


    @Override
    public boolean save() {
        return false;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    
    public String getFirstName(Employee employee) {
    	return employee.getName().split(" ")[0];
    }

    public JSONObject toJson() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id", ID);
        object.put("event", event.toJson());
        object.put("type", type.toString());
        object.put("timestamp", Util.dateTimeToString(timestamp.getTime()));
        return object;
    }

    public enum NotificationType {
    	INVITATION, DELETION, CHANGE, ACCEPTED, DECLINED;
    }

}
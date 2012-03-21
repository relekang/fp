package no.ntnu.fp.model;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Calendar;

public class Notification implements Model{
    private String description;
    private final int ID;
    private final Calendar cal;
    private boolean is_invitation;
    private Event event;
    private PropertyChangeSupport pcs;
    private NotificationType type;
    // Temporary timestamp variable:
    private String timestamp;

    /**
     * Constructor for Notification
     * @param id
     * @param timestamp
     * @param description
     * @param is_invitation
     */
    public Notification(int id, String timestamp, String description, 
    		int is_invitation, NotificationType type){
        this.ID = id;
        this.description = description;
        cal = Calendar.getInstance();
//        this.description = timestamp;
        if (is_invitation == 1){
            this.is_invitation = true;
        } else {
            this.is_invitation = false;
        }
        this.type = type;
        this.timestamp = timestamp;

    }
    
    /**
     * returns the description string
     * @return description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Sets a string for the description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
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
        return timestamp;
    }
    
    public Calendar getTimestamp() {
    	return Calendar.getInstance();
    }
    
    public NotificationType getType() {
    	return type;
    }

    public int isInvitationAsInt() {
        if (this.is_invitation) return 1;
        else return 0;

    }

    @Override
    public boolean save() {
        return false;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public enum NotificationType {
    	INVITATION, DELETION, CHANGE, ACCEPTED, DECLINED;
    }
}
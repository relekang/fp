package no.ntnu.fp.model;


import java.util.Calendar;

public class Notification implements Model{
    private String description;
    private final int ID;
    private final Calendar cal;
    private boolean is_invitation;
    private Event event;

    /**
     * Constructor for Notification
     * @param id
     * @param timestamp
     * @param description
     * @param is_invitation
     */
    public Notification(int id, String timestamp, String description, int is_invitation){
        this.ID = id;
        this.description = description;
        cal = Calendar.getInstance();
//        this.description = timestamp;
        if (is_invitation == 1){
            this.is_invitation = true;
        } else {
            this.is_invitation = false;
        }

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
        return "";
    }

    public int isInvitationAsInt() {
        if (this.is_invitation) return 1;
        else return 0;

    }

    @Override
    public boolean save() {
        return false;
    }
}

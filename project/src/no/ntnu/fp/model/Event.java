 package no.ntnu.fp.model;


import no.ntnu.fp.client.Connection;
import no.ntnu.fp.controller.ClientApplication;
import no.ntnu.fp.storage.db.EventHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Event implements Model{
	
	public static final int TITLE_LENGTH = 64;
	
	public final String ADDED_NEW_PARTICIPANT = "new Participant";
	public final String DESCRIPTION_CHANGED = "description changed";
	public final String ROOM_CHANGED = "room changed";
	public final String DATETO_CHANGED = "dateTo changed";
	public final String DATEFROM_CHANGED = "dateFrom changed";
	public final String TITLE_CHANGED = "title changed";
	
    private int ID;	
    private String title;
    private Date dateFrom;
    private Date dateTo;
    private Room room;
    private String description;
    private boolean isCanceled;
    private PropertyChangeSupport pcs;
    private ArrayList<Employee> participants;
    private Employee admin;


    private Event() {
    	pcs = new PropertyChangeSupport(this);
    	participants = new ArrayList<Employee>();
        dateFrom = new Date();
        dateTo = new Date();
    }
    
    /**
     * Constructor, sets the title of the event.
     * @param title
     */
    public Event(String title, Employee admin){
    	this();
        ID = 0;
        setTitle(title);
        this.admin = admin;
        participants = new ArrayList<Employee>();
        participants.add(this.admin);
    }
    
    public Event(String title){
    	this();
    	ID = 0;
    	setTitle(title);
    }
    
    /**
     * Constructor, sets the title of the event, the start and end date.
     * @param title
     * @param dateFrom
     * @param dateTo
     */
    public Event(String title, Date dateFrom, Date dateTo){
    	this();
        setTitle(title);
        setDateFrom(dateFrom);
        setDateTo(dateTo);
    }
    
    public Event(int id, String title, Date dateFrom, Date dateTo){
    	this();
        ID = id;
        setTitle(title);
        setDateFrom(dateFrom);
        setDateTo(dateTo);
    }
    public Event(JSONObject object) throws JSONException {
        this(object.getInt("id"), object.getString("title"), new Date(object.getString("date_from")), new Date(object.getString("date_to")));
    }

    public int getID(){
        return ID;
    }
    
    /**
     * 
     * @return title
     */
    public String getTitle() {
        return title;
    }
    
    
    /**
     * Sets the title if the title length is less than or equals to the max allowed title length (64)
     * @param title
     */
    public void setTitle(String title) {
    	if(title.length() <= TITLE_LENGTH){
    		String oldTitle = this.title;
    		this.title = title;
    		pcs.firePropertyChange(TITLE_CHANGED, oldTitle, this.title);
    	}
    }

    /**
     * Return the date that the event starts from.
     * @return dateFrom
     */
    public Date getDateFrom() {
        return dateFrom;
    }
    
    /**
     * Sets the date that the event starts from.
     * @param dateFrom
     */
    public void setDateFrom(Date dateFrom) {
    	Date oldDateFrom = this.dateFrom;
        this.dateFrom = dateFrom;
        pcs.firePropertyChange(DATEFROM_CHANGED, oldDateFrom, this.dateFrom);
    }
    
    /**
     * Return the date that the event ends.
     * @return dateTo
     */
    public Date getDateTo() {
        return dateTo;
    }

   /**
    *  Sets the end date if it's after the startdate.
    * @param dateTo
    */
    public void setDateTo(Date dateTo) {
        if(getDateFrom() != null && getDateFrom().before(dateTo)){
        	Date oldDateTo = this.dateTo;
            this.dateTo = dateTo;
            pcs.firePropertyChange(DATETO_CHANGED, oldDateTo, this.dateTo);
        }
    }
	
    /**
	 * Returns the selected room for the given event
	 * @return room
	 */
    public Room getRoom() {
        return room;
    }
    
    /**
     * sets a new room for the given event
     * @param room
     */
    public void setRoom(Room room) {
    	Room oldRoom = this.room;
        this.room = room;
        pcs.firePropertyChange(ROOM_CHANGED, oldRoom, this.room);
    }
    
    /**
     *	Returnds the description if the description is not null
     * @return description
     */
    public String getDescription() {
        return description == null ? "" : description;
    }
    
    /**
     * sets the description
     * @param description
     */
    public void setDescription(String description) {
    	String oldDescription = this.description;
        this.description = description;
        pcs.firePropertyChange(DESCRIPTION_CHANGED, oldDescription, description);
    }
    
    public void addParticipants (Employee employee){
    	participants.add(employee);
    	pcs.firePropertyChange(ADDED_NEW_PARTICIPANT, employee, participants);
    }

    /**
     * Gets the date from sql, and converts it to an "util" date
     * @return date
     */
    public String getSqlDateFrom(){
        if(dateTo != null)
            return new java.sql.Date(dateFrom.getTime()).toString();
        else
            return "0000-00-00";
    }
    
    /**
     * Gets the date from sql, and converts it to an "util" date
     * @return
     */
    public String getSqlDateTo(){
        if(dateTo != null)
            return new java.sql.Date(dateTo.getTime()).toString();
        else
            return "0000-00-00";
    }

    public boolean save(){
        try {
            EventHandler eventHandler = new EventHandler();
            if(ID == 0) {
                Event e = eventHandler.createEvent(this);
                this.ID = e.getID();
                
            }
            else eventHandler.updateEvent(this);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public int getIsCanceledAsInt() {
        if(isCanceled) return 1;
        return 0;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id", getID());
        object.put("title", getTitle());
        object.put("date_from", getDateFrom().toString());
        object.put("date_to", getDateTo().toString());
        object.put("room", getRoom().toString());
        object.put("description", getDescription());

        return object;
    }
}

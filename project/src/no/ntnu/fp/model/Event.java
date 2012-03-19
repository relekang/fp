 package no.ntnu.fp.model;


import java.util.Date;

public class Event {
	
	public static final int TITLE_LENGTH = 64;
    private final int ID;	
    private String title;
    private Date dateFrom;
    private Date dateTo;
    private Room room;
    private String description;
    
    
    
    /**
     * Constructor, sets the title of the event.
     * @param title
     */
    public Event(String title){
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
        ID = 0;
        setTitle(title);
        setDateFrom(dateFrom);
        setDateTo(dateTo);
    }
    public Event(int id, String title, Date dateFrom, Date dateTo){
        ID = id;
        setTitle(title);
        setDateFrom(dateFrom);
        setDateTo(dateTo);
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
    	if(title.length() <= TITLE_LENGTH)
    		this.title = title;
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
        this.dateFrom = dateFrom;
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
        if(getDateFrom() != null && getDateFrom().before(dateTo))
            this.dateTo = dateTo;
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
        this.room = room;
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
        this.description = description;
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

}

package no.ntnu.fp.model;


import java.util.Date;

public class Event {
    private String title;
    private Date dateFrom;
    private Date dateTo;
    private Room room;
    private String description;

    public Event(String title){
        setTitle(title);
    }

    public Event(String title, Date dateFrom, Date dateTo){
        setTitle(title);
        setDateFrom(dateFrom);
        setDateTo(dateTo);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        if(getDateFrom() != null && getDateFrom().before(dateTo))
            this.dateTo = dateTo;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public java.sql.Date getSqlDateFrom(){
        return new java.sql.Date(dateFrom.getTime());
    }

    public java.sql.Date getSqlDateTo(){
        return new java.sql.Date(dateTo.getTime());
    }

}

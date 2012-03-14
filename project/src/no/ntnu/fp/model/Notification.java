package no.ntnu.fp.model;


public class Notification {
    private String description;
    
    /**
     * Constructor for Notification
     * @param desc
     */
    public Notification(String desc){
        description = desc;
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
}

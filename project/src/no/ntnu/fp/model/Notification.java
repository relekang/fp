package no.ntnu.fp.model;


public class Notification {
    private String description;
    public Notification(String desc){
        description = desc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}

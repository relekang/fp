package no.ntnu.fp.common.model;


import no.ntnu.fp.common.handlers.RoomHandler;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Room extends RoomHandler implements Model{

	private final int ROOM_ID; 
	
	private String name;
	private String location;
	private int capacity;
    private PropertyChangeSupport pcs;
    
    private Room(int id) {
        super(id);
    	pcs = new PropertyChangeSupport(this);
    	ROOM_ID = id;
    }
    
    public Room(String name, String location, int capacity) {
    	this(0);
    	setName(name);
    	setLocation(location);
    	setCapacity(capacity);
    }
    public Room(int id, String name, String location, int capacity) {
        this(id);
        setName(name);
        setLocation(location);
        setCapacity(capacity);
    }

    public Room(JSONObject object) throws JSONException {
        this(object.getInt("id"));
        setName(object.getString("name"));
        setLocation(object.getString("location"));
        setCapacity(object.getInt("capacity"));
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		if(capacity > 0)
			this.capacity = capacity;
	}
    
    public int getId() {
    	return ROOM_ID;
    }
    
    @Override
    public String toString() {
    	return name + ", " + location; 
    }

    @Override
    public boolean save() {
        return false;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public JSONObject toJson() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id", getId());
        object.put("name", getName());
        object.put("location", getLocation());
        object.put("capacity", getCapacity());
        return object;
    }
}

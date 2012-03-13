package no.ntnu.fp.model;


public class Room {

	private final int ROOM_ID; 
	
	private String name;
	private String location;
	private int capacity;
	
	
    public Room(int roomId){
        //Todo fetch from database from id in db
    	ROOM_ID = 0;
    }
    
    public Room(String name, String location, int capacity) {
    	ROOM_ID = 0;
    	setName(name);
    	setLocation(location);
    	setCapacity(capacity);
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
    
    public int getRoomId() {
    	return ROOM_ID;
    }
    
}

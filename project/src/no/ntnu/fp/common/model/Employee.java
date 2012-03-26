package no.ntnu.fp.common.model;

import no.ntnu.fp.common.Util;
import no.ntnu.fp.common.handlers.EmployeeHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;

public class Employee extends EmployeeHandler implements Model, Comparable<Employee> {

    public enum Gender{
        FEMALE, MALE
    };

    private int ID;
	private String name;
	private String email;
	private Date dateOfBirth;
	private Gender gender;
	private PropertyChangeSupport propChangeSupp;


	public final static String NAME_PROPERTY_NAME = "name";
	public final static String EMAIL_PROPERTY_NAME = "email";
	public final static String DATEOFBIRTH_PROPERTY_NAME = "dateOfBirth";
	public final static String GENDER_PROPERTY_NAME = "gender";

	public enum Status{
		ACCEPTED, PENDING, DECLINED
	}
	
	public static Employee getExampleEployee() {
		return new Employee("Bernt Arne", "awsm@test.no", new Date(1990, 12, 31), Gender.MALE);
	}

    public Employee(JSONObject jsonEmployee) throws JSONException {
        this(jsonEmployee.getInt("id"), jsonEmployee.getString("name"), jsonEmployee.getString("email"), Util.dateFromString(jsonEmployee.getString("date_of_birth")), Gender.FEMALE );
    }
	
	private Employee(int id) {
        super(id);
        this.ID = 0;
		name = "";
		email = "";
		dateOfBirth = new Date();
		propChangeSupp = new PropertyChangeSupport(this);
	}
	
	public Employee(String name, String email, Date dateOfBirth, Gender gender) {
		this(0);
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
	}

    public Employee(int id, String name, String email, Date dateOfBirth, Gender gender) {
        this(id);
    	this.ID = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }
	
	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		PropertyChangeEvent event = new PropertyChangeEvent(this, NAME_PROPERTY_NAME, oldName, name);
		propChangeSupp.firePropertyChange(event);
	}
	
	public void setEmail(String email) {
		String oldEmail = this.email;
		this.email = email;
		PropertyChangeEvent event = new PropertyChangeEvent(this, EMAIL_PROPERTY_NAME, oldEmail, this.email);
		propChangeSupp.firePropertyChange(event);
	}
	

	public void setDateOfBirth(Date dateOfBirth) {
		Date oldDateOfBirth = this.dateOfBirth;
		this.dateOfBirth = dateOfBirth;
		PropertyChangeEvent event = new PropertyChangeEvent(this, DATEOFBIRTH_PROPERTY_NAME, oldDateOfBirth, this.dateOfBirth);
		propChangeSupp.firePropertyChange(event);
	}
	
	public void setGender(Gender gender) {
		Gender oldGender = this.gender;
		this.gender = gender;
		PropertyChangeEvent event = new PropertyChangeEvent(this, GENDER_PROPERTY_NAME, oldGender, gender);
		propChangeSupp.firePropertyChange(event);
	}

	public String getName() {
		return name;
	}

	/**
	 * Returns the person's email address.
	 * 
	 * @return The person's email address.
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Returns the person's date of birth.
	 * 
	 * @return The person's date of birth.
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	/**
	 * Returns this object's unique identification.
	 * 
	 * @return The person's unique identification.
	 */
	public long getId() {
		return ID;
	}
	/**
	 * Returns the person's gender.
	 * 
	 * @return The person's gender.
	 */
	public Gender getGender() {
		return gender;
	}
	
	/**
	 * Add a {@link java.beans.PropertyChangeListener} to the listener list.
	 * 
	 * @param listener The {@link java.beans.PropertyChangeListener} to be added.
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propChangeSupp.addPropertyChangeListener(listener);
	}
	
	/**
	 * Remove a {@link java.beans.PropertyChangeListener} from the listener list.
	 * 
	 * @param listener The {@link java.beans.PropertyChangeListener} to be removed.
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propChangeSupp.removePropertyChangeListener(listener);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object obj) {
		if (super.equals(obj))
			return true;
		
		if (obj.getClass() != this.getClass())
			return false;
		
		Employee aPerson = (Employee)obj;
		
		if (aPerson.getName().compareTo(getName()) != 0) 
			return false;
		if (aPerson.getEmail().compareTo(getEmail()) != 0)
			return false;
		if (aPerson.getDateOfBirth().compareTo(getDateOfBirth()) != 0)
			return false;
		
		return true;
	}
	
	/**
	 * 
	 */
	public Status getAccepted(){
		//TODO connect to database and get if accepted event, pending or declined
		
		return Status.ACCEPTED;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override 
	public String toString() {
		return getName();
	}

    public JSONObject toJson() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id", getId());
        object.put("name", getName());
        object.put("email", getEmail());
        object.put("date_of_birth", Util.dateToString(getDateOfBirth()));
        object.put("gender", getGender().toString());

        return object;
    }

    @Override
    public boolean save() {
        return false;
    }

	@Override
	public int compareTo(Employee e) {
		return getName().compareTo(e.getName());
	}
	
}

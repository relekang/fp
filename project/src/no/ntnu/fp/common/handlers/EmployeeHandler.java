package no.ntnu.fp.common.handlers;

import no.ntnu.fp.common.model.Employee;
import no.ntnu.fp.common.model.Event;

import java.util.ArrayList;

public class EmployeeHandler {
    public ArrayList<Event> getEvents(int weekNr){
        ArrayList<Event> events = new ArrayList<Event>();
        events.add(Event.getDummyEvent("Superevent"));
        return events;
    }

    public Event getEvent(int id){
        return Event.getDummyEvent("Superevent");
    }
    
    public static ArrayList<Employee> getAllEmployees(){
        ArrayList<Employee> list = new ArrayList<Employee>();
        list.add(Employee.getExampleEployee());
        return list;
    } 
}

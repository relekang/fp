package no.ntnu.fp.server.storage.db;

import no.ntnu.fp.common.Util;
import no.ntnu.fp.common.model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class EmployeeHandler extends DbHandler {

    public EmployeeHandler() throws SQLException {
        super();
    }
    public Employee fetchEmployee(String arg){
        try {
            if(!connect())
                return null;
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM EMPLOYEE WHERE " + arg;
            Util.print(query);
            ResultSet rs = stmt.executeQuery(query);
            
            Employee employee;
            rs.next();
            employee = new Employee(rs.getInt("id"),rs.getString("name"), rs.getString("email"), Util.dateFromString(rs.getString("date_of_birth")), Employee.Gender.MALE);
            rs.close();
            close();
            return employee;
        } catch (SQLException e) { return null; }
    }
    public static Employee getEmployee(int id) {
        try {
            EmployeeHandler handler = new EmployeeHandler();
            Employee e = handler.fetchEmployee("id = " + id);
            handler.close();
            return e;
        } catch (SQLException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return  null;
    }

    public ArrayList<Employee> fetchAllEmployees() {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        try {
            if (!connect())
                return null;
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM EMPLOYEE";
            Util.print(query);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Employee employee = new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("email"), Util.dateFromString(rs.getString("date_of_birth")), Employee.Gender.MALE);
                employees.add(employee);
            }
            rs.close();
            close();
            return employees;
        } catch (SQLException e) {
            return null;
        }
    }
}

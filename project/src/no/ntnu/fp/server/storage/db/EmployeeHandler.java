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
//
//    public ArrayList<Employee> fetchAllEmployees() throws SQLException {
//        ArrayList<Employee> employees = new ArrayList<Employee>();
//
//        if(!connect())
//            return employees;
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLOYEE");
//
//        while (rs.next()) {
//            Employee employee = new Employee(rs.getInt("id") , rs.getString("name"),rs.getString("email"), rs.getString("date"), (Employee.Gender)rs.getString("gender"));
//            employee.setRoom(RoomHandler.getRoom(rs.getInt("room_id")));
//            employee.setDescription(rs.getString("description"));
//            employees.add(employee);
//        }
//        rs.close();
//        close();
//        return employees;
//
//    }
//
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
//    public Employee createEmployee(Employee employee) throws SQLException {
//        if(!connect())
//            return null;
//        String query = "INSERT INTO `EMPLOYEE` (`id`,`room_id`, `date_from`, `date_to`, `title`, `description`, `type`, `canceled`) VALUES (NULL, %d, '%s', '%s', '%s', '%s', '%s', 0);";
//        query = String.format(query, employee.getRoom().getRoomId(), employee.getSqlDateFrom(), employee.getSqlDateTo(), employee.getTitle(), employee.getDescription(), "meeting"/*employee.getTypeAsString()*/);
//        System.out.println(query);
//        Statement stm = conn.createStatement();
//        boolean rs = stm.execute(query);
//        System.out.println(rs);
//        close();
//        return employee;
//    }
//    public static Employee getEmployee(int employeeId) throws SQLException {
//        EmployeeHandler employeeHandler = new EmployeeHandler();
//        return employeeHandler.fetchEmployee(String.format("id = %d", employeeId));
//    }

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

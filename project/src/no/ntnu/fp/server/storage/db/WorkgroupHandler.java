package no.ntnu.fp.server.storage.db;

import no.ntnu.fp.common.model.Workgroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class WorkgroupHandler extends DbHandler {

    public WorkgroupHandler() throws SQLException {
        super();
    }

    public ArrayList<Workgroup> fetchAllGroups() throws SQLException {
        ArrayList<Workgroup> groups = new ArrayList<Workgroup>();

        if(!connect())
            return groups;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM WORKGROUP");

        while (rs.next()) {
            Workgroup group = new Workgroup(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
            groups.add(group);
        }
        rs.close();
        close();
        return groups;

    }

    public Workgroup fetchGroup(String arg){
        try {
            if(!connect())
                return null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM WORKGROUP WHERE " + arg);
            Workgroup group = null;
            while (rs.next()) {
                group = new Workgroup(rs.getInt("id"), rs.getString("name"), rs.getString("email"));

            }
            rs.close();
            close();

            return group;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    public Workgroup createGroup(Workgroup group) throws SQLException {
        if(!connect())
            return null;


        String query = "INSERT INTO `WORKGROUP` (`id`, `name`, `email`) VALUES (NULL, '%s', '%s');";
        query = String.format(query, group.getName(), group.getEmail());
        System.out.println(query);
        Statement stm = conn.createStatement();
        boolean rs = stm.execute(query);
        System.out.println(rs);
        close();
        return group;
    }

    public static Workgroup getGroup(int groupId) throws SQLException {
        WorkgroupHandler roomHandler = new WorkgroupHandler();
        return roomHandler.fetchGroup(String.format("id = %d", groupId));
    }
}

package no.ntnu.fp.server;

import no.ntnu.fp.storage.db.DbHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Authentication {

    public static boolean authenticate(String username, String password) throws SQLException {
        DbHandler handler = new DbHandler();
        boolean loggedIn = false;
        if(!handler.connect())
            return false;
        String query = "SELECT COUNT(*) AS rowcount FROM EMPLOYEE WHERE username='%s' AND password='%s'";
        query = String.format(query, username, password);
        Statement stm = handler.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(query);
        rs.next();
        if(rs.getInt("rowcount") == 1) loggedIn = true;
        handler.close();
        return loggedIn;
    }
}

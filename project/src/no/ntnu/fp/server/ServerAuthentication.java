package no.ntnu.fp.server;

import no.ntnu.fp.model.Employee;
import no.ntnu.fp.storage.db.DbHandler;
import no.ntnu.fp.storage.db.EmployeeHandler;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerAuthentication {

    public static Employee authenticate(String username, String password) throws SQLException {
        EmployeeHandler handler = new EmployeeHandler();
        Employee employee = null;
        if(!handler.connect())
            return null;
        String query = "SELECT COUNT(*) AS rowcount FROM EMPLOYEE WHERE username='%s' AND password='%s'";
        query = String.format(query, username, password);
        Statement stm = handler.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(query);
        rs.next();
        if(rs.getInt("rowcount") == 1) {
            String arg = "username='%s' AND password='%s'";
            arg = String.format(arg, username, password);
            employee = handler.fetchEmployee(arg);
        }
        handler.close();
        if (employee != null)
            System.out.println("Authenticated user: " + username);
        else
            System.out.println("Could not authenticated user: " + username);
        return employee;
    }


    private static String convToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return convToHex(sha1hash);
    }
}

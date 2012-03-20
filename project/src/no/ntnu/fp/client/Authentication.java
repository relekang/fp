package no.ntnu.fp.client;

import no.ntnu.fp.controller.ClientApplication;
import no.ntnu.fp.model.Employee;
import no.ntnu.fp.server.ServerAuthentication;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Authentication {

    public static boolean authenticate(String username, String password) throws SQLException {

        try {
            password = SHA1(password);
        }
        catch (NoSuchAlgorithmException e)     { return false; }
        catch (UnsupportedEncodingException e) { return false; }
        
        Employee employee = ServerAuthentication.authenticate(username, password);
        if(employee == null) return false;
        ClientApplication.setCurrentUser(employee);
        return true;
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

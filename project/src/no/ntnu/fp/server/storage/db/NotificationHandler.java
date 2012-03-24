package no.ntnu.fp.server.storage.db;

import no.ntnu.fp.common.Util;
import no.ntnu.fp.common.model.Notification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class NotificationHandler extends DbHandler {

    public NotificationHandler() throws SQLException {
        super();
    }

    public ArrayList<Notification> fetchAllNotifications() throws SQLException {
        ArrayList<Notification> notifications = new ArrayList<Notification>();

        if(!connect())
            return notifications;
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM NOTIFICATION";
        Util.print(query);
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
//            Notification notification = new Notification(rs.getInt("id"), rs.getString("name"), rs.getString("location"), rs.getInt("capacity"));
//            notifications.add(notification);
        }
        rs.close();
        close();
        return notifications;

    }

    public ArrayList<Notification> fetchNotificationsForUser(String arg) throws SQLException {
        ArrayList<Notification> notifications = new ArrayList<Notification>();

        if(!connect())
            return notifications;
        Statement stmt = conn.createStatement();
        String query = "SELECT *  FROM NOTIFICATION INNER JOIN EMPLOYEE_RECEIVE_NOTIFICATION ON (NOTIFICATION.id = EMPLOYEE_RECEIVE_NOTIFICATION.notification_id) WHERE (EMPLOYEE_RECEIVE_NOTIFICATION.employee_id = %s);";
        query = String.format(query, arg);
        Util.print(query);
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            boolean is_invitation;
            if(rs.getInt("is_invitation") == 1) is_invitation = true;
            else is_invitation = false;
            Notification notification = new Notification(rs.getInt("id"), EventHandler.getEvent(rs.getInt("event_id")), rs.getString("timestamp"), is_invitation, Notification.NotificationType.valueOf(rs.getString("type")));
            notifications.add(notification);
        }
        rs.close();
        close();
        return notifications;

    }

    public Notification fetchNotification(String arg){
        try {
            if(!connect())
                return null;
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM NOTIFICATION WHERE " + arg;
            Util.print(query);
            ResultSet rs = stmt.executeQuery(query);
            Notification notification = null;
            while (rs.next()) {
//                notification = new Notification(rs.getInt("id"),rs.getString("timestamp"), rs.getString("description"), rs.getInt("is_invitation"));

            }
            rs.close();
            close();

            return notification;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Notification createNotification(Notification notification) throws SQLException {
        if(!connect())
            return null;


        String query = "INSERT INTO `NOTIFICATION` (`id`, `event_id`, `established`, `description`, `is_invitation`) VALUES (NULL, %d, '%s', '%s', %d)";
        query = String.format(query, notification.getID(), notification.getEvent().getID(), notification.getTimestampString(), notification.getDescription(), notification.isInvitationAsInt());
        Util.print(query);
        Statement stm = conn.createStatement();
        boolean rs = stm.execute(query);
        close();
        return notification;
    }

    public static Notification getNotification(int notificationId) throws SQLException {
        NotificationHandler notificationHandler = new NotificationHandler();
        return notificationHandler.fetchNotification(String.format("id = %d", notificationId));
    }
}

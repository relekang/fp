package no.ntnu.fp.common.handlers;

import no.ntnu.fp.client.Connection;
import no.ntnu.fp.common.Constants;
import no.ntnu.fp.common.model.Event;
import no.ntnu.fp.common.model.Room;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.Console;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomHandler {

    private int id;

    public RoomHandler(int id){
        this.id = id;
    }

    public static Room getRoom(int id){
        if (Constants.USE_SERVER) return getRoomFromServer("id=" + id);
        return new Room(1, "Drivhuset", "its", 100);
    }

    private static Room getRoomFromServer(String arg) {
        JSONObject json = new JSONObject();
        try {
            Connection conn = new Connection();
            try {
                json.put("key", "room");
                json.put("action", "get");
                json.put("argument", arg);
                conn.send(json);
                JSONObject object = new JSONObject(conn.receive());
                conn.close();
                if(object.getString("key").equals("failure")) return null;
                Room room = new Room(object.getJSONObject("room"));
                return room;

            } catch (JSONException e) {
                e.printStackTrace();
                conn.close();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    public static ArrayList<Room> getRooms() {
        return getRoomsFromServer();
    }

    public static ArrayList<Room> getRoomsFromServer() {
        ArrayList<Room> list = new ArrayList<Room>();
        try {
            Connection conn = new Connection();
            try {
                conn.send(new JSONObject().put("key", "room").put("action", "all"));
                String message = conn.receive();
                conn.close();
                JSONObject jsonObject = new JSONObject(message);
                JSONArray jsonArray = jsonObject.getJSONArray("rooms");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Room r = new Room(object);
                    list.add(r);
                }


            } catch (JSONException e) {
                conn.close();
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}

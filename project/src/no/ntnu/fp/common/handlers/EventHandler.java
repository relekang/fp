package no.ntnu.fp.common.handlers;

import no.ntnu.fp.client.Connection;
import no.ntnu.fp.common.model.Event;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class EventHandler {

    private int id;

    public EventHandler(int id) {
        this.id = id;
    }

    public void updateEvent(Event event) {
        JSONObject json = new JSONObject();
        try {
            Connection conn = new Connection();
            try {
                json.put("key", "event");
                if (event.getID() == 0)
                    json.put("action", "create");
                else
                    json.put("action", "save");
                json.put("event", event.toJson());
                conn.send(json);
                conn.receive();
                conn.close();

            } catch (JSONException e) {
                e.printStackTrace();
                conn.close();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public void deleteOnServer(Event event) {
        JSONObject json = new JSONObject();
        try {
            Connection conn = new Connection();
            try {
                json.put("key", "event");
                json.put("action", "delete");
                json.put("event_id", event.getID());
                conn.send(json);
                conn.receive();
                conn.close();

            } catch (JSONException e) {
                e.printStackTrace();
                conn.close();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}

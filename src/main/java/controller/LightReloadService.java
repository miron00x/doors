package controller;

import entities.Door;

import javax.websocket.Session;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LightReloadService {
    private static LightReloadService instance;
    private static Map<String, Session> sMap = new HashMap<String, Session>();
    private LightReloadService() {}

    public static void add(Session s) {
        sMap.put(s.getId(), s);
    }

    public static void initialize() {
        if (instance == null) {
            instance = new LightReloadService();
        }
    }

    public static void update(Door door) {
        try {
            for (String key : sMap.keySet()) {
                Session s = sMap.get(key);
                if (s.isOpen()) {
                    String light = door.isLight()?"The light is ON":"The light is OFF";
                    s.getBasicRemote().sendText(light);
                }
            }
            for (String key : sMap.keySet()) {
                Session s = sMap.get(key);
                if (!s.isOpen()) sMap.remove(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

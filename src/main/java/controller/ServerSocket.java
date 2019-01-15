package controller;

import dao.mysqlIMPL.DoorDaoImpl;
import entities.Door;
import services.impl.DoorServiceImpl;
import util.DataSource;

import javax.servlet.ServletException;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@ServerEndpoint("/check")
public class ServerSocket {
    private DoorServiceImpl doorService = new DoorServiceImpl();

    @OnOpen
    public void onOpen(Session session) {
        //System.out.println("onOpen::" + session.getId());

        LightReloadService.initialize();
        LightReloadService.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        //System.out.println("onClose::" +  session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        //System.out.println("onMessage::From=" + session.getId() + " Message=" + message);
        try {
            Connection connection = DataSource.getConnection();
            DoorDaoImpl doorDao = new DoorDaoImpl();
            doorDao.setConnection(connection);
            doorService.setDoorDao(doorDao);
            Door door = doorService.findByName(message);
            door.setLight(!door.isLight());
            doorService.save(door);
            DataSource.returnConnection(connection);
            LightReloadService.update(door);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Throwable t) {
        //System.out.println("onError::" + t.getMessage());
    }
}

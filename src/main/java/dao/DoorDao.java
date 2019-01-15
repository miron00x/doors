package dao;

import entities.Door;

import java.sql.SQLException;
import java.util.List;

public interface DoorDao extends Dao<Door, Long> {
    List<Door> readAll() throws SQLException;
    Door readByName(String name) throws SQLException;
}

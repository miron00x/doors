package services;

import entities.Door;

import java.sql.SQLException;
import java.util.List;

public interface DoorService {
    Door findById(Long id) throws SQLException;

    Door findByName(String name) throws SQLException;

    List<Door> findAll() throws SQLException;

    void save(Door door) throws SQLException;

    void delete(Long id) throws SQLException;
}

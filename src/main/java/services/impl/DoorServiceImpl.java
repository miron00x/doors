package services.impl;

import dao.DoorDao;
import entities.Door;
import services.DoorService;

import java.sql.SQLException;
import java.util.List;

public class DoorServiceImpl implements DoorService {
    private DoorDao doorDao;

    public void setDoorDao(DoorDao doorDao) {
        this.doorDao = doorDao;
    }

    @Override
    public Door findById(Long id) throws SQLException {
        try {
            return doorDao.read(id);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Door findByName(String name) throws SQLException {
        try {
            return doorDao.readByName(name);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<Door> findAll() throws SQLException {
        try {
            return doorDao.readAll();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void save(Door door) throws SQLException {
        try {
            if (door.getId() != null) {
                doorDao.update(door);
            } else {
                Long id = doorDao.create(door);
                door.setId(id);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void delete(Long id) throws SQLException {

    }
}

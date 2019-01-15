package testdao;

import dao.mysqlIMPL.DoorDaoImpl;
import entities.Door;
import util.DataSource;

import java.sql.SQLException;
import java.util.List;

public class DoorDaoTest {
    public static void main(String[] args) {
        DoorDaoImpl rootDao = new DoorDaoImpl();
        try {
            rootDao.setConnection(DataSource.getConnection());
            System.out.println("UserDao.readAll():");
            readAllTest(rootDao);
            readByName(rootDao);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void readAllTest(DoorDaoImpl userDao) throws SQLException {
        List<Door> doors = userDao.readAll();
        for(Door door : doors) {
            output(door);
        }
    }

    private static void readByName(DoorDaoImpl userDao) throws SQLException {
        Door door = userDao.readByName("Red");
        output(door);
    }

    private static void output(Door door) {
        System.out.printf("\tid=%d, name=%s, light=%s\n", door.getId(), door.getName(), door.isLight());
    }
}

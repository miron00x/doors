package dao.mysqlIMPL;

import dao.DoorDao;
import entities.Door;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoorDaoImpl implements DoorDao {
    static final Logger log = LogManager.getRootLogger();
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public List<Door> readAll() throws SQLException {
        String sql = "SELECT `id`, `name`, `light` FROM `doors`";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            List<Door> doors = new ArrayList<Door>();
            while (resultSet.next()) {
                Door door = new Door();
                door.setId(resultSet.getLong("id"));
                door.setName(resultSet.getString("name"));
                door.setLight(resultSet.getBoolean("light"));
                doors.add(door);
            }
            return doors;
        } catch(SQLException e) {
            log.debug("Error. Failed to read from DB. " + e.getMessage());
            throw new SQLException();
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }

    public Door readByName(String name) throws SQLException {
        String sql = "SELECT `id`, `name`, `light` FROM `doors` WHERE `name` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            Door door = null;
            if(resultSet.next()) {
                door = new Door();
                door.setId(resultSet.getLong("id"));
                door.setName(name);
                door.setLight(resultSet.getBoolean("light"));
            }
            return door;
        } catch(SQLException e) {
            log.debug("Error. Failed to read from DB. " + e.getMessage());
            throw new SQLException();
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }

    public Door read(Long id) throws SQLException {
        String sql = "SELECT `id`, `name`, `light` FROM `doors` WHERE `id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            Door door = null;
            if(resultSet.next()) {
                door = new Door();
                door.setId(id);
                door.setName(resultSet.getString("name"));
                door.setLight(resultSet.getBoolean("light"));
            }
            return door;
        } catch(SQLException e) {
            log.debug("Error. Failed to read from DB. " + e.getMessage());
            throw new SQLException();
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }

    public Long create(Door door) throws SQLException {
        String sql = "INSERT INTO `doors` (`name`, `light`) VALUES (?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, door.getName());
            statement.setBoolean(2, door.isLight());
            statement.executeUpdate();
            Long id = null;
            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                id = resultSet.getLong(1);
            }
            return id;
        } catch(SQLException e) {
            throw new SQLException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }

    public void update(Door door) throws SQLException {
        String sql = "UPDATE `doors` SET `name` = ?, `light` = ? WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setString(1, door.getName());
            statement.setBoolean(2, door.isLight());
            statement.setLong(3, door.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new SQLException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
        }
    }

    public void delete(Long id) throws SQLException {

    }
}

package dao;

import java.sql.SQLException;

public interface Dao<Entity, Key> {
    Entity read(Key id) throws SQLException;

    Long create(Entity entity) throws SQLException;

    void update(Entity entity) throws SQLException;

    void delete(Key id) throws SQLException;
}

package util;

import java.sql.Connection;

public interface ConnectionPool extends AutoCloseable {
    Connection getConnectionFromPool();
    boolean returnConnectionToPool(Connection connection);
}

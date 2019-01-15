package util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class JdbcConnectionPool implements ConnectionPool {
    static final Logger log = LogManager.getRootLogger();
    private Set<Connection> availableConnections = new HashSet<Connection>();

    public JdbcConnectionPool(){
        initializeConnectionPool();

        /*String sql = null;
        try {
            sql = readFile("src/main/db/init.sql");
            System.out.println(sql);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Statement statement = null;
        Connection connection = createNewConnectionForPool();
        try {
            statement = connection.createStatement();
            statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ connection.close(); } catch(Exception e) {}
        }*/

    }

   /* private String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }*/


    private void initializeConnectionPool()
    {
        while(!checkIfConnectionPoolIsFull()) {
            availableConnections.add(createNewConnectionForPool());
        }
    }

    private synchronized boolean checkIfConnectionPoolIsFull()
    {
        final int MAX_POOL_SIZE = Configuration.getInstance().DB_MAX_CONNECTIONS;
        if(availableConnections.size() < MAX_POOL_SIZE) {
            return false;
        }
        return true;
    }

    private Connection createNewConnectionForPool()
    {
        Configuration config = Configuration.getInstance();
        try {
            Class.forName(config.DB_DRIVER);
            Connection connection = (Connection) DriverManager.getConnection(config.DB_URL, config.DB_USER_NAME, config.DB_PASSWORD);
            return connection;
        } catch (ClassNotFoundException e) {
            log.debug("Error. Failed to create connection. " + e.getMessage());
            //e.printStackTrace();
        } catch (SQLException e) {
            log.debug("Error. Failed to create connection. " + e.getMessage());
            //e.printStackTrace();
        }
        log.debug("Error. Failed to create connection");
        throw new NullPointerException();
    }

    public Connection getConnectionFromPool() {
        Connection connection = null;
        if(!availableConnections.isEmpty()) {
            connection = (Connection) availableConnections.iterator().next();
            availableConnections.remove(connection);
            log.debug("Took connection from connection pool. Free connection: " + availableConnections.size());
            return connection;
        }
        log.debug("Error. Failed to get a connection");
        throw new NullPointerException();
    }

    public boolean returnConnectionToPool(Connection connection) {
        log.debug("Return connection to connection pool. Free connection:" + (availableConnections.size() + 1));
        return availableConnections.add(connection);
    }

    public void close() throws Exception {
        for(Connection connection : availableConnections){
            connection.close();
            connection = null;
            availableConnections.remove(connection);
        }
    }
}

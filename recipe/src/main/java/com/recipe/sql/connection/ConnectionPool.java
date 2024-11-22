package com.recipe.sql.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private final List<Connection> availableConnections = new ArrayList<>();
    private final List<Connection> usedConnections = new ArrayList<>();
    private static final int INITIAL_POOL_SIZE = 5;

    public ConnectionPool() {
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            availableConnections.add(createConnection());
        }
    }

    private Connection createConnection() {
        try {
            Class.forName("org.postgresql.Driver"); // Force the driver to load
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL Driver not found", e);
        }

        try {
            return DriverManager.getConnection(DatabaseConfig.get("datasource.url"),
                    DatabaseConfig.get("datasource.username"),
                    DatabaseConfig.get("datasource.password"));
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось создать соединение", e);
        }
    }

    public synchronized Connection getConnection() {
        while (availableConnections.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Connection connection = availableConnections.remove(availableConnections.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public synchronized void releaseConnection(Connection connection) {
        usedConnections.remove(connection);
        availableConnections.add(connection);
        notifyAll();
    }

    public void shutdown() {
        usedConnections.forEach(this::closeConnection);
        availableConnections.forEach(this::closeConnection);
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getPoolSize() {
        return availableConnections.size() + usedConnections.size();
    }

}

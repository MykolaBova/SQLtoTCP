package org.rublin.repository;

import org.slf4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Repository provides JDBC connections to SQL database
 *
 * @author Ruslan Sheremet
 * @see Connection
 * @see Statement
 * @see ResultSet
 * @since 1.0
 */
public class JdbcRepository implements Repository {

    private static final Logger LOG = getLogger(JdbcRepository.class);

    private static final ResourceBundle MYSQL = ResourceBundle.getBundle("db.mysql");
    private static final String DATABASE_NAME = MYSQL.getString("database.dbName");
    private static final String LOGIN = MYSQL.getString("database.username");
    private static final String PASSWORD = MYSQL.getString("database.password");
    private static final JdbcRepository repository = new JdbcRepository();
    private Connection connection;
    private Statement statement;
    private JdbcRepository() {
        try {
            Class.forName(MYSQL.getString("database.driverClassName"));
            connection = DriverManager.getConnection(String.format("%s?user=%s&password=%s", MYSQL.getString("database.url"), LOGIN, PASSWORD));
            connection.setAutoCommit(false);
            statement = connection.createStatement();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    public static JdbcRepository getRepository() {
        return repository;
    }

    @Override
    public List<String> getAllRecords(String table) {
        return null;
    }

    @Override
    public List<String> getLastRecords(int id, String table) {
        return null;
    }

    /**
     * Close statement and connection
     */
    @Override
    public void closeRepository() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e ) {
            LOG.error(e.getMessage());
        }
    }
}

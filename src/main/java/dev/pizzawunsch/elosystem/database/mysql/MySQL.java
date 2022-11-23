package dev.pizzawunsch.elosystem.database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.Getter;

/**
 * This class allows one to create a connection to an SQL database and send
 * requests to it.
 *
 * @author Vuquiz
 * @version 1.0
 * @since 21.05.2021
 */
@Getter
public class MySQL {

    // instance variables
    private Connection connection;
    private final String host;
    private final String port;
    private final String database;
    private final String username;
    private final String password;

    /**
     * Creates a new mysql object (does not yet establish a connection)
     *
     * @param host     the hostname of the server
     * @param port     the port of the sql server
     * @param database the name of the database
     * @param username the username
     * @param password the corresponding password
     */
    public MySQL(String host, String port, String database, String username, String password) {
        // initialising instance variables
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.connection = null;
    }

    /**
     * Establishes the connection to the sql database
     */
    public void connect() {
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password);
            System.out.println("[MYSQL] Database connection established");
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("[MYSQL] Could not connect to database service!");
        }
    }

    /**
     * Closes an existing sql connection
     */
    public void disconnect() {
        try {
            this.connection.close();
            this.connection = null;
            System.out.println("[MYSQL] Disconnected from database service");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Executes an update
     *
     * @param query the statement for the update
     */
    public void update(String query) {
        if (this.connection != null) {
            try {
                connection.createStatement().executeUpdate(query);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Prepares an sql statement
     *
     * @param query the contents of the statement
     * @return the prepared statement
     */
    public PreparedStatement prepare(String query) {
        if (this.connection != null) {
            try {
                return this.connection.prepareStatement(query);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return null; // if something goes wrong
    }

    /**
     * @param query the statement for the result
     * @return the corresponding result
     */
    public ResultSet getResult(String query) {
        if (this.connection != null) {
            try {
                return connection.createStatement().executeQuery(query);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
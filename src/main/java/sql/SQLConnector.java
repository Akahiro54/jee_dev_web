package sql;

import beans.Activite;
import beans.Lieu;
import beans.Utilisateur;
import tools.PasswordHasher;

import java.io.InputStream;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SQLConnector {

    // Settings to create database connection
    private static final String DATABASE_URL = "127.0.0.1";
    private static final String DATABASE_NAME = "jee_database";
    private static final String DATABASE_PORT = "3306";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";


    // Singleton managing database connection
    private static volatile SQLConnector connector;

    // Class variable representing the connection
    private Connection connection;

    public static Connection getConnection(){
        if (connector == null) {
            synchronized (SQLConnector.class) {
                if (connector == null){
                    connector = new SQLConnector();
                }
            }
        }
        return connector.connection;
    }


    // Constructor
    private SQLConnector(){
        // Check if the database driver is correctly implemented on server side
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Cannot load MySQL Driver.");
            e.printStackTrace();
            System.exit(-1);
        }

        // Tries to connect to the database then stores the connection object
        try {
            String DBUrl = "jdbc:mysql://" + SQLConnector.DATABASE_URL + ":" + SQLConnector.DATABASE_PORT  + "/" + SQLConnector.DATABASE_NAME;
            this.connection = DriverManager.getConnection(DBUrl, SQLConnector.DATABASE_USERNAME,SQLConnector.DATABASE_PASSWORD);
            System.out.println("Successfully connected to the database.");
        } catch(SQLException sqe) {
            System.err.println("Cannot create connection to the database.");
            sqe.printStackTrace();
            System.exit(-1);
        }
    }

}

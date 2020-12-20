package sql;

import beans.Utilisateur;
import exceptions.DatabaseConnectionException;

import java.sql.*;
import java.util.Enumeration;

public class SQLConnector {

    // Singleton managing database connection
    private static volatile SQLConnector connector;

    public static SQLConnector getConnection(){
        if (connector == null) {
            synchronized (SQLConnector.class) {
                if (connector == null){
                    connector = new SQLConnector();
                }
            }
        }
        return connector;
    }



    // Attributes
    private Connection connection;

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
            String DBUrl = "jdbc:mysql://127.0.0.1/jee_database";
            this.connection = DriverManager.getConnection(DBUrl, "root","root");
            System.out.println("Successfully connected to the database.");
        } catch(SQLException sqe) {
            System.err.println("Cannot create connection to the database.");
            sqe.printStackTrace();
            System.exit(-1);
        }
    }


    public boolean createUser(Utilisateur user) {
        boolean created = false;
        // TODO : Password hash
        // TODO : method to check if user exists
        try {
            PreparedStatement preparedStatement = connection.prepareStatement( "INSERT INTO user (email, password) VALUES (?, ?);");
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPass());
            preparedStatement.execute();
            created = true;
        } catch (Exception e) {
            System.err.println("Cannot create the user.");
            System.err.println("User Object : " + user.toString());
            e.printStackTrace();
            created = false;
        }
        return created;
    }


}

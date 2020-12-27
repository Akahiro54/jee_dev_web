package sql;

import beans.Utilisateur;
import com.mysql.cj.jdbc.Blob;
import exceptions.DatabaseConnectionException;
import tools.PasswordHasher;

import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Enumeration;

public class SQLConnector {

    private static final String DATABASE_URL = "127.0.0.1";
    private static final String DATABASE_NAME = "jee_database";
    private static final String DATABASE_PORT = "3306";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";


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
            String DBUrl = "jdbc:mysql://" + SQLConnector.DATABASE_URL + ":" + SQLConnector.DATABASE_PORT  + "/" + SQLConnector.DATABASE_NAME;
            this.connection = DriverManager.getConnection(DBUrl, SQLConnector.DATABASE_USERNAME,SQLConnector.DATABASE_PASSWORD);
            System.out.println("Successfully connected to the database.");
        } catch(SQLException sqe) {
            System.err.println("Cannot create connection to the database.");
            sqe.printStackTrace();
            System.exit(-1);
        }
    }

    public boolean login(Utilisateur user) {
        boolean logged = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM utilisateur WHERE email = ?;");
            preparedStatement.setString(1, user.getEmail());
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) {
                byte[] userPassword = result.getBytes(3);
                byte[] givenPassword = PasswordHasher.getPasswordHash(user.getPass());
                System.out.println(userPassword);
                System.out.println(givenPassword);
                if(Arrays.equals(userPassword, givenPassword)) {
                    logged = true;
                }
            }
        } catch(Exception e) {
            System.err.println("Cannot log the user.");
            System.err.println("User Object : " + user.toString());
            e.printStackTrace();
            logged = false;
        }
        return logged;
    }

    public boolean createUser(Utilisateur user) {
        boolean created = false;
        // TODO : Password hash
        // TODO : method to check if user exists
        try {
            byte[] passwordHash = PasswordHasher.getPasswordHash(user.getPass());
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO utilisateur (email, mot_de_passe, nom , prenom, date_naissance) VALUES (?, ?, ? ,?, ?);");
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setBytes(2, passwordHash);
            preparedStatement.setString(3, user.getNom());
            preparedStatement.setString(4, user.getPrenom());
            preparedStatement.setDate(5, new Date(System.currentTimeMillis()));

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

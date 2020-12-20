package sql;

import beans.Utilisateur;
import com.mysql.cj.jdbc.Blob;
import exceptions.DatabaseConnectionException;
import tools.PasswordHasher;

import java.sql.*;
import java.util.Arrays;
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

    public boolean login(Utilisateur user) {
        boolean logged = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM user WHERE email = ?;");
            preparedStatement.setString(1, user.getEmail());
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) {
                byte[] userPassword = result.getBytes(2);
                byte[] givenPassword = PasswordHasher.getPasswordHash(user.getPass());
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
                    "INSERT INTO user (email, mot_de_passe,nom , prenom) VALUES (?, ?, ? ,?);");
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setBytes(2, passwordHash);
            preparedStatement.setString(3, user.getNom());
            preparedStatement.setString(4, user.getPrenom());

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

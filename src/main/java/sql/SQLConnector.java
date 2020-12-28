package sql;

import beans.Utilisateur;
import tools.PasswordHasher;

import java.sql.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

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

    // TODO : method to check if user exists
    public boolean createUser(Utilisateur user) {
        boolean created = false;
        try {
            byte[] passwordHash = PasswordHasher.getPasswordHash(user.getPass());
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO utilisateur (email, mot_de_passe, nom , prenom, date_naissance) VALUES (?, ?, ? ,?, ?);");
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setBytes(2, passwordHash);
            preparedStatement.setString(3, user.getNom());
            preparedStatement.setString(4, user.getPrenom());
            Instant instant = user.getDate().toInstant();
            ZonedDateTime frDateTime = ZonedDateTime.ofInstant( instant, ZoneId.of("Europe/Paris"));
            preparedStatement.setObject(5, frDateTime.toLocalDate());

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

    public Utilisateur RecupInfoUser(String emailUtilisateur) {
        ResultSet resultat = null;
        Utilisateur utilisateur = new Utilisateur();
        try {

            PreparedStatement preparedStatement = connection.prepareCall("SELECT * FROM utilisateur WHERE email = ? ");
            preparedStatement.setString(1,emailUtilisateur);
            resultat = preparedStatement.executeQuery();

            while (resultat.next()) {
                String prenomUtilisateur = resultat.getString(5);
                String nomUtilisateur = resultat.getString(4);
                Date dateNaissanceUtilisateur = resultat.getDate(6);
                utilisateur.setDate(dateNaissanceUtilisateur);
                utilisateur.setPrenom(prenomUtilisateur);
                utilisateur.setNom(nomUtilisateur);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return utilisateur;
    }

    public boolean ModifInfoUser(String prenom, String nom, String emailUtilisateur, Date date, String emailActuel) {
        boolean created = false;
        try {

            PreparedStatement preparedStatement = connection.prepareCall("UPDATE utilisateur SET email = ?, nom = ?, prenom = ?, date_naissance = ? WHERE email = ? ");
            preparedStatement.setString(1,emailUtilisateur);
            preparedStatement.setString(2,nom);
            preparedStatement.setString(3,prenom);
            preparedStatement.setDate(4,date);
            preparedStatement.setString(5,emailActuel);
            preparedStatement.executeUpdate();
            created = true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            created = false;
        }
        return created;
    }


}

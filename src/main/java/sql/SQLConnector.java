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
                byte[] userPassword = result.getBytes(4);
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
        try {
            byte[] passwordHash = PasswordHasher.getPasswordHash(user.getPass());
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO utilisateur (email, pseudo, mot_de_passe, nom , prenom, date_naissance) VALUES (?, ?, ? ,?, ?, ?);");
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPseudo());
            preparedStatement.setBytes(3, passwordHash);
            preparedStatement.setString(4, user.getNom());
            preparedStatement.setString(5, user.getPrenom());
            Instant instant = user.getDate().toInstant();
            ZonedDateTime frDateTime = ZonedDateTime.ofInstant( instant, ZoneId.of("Europe/Paris"));
            preparedStatement.setObject(6, frDateTime.toLocalDate());

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
                utilisateur.setId(resultat.getInt(1));
                utilisateur.setEmail(resultat.getString(2));
                utilisateur.setPseudo(resultat.getString(3));
                utilisateur.setNom(resultat.getString(5));
                utilisateur.setPrenom(resultat.getString(6));
                utilisateur.setDate(resultat.getDate(7));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return utilisateur;
    }

    public boolean ModifInfoUser(String prenom, String nom, String emailUtilisateur, Date date, String emailActuel, InputStream photo) {
        boolean updated = false;
        try {

            PreparedStatement preparedStatement = connection.prepareCall("UPDATE utilisateur SET email = ?, nom = ?, prenom = ?, date_naissance = ?, image = ? WHERE email = ? ");
            preparedStatement.setString(1,emailUtilisateur);
            preparedStatement.setString(2,nom);
            preparedStatement.setString(3,prenom);
            preparedStatement.setDate(4,date);
            preparedStatement.setBlob(5,photo);
            preparedStatement.setString(6,emailActuel);
            preparedStatement.executeUpdate();
            updated = true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            updated = false;
        }
        return updated;
    }


    public ArrayList<Lieu> getAvailablePlaces() {
        ArrayList<Lieu> lieux = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareCall("SELECT * FROM lieu");
            ResultSet resultat = preparedStatement.executeQuery();
            while (resultat.next()) {
                Lieu l = new Lieu();
                l.setId(resultat.getInt(1));
                l.setNom(resultat.getString(2));
                l.setDescription(resultat.getString(3));
                l.setAdresse(resultat.getString(4));
                l.setLatitude(resultat.getDouble(5));
                l.setLongitude(resultat.getDouble(6));
                l.setImage(resultat.getString(7));
                lieux.add(l);
            }
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return lieux;
    }



    public boolean createActivity(Activite activite) {
        boolean created = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO activite (utilisateur, nom, debut, fin, lieu) VALUES (?, ?, ? ,?, ?);");
            preparedStatement.setInt(1, activite.getIdUtilisateur());
            preparedStatement.setString(2, activite.getNom());
            LocalDateTime debut = LocalDateTime.of(activite.getDateDebut(), activite.getHeureDebut());
            LocalDateTime fin = LocalDateTime.of(activite.getDateFin(), activite.getHeureFin());
            preparedStatement.setObject(3, debut);
            preparedStatement.setObject(4, fin);
            preparedStatement.setInt(5, activite.getIdLieu());
            preparedStatement.execute();
            created = true;
        } catch (Exception e) {
            System.err.println("Cannot create the activity.");
            System.err.println("Activity Object : " + activite.toString());
            e.printStackTrace();
            created = false;
        }
        return created;
    }

    public HashMap<Activite, String> getMyActivities(int idUser) {
        HashMap<Activite, String>  activities = new HashMap<>();
        try {
            PreparedStatement preparedStatement = connection.prepareCall("SELECT a.* , l.nom FROM ACTIVITE a INNER JOIN LIEU l ON a.lieu = l.id WHERE a.utilisateur = ?");
            preparedStatement.setInt(1, idUser);
            ResultSet resultat = preparedStatement.executeQuery();
            while (resultat.next()) {
                Activite a = new Activite();
                a.setId(resultat.getInt(1));
                a.setIdUtilisateur(resultat.getInt(2));
                a.setNom(resultat.getString(3));
                LocalDateTime debut = ((Timestamp)resultat.getObject(4)).toLocalDateTime();
                LocalDateTime fin = ((Timestamp)resultat.getObject(5)).toLocalDateTime();
                a.setDateDebut(debut.toLocalDate());
                a.setHeureDebut(debut.toLocalTime());
                a.setDateFin(fin.toLocalDate());
                a.setHeureFin(fin.toLocalTime());
                String lieu = resultat.getString(7);
                activities.put(a,lieu);
            }
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return activities;
    }

    public boolean createPlace(Lieu lieu) {
        boolean created = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO lieu (nom, description, adresse, latitude, longitude) VALUES (?, ?, ? ,?, ?);");
            preparedStatement.setString(1, lieu.getNom());
            preparedStatement.setString(2, lieu.getDescription());
            preparedStatement.setString(3, lieu.getAdresse());
            preparedStatement.setDouble(4, lieu.getLatitude());
            preparedStatement.setDouble(5, lieu.getLatitude());
            preparedStatement.execute();
            created = true;
        } catch (Exception e) {
            System.err.println("Cannot create the place.");
            System.err.println("Place Object : " + lieu.toString());
            e.printStackTrace();
            created = false;
        }
        return created;
    }



    /**
     *
     *
     * EXISTS METHODS
     *
     *
     */

    public boolean placeExistsById(int idPlace) {
        boolean exists = false;
        try {
            PreparedStatement preparedStatement = connection.prepareCall("SELECT count(*) FROM lieu WHERE id = ?");
            preparedStatement.setInt(1,idPlace);
            ResultSet resultat = preparedStatement.executeQuery();
            while(resultat.next()) {
                if(resultat.getInt(1) >= 1) exists = true;
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return exists;
    }

    public boolean placeExistsByName(String namePlace) {
        boolean exists = false;
        try {
            PreparedStatement preparedStatement = connection.prepareCall("SELECT count(*) FROM lieu WHERE nom = ?");
            preparedStatement.setString(1,namePlace);
            ResultSet resultat = preparedStatement.executeQuery();
            while(resultat.next()) {
                if(resultat.getInt(1) >= 1) exists = true;
            }
        } catch (SQLException sqe) {
            exists = true;
            sqe.printStackTrace();
        }
        return exists;
    }

    public boolean userExistsMail(String email) {
        boolean exists = false;
        try {
            PreparedStatement preparedStatement = connection.prepareCall("SELECT count(*) FROM utilisateur WHERE email = ?");
            preparedStatement.setString(1,email);
            ResultSet resultat = preparedStatement.executeQuery();
            while(resultat.next()) {
                if(resultat.getInt(1) >= 1) exists = true;
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return exists;
    }

    public boolean userExistsNickname(String nickname) {
        boolean exists = false;
        try {
            PreparedStatement preparedStatement = connection.prepareCall("SELECT count(*) FROM utilisateur WHERE pseudo = ?");
            preparedStatement.setString(1,nickname);
            ResultSet resultat = preparedStatement.executeQuery();
            while(resultat.next()) {
                if(resultat.getInt(1) >= 1) exists = true;
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return exists;
    }

    public boolean activityExists(int idActivity) {
        boolean exists = false;
        try {
            PreparedStatement preparedStatement = connection.prepareCall("SELECT count(*) FROM activite WHERE id = ?");
            preparedStatement.setInt(1,idActivity);
            ResultSet resultat = preparedStatement.executeQuery();
            while(resultat.next()) {
                if(resultat.getInt(1) >= 1) exists = true;
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return exists;
    }
}

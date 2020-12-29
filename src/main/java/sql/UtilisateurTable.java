package sql;

import beans.Utilisateur;
import tools.PasswordHasher;

import java.io.InputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

public class UtilisateurTable {
    
    public static boolean login(Utilisateur user) {
        boolean logged = false;
        try {
            PreparedStatement preparedStatement = SQLConnector.getConnection().prepareStatement("SELECT * FROM utilisateur WHERE email = ?;");
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


    public static boolean createUser(Utilisateur user) {
        boolean created = false;
        try {
            byte[] passwordHash = PasswordHasher.getPasswordHash(user.getPass());
            PreparedStatement preparedStatement = SQLConnector.getConnection().prepareStatement("INSERT INTO utilisateur (email, pseudo, mot_de_passe, nom , prenom, date_naissance) VALUES (?, ?, ? ,?, ?, ?);");
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

    public static Utilisateur RecupInfoUser(String emailUtilisateur) {
        ResultSet resultat = null;
        Utilisateur utilisateur = new Utilisateur();
        try {

            PreparedStatement preparedStatement = SQLConnector.getConnection().prepareCall("SELECT * FROM utilisateur WHERE email = ? ");
            preparedStatement.setString(1,emailUtilisateur);
            resultat = preparedStatement.executeQuery();

            while (resultat.next()) {
                utilisateur.setId(resultat.getInt(1));
                utilisateur.setEmail(resultat.getString(2));
                utilisateur.setPseudo(resultat.getString(3));
                utilisateur.setNom(resultat.getString(5));
                utilisateur.setPrenom(resultat.getString(6));
                utilisateur.setDate(resultat.getDate(7));
                utilisateur.setImage(resultat.getBytes(11));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return utilisateur;
    }

    public static boolean ModifInfoUser(String prenom, String nom, String emailUtilisateur, Date date, String emailActuel, InputStream photo,String nomImage) {
        boolean created = false;
        System.out.println("Ca passe ici");
        try {

            PreparedStatement preparedStatement = SQLConnector.getConnection().prepareCall("UPDATE utilisateur SET email = ?, nom = ?, prenom = ?, date_naissance = ?, image = ?, nomimage = ? WHERE email = ? ");
            preparedStatement.setString(1,emailUtilisateur);
            preparedStatement.setString(2,nom);
            preparedStatement.setString(3,prenom);
            preparedStatement.setDate(4,date);
            preparedStatement.setBlob(5,photo);
            preparedStatement.setString(6,nomImage);
            preparedStatement.setString(7,emailActuel);
            preparedStatement.executeUpdate();
            created = true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            created = false;
        }
        return created;
    }

    public static boolean ModifInfoUser2(String prenom, String nom, String emailUtilisateur, Date date, String emailActuel) {
        boolean created = false;
        try {

            PreparedStatement preparedStatement = SQLConnector.getConnection().prepareCall("UPDATE utilisateur SET email = ?, nom = ?, prenom = ?, date_naissance = ? WHERE email = ? ");
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

    public static boolean userExistsMail(String email) {
        boolean exists = false;
        try {
            PreparedStatement preparedStatement = SQLConnector.getConnection().prepareCall("SELECT count(*) FROM utilisateur WHERE email = ?");
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

    public static boolean userExistsNickname(String nickname) {
        boolean exists = false;
        try {
            PreparedStatement preparedStatement = SQLConnector.getConnection().prepareCall("SELECT count(*) FROM utilisateur WHERE pseudo = ?");
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
}

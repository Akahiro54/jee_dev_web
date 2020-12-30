package dao;

import beans.Activite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

public class ActiviteTable {

    public static boolean createActivity(Activite activite) {
        boolean created = false;
        try {
            PreparedStatement preparedStatement = SQLConnector.getConnection().prepareStatement("INSERT INTO activite (utilisateur, nom, debut, fin, lieu) VALUES (?, ?, ? ,?, ?);");
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

    // TODO : if needed it is possible to replace the string with the bean Lieu in the hashmap
    public static HashMap<Activite, String> getMyActivities(int idUser) {
        HashMap<Activite, String>  activities = new HashMap<>();
        try {
            PreparedStatement preparedStatement = SQLConnector.getConnection().prepareCall("SELECT a.* , l.nom FROM ACTIVITE a INNER JOIN LIEU l ON a.lieu = l.id WHERE a.utilisateur = ?");
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


    public static boolean activityExists(int idActivity) {
        boolean exists = false;
        try {
            PreparedStatement preparedStatement = SQLConnector.getConnection().prepareCall("SELECT count(*) FROM activite WHERE id = ?");
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

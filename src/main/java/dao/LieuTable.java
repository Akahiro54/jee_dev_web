package dao;

import beans.Lieu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LieuTable {
    public static ArrayList<Lieu> getAvailablePlaces() {
        ArrayList<Lieu> lieux = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = SQLConnector.getConnection().prepareCall("SELECT * FROM lieu");
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

    public static boolean createPlace(Lieu lieu) {
        boolean created = false;
        try {
            PreparedStatement preparedStatement = SQLConnector.getConnection().prepareStatement("INSERT INTO lieu (nom, description, adresse, latitude, longitude) VALUES (?, ?, ? ,?, ?);");
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

    public static boolean placeExistsById(int idPlace) {
        boolean exists = false;
        try {
            PreparedStatement preparedStatement = SQLConnector.getConnection().prepareCall("SELECT count(*) FROM lieu WHERE id = ?");
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

    public static boolean placeExistsByName(String namePlace) {
        boolean exists = false;
        try {
            PreparedStatement preparedStatement = SQLConnector.getConnection().prepareCall("SELECT count(*) FROM lieu WHERE nom = ?");
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

}

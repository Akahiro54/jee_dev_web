package dao;

import beans.Activite;
import beans.Lieu;
import exceptions.DAOException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActiviteDAOImpl implements ActiviteDAO{

    private DAOFactory daoFactory;

    public ActiviteDAOImpl(DAOFactory factory) {
        this.daoFactory = factory;
    }

    //TODO
    @Override
    public List<Activite> getAllActivities() {
        return null;
    }

    //TODO
    @Override
    public List<Activite> getUserActivites(int idUtilisateur) {
        return null;
    }

    //TODO
    @Override
    public Map<Activite, Lieu> getAllActivitiesWithPlaces() {
        return null;
    }


    @Override
    public boolean activityExists(int idActivity) {
        boolean exists = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        String request = "SELECT count(*) FROM activite WHERE id = ?";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false,idActivity);
            preparedStatement.setInt(1,idActivity);
            ResultSet resultat = preparedStatement.executeQuery();
            while(resultat.next()) {
                if(resultat.getInt(1) >= 1) exists = true;
            }
        } catch (Exception e) {
            System.err.println("Cannot check if activity exists : " + e.getMessage());
            System.err.println("Activity ID : " + idActivity);
            exists = false;
        }
        return exists;
    }


    @Override
    public Map<Activite, Lieu> getUserActivitiesWithPlaces(int idUtilisateur) {
        HashMap<Activite, Lieu> activities = new HashMap<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String request ="SELECT a.* , l.* FROM activite a INNER JOIN lieu l ON a.lieu = l.id WHERE a.utilisateur = ?";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false, idUtilisateur);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                activities.putAll(mapActivityAndPlace(resultSet));
            }
        } catch(Exception e) {
            System.err.println("Cannot create the activities and places list : " + e.getMessage());
            System.err.println("HashMap Object : " + activities.toString());
        } finally {
            SQLTools.close(connection,resultSet,preparedStatement);
        }
        return activities;
    }

    @Override
    public boolean add(Activite activite) {
        boolean created = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedValue = null;
        String request ="INSERT INTO activite (utilisateur, nom, debut, fin, lieu) VALUES (?, ?, ? ,?, ?)";
        try {
            connection = daoFactory.getConnection();
            LocalDateTime debut = LocalDateTime.of(activite.getDateDebut(), activite.getHeureDebut());
            LocalDateTime fin = LocalDateTime.of(activite.getDateFin(), activite.getHeureFin());
            preparedStatement = SQLTools.initPreparedRequest(connection,request,true, activite.getIdUtilisateur(), activite.getNom(), debut, fin, activite.getIdLieu());
            preparedStatement.executeUpdate();
            generatedValue = preparedStatement.getGeneratedKeys();
            if(generatedValue.next()) activite.setId(generatedValue.getInt(1));
            else throw new DAOException("Cannot get new ID");
            created = true;
        } catch (Exception e) {
            System.err.println("Cannot create the activity : " + e.getMessage());
            System.err.println("Activity Object : " + activite.toString());
            created = false;
        } finally {
            SQLTools.close(connection,generatedValue,preparedStatement);
        }
        return created;
    }

    @Override
    public boolean update(Activite activite, Object... data) {
        return false;
    }

    @Override
    public boolean delete(Activite activite) {
        return false;
    }

    private static Activite mapActivity(ResultSet resultSet) throws SQLException {
        Activite activite = new Activite();
        activite.setId(resultSet.getInt(1));
        activite.setNom(resultSet.getString(3));
        LocalDateTime debut = ((Timestamp)resultSet.getObject(4)).toLocalDateTime();
        LocalDateTime fin = ((Timestamp)resultSet.getObject(5)).toLocalDateTime();
        activite.setDateDebut(debut.toLocalDate());
        activite.setHeureDebut(debut.toLocalTime());
        activite.setDateFin(fin.toLocalDate());
        activite.setHeureFin(fin.toLocalTime());
        return activite;
    }

    private static Map<Activite, Lieu> mapActivityAndPlace(ResultSet resultSet) throws SQLException {
        Activite activite = new Activite();
        activite.setId(resultSet.getInt(1));
        activite.setNom(resultSet.getString(3));
        LocalDateTime debut = ((Timestamp)resultSet.getObject(4)).toLocalDateTime();
        LocalDateTime fin = ((Timestamp)resultSet.getObject(5)).toLocalDateTime();
        activite.setDateDebut(debut.toLocalDate());
        activite.setHeureDebut(debut.toLocalTime());
        activite.setDateFin(fin.toLocalDate());
        activite.setHeureFin(fin.toLocalTime());

        Lieu lieu = new Lieu();
        lieu.setId(resultSet.getInt(7));
        lieu.setNom(resultSet.getString(8));
        lieu.setDescription(resultSet.getString(9));
        lieu.setAdresse(resultSet.getString(10));
        lieu.setLatitude(resultSet.getDouble(11));
        lieu.setLongitude(resultSet.getDouble(12));
        // TODO getImage
        return Collections.singletonMap(activite,lieu);
    }
}

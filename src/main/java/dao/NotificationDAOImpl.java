package dao;

import beans.EtatNotification;
import beans.Notification;
import beans.Utilisateur;
import exceptions.DAOException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAOImpl implements NotificationDAO{


    private DAOFactory daoFactory;

    public NotificationDAOImpl(DAOFactory factory) {
        this.daoFactory = factory;
    }

    @Override
    public boolean hasNotifications(int idUser) {
        return false;
    }

    @Override
    public List<Notification> getNotifications(int idUser, EtatNotification etatNotification) {
        ArrayList<Notification> listeNotifications = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        String request = "SELECT * FROM notification WHERE destination = ? AND etat = ?";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false, idUser, etatNotification.getEtatNotification());
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()) {
                Notification notif = map(result);
                listeNotifications.add(notif);
            }
        } catch(Exception e) {
            System.err.println("Cannot get user notifications: " + e.getMessage());
            System.err.println("ID user : " + idUser + ", asked state : " + etatNotification);
            System.err.println("Notifications list : " + listeNotifications);
        }
        return listeNotifications;
    }

    @Override
    public boolean add(int idUserSource, int idUserDestination, String message) {
        boolean created = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedValue = null;
        Notification notification = new Notification();
        String request = "INSERT INTO notification (message, date, etat, source, destination) VALUES (?, ?, ? ,?, ?);";
        try {
            connection = daoFactory.getConnection();
            notification.setDate(LocalDateTime.now());
            notification.setUtilisateurSource(idUserSource);
            notification.setUtilisateurDestination(idUserDestination);
            notification.setMessage(message);
            notification.setEtat(EtatNotification.NON_LUE);
            preparedStatement = SQLTools.initPreparedRequest(connection,request, true, notification.getMessage(), notification.getDate(), notification.getEtat().getEtatNotification(), notification.getUtilisateurSource(), notification.getUtilisateurDestination());
            preparedStatement.executeUpdate();
            generatedValue = preparedStatement.getGeneratedKeys();
            if(generatedValue.next()) notification.setId(generatedValue.getInt(1));
            else throw new DAOException("Cannot get new ID");
            created = true;
        } catch (Exception e) {
            System.err.println("Cannot create the notification.");
            System.err.println("Notification Object : " + notification.toString());
            created = false;
        } finally {
            SQLTools.close(connection,generatedValue,preparedStatement);
        }
        return created;
    }

    @Override
    public boolean changeState(Notification notification, EtatNotification newState) {
        return false;
    }

    @Override
    public Notification get(int idNotification) {
        return null;
    }

    private static Notification map(ResultSet resultSet) throws SQLException {
        Notification notification = new Notification();
        notification.setId(resultSet.getInt(1));
        notification.setMessage(resultSet.getString(2));
        notification.setDate(((Timestamp)resultSet.getObject(3)).toLocalDateTime());
        notification.setEtat(EtatNotification.valueOf(resultSet.getString(4).toUpperCase()));
        notification.setUtilisateurSource(resultSet.getInt(5));
        notification.setUtilisateurDestination(resultSet.getInt(6));
        return notification;
    }
}

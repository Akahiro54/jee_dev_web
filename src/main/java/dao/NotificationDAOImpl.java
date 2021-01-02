package dao;

import beans.EtatNotification;
import beans.Notification;
import beans.TypeNotification;
import exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAOImpl implements NotificationDAO{


    private DAOFactory daoFactory;

    public NotificationDAOImpl(DAOFactory factory) {
        this.daoFactory = factory;
    }

    @Override
    public boolean hasNotifications(int idUser) {
        boolean hasNotifications = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        String request = "SELECT count(*) FROM notification WHERE etat = 'non_lue' AND destination = ?";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false,idUser);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                if(resultSet.getInt(1) >= 1) hasNotifications = true;
            }
        } catch (Exception e) {
            System.err.println("Cannot check if user has notifications : " + e.getMessage());
            System.err.println("ID given : " + idUser);
        } finally {
            SQLTools.close(connection,resultSet,preparedStatement);
        }
        return hasNotifications;
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
    public boolean add(Notification notification) {
        boolean created = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedValue = null;
        String request = "INSERT INTO notification (message, date, etat, source, destination) VALUES (?, ?, ? ,?, ?);";
        try {
            connection = daoFactory.getConnection();
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
    public boolean addMultiple(List<Notification> notifications) {
        boolean success = true;
        for(Notification n : notifications) {
            success = add(n);
        }
        return success;
    }

    @Override
    public boolean changeState(Notification notification, EtatNotification newState) {
        boolean updated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        String request = null;
        try {
            connection = daoFactory.getConnection();
            request = "UPDATE notification SET etat = ?  WHERE id = ?";
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false, newState.getEtatNotification(), notification.getId());
            preparedStatement.executeUpdate();
            updated = true;
        } catch(Exception e) {
            System.err.println("Cannot update the notification : " + e.getMessage());
            System.err.println("Notification object : " + notification.toString());
            System.err.println("New state : " + newState.getEtatNotification());
            updated =false;
        } finally {
            SQLTools.close(connection,resultSet,preparedStatement);
        }
        return updated;
    }

    @Override
    public Notification get(int idNotification) {
        ResultSet resultat = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        String request = "SELECT * FROM notification WHERE id = ? ";
        Notification notification = new Notification();
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false, idNotification);
            resultat = preparedStatement.executeQuery();
            if(resultat.next()) {
                notification = NotificationDAOImpl.map(resultat);
            }
        } catch (Exception e) {
            System.err.println("Cannot get the notification : " + e.getMessage());
            System.err.println("Notification ID given : " + idNotification);
            e.printStackTrace();
        } finally {
            SQLTools.close(connection,resultat,preparedStatement);
        }
        return notification;
    }

    @Override
    public boolean hasAlreadyAFriendRequest(int idSource, int idDestination) {
        boolean hasAlreadyAFriendRequest = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        String request = "SELECT count(*) FROM notification WHERE (etat = 'non_lue' AND source = ? AND destination = ?) OR (etat = 'non_lue' AND source = ? AND destination = ?)";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false, idSource, idDestination, idDestination, idSource);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                if(resultSet.getInt(1) >= 1) hasAlreadyAFriendRequest = true;
            }
        } catch (Exception e) {
            System.err.println("Cannot check if users has already a friend request : " + e.getMessage());
            System.err.println("IDs given : " + idSource + "," + idDestination);
        } finally {
            SQLTools.close(connection,resultSet,preparedStatement);
        }
        return hasAlreadyAFriendRequest;
    }


    private static Notification map(ResultSet resultSet) throws SQLException {
        Notification notification = new Notification();
        notification.setId(resultSet.getInt(1));
        notification.setMessage(resultSet.getString(2));
        notification.setDate(((Timestamp)resultSet.getObject(3)).toLocalDateTime());
        notification.setEtat(EtatNotification.valueOf(resultSet.getString(4).toUpperCase()));
        notification.setType(TypeNotification.valueOf(resultSet.getString(5).toUpperCase()));
        notification.setUtilisateurSource(resultSet.getInt(6));
        notification.setUtilisateurDestination(resultSet.getInt(7));
        return notification;
    }
}

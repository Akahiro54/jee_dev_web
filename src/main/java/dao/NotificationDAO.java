package dao;

import beans.EtatNotification;
import beans.Notification;

import java.util.List;

public interface NotificationDAO {

    boolean hasNotifications(int idUser);

    List<Notification> getNotifications(int idUser, EtatNotification etatNotification);

    boolean add(int idUserSource, int idUserDestination, String message);

    boolean changeState(Notification notification, EtatNotification newState);

    Notification get(int idNotification);
}

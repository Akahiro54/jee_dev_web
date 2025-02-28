package dao;

import beans.EtatNotification;
import beans.Notification;

import java.util.List;

public interface NotificationDAO {

    boolean hasNotifications(int idUser);

    List<Notification> getNotifications(int idUser, EtatNotification etatNotification);

    boolean add(Notification notification);

    boolean addMultiple(List<Notification> notifications);

    boolean changeState(Notification notification, EtatNotification newState);

    Notification get(int idNotification);

    boolean hasAlreadyAFriendRequest(int idSource, int idDestination);
}

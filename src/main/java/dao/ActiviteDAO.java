package dao;

import beans.Activite;
import beans.Lieu;

import java.util.List;
import java.util.Map;

public interface ActiviteDAO {

    boolean activityExists(int idActivity);

    List<Activite> getAllActivities();

    List<Activite> getUserActivites(int idUtilisateur);

    Map<Activite, Lieu> getAllActivitiesWithPlaces();

    Map<Activite, Lieu> getUserActivitiesWithPlaces(int idUtilisateur);

    boolean add(Activite activite);

    boolean update(Activite activite, Object... data);

    boolean delete(int idActivity);

    Activite getActivityById(int idActivite);

    boolean isPlaceInActivity(int idActivity, int idPlace);

    Activite get(int idActivity);

    boolean isUserInActivity(int idActivity, int idUser);


}

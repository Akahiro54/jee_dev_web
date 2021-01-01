package dao;

import beans.Amis;
import beans.Utilisateur;

import java.util.List;

public interface AmisDAO {

    List<Utilisateur> getFriends(int idUtilisateur);

    List<Utilisateur> getNonFriends(int idUtilisateur);

    public List<Utilisateur> searchNonFriends(int idUtilisateur, String nickname);

    boolean areFriends(int friend1, int friend2);

    boolean addFriend(int friend1, int friend2);

    boolean update(Amis amis);
}

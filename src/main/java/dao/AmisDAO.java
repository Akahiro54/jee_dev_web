package dao;

import beans.Amis;
import beans.Utilisateur;

import java.util.List;

public interface AmisDAO {

    List<Utilisateur> getFriends(int idUtilisateur);

    List<Utilisateur> getNonFriends(int idUtilisateur);

    public List<Utilisateur> searchNonFriends(int idUtilisateur, String nickname);

    boolean areFriends(Amis amis);

    boolean askFriend(Amis amis);

    boolean update(Amis amis);
}

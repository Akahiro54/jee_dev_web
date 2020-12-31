package dao;

import beans.Utilisateur;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;


public interface UtilisateurDAO{


    Utilisateur getById(int id);

    Utilisateur getByEmail(String email);

    List<Utilisateur> getAll();

    boolean add(Utilisateur utilisateur);

    boolean update(Utilisateur utilisateur, InputStream finput, Object... data);

    boolean delete(Utilisateur utilisateur);

    boolean canLogin(Utilisateur utilisateur);

    boolean nicknameExists(String nickname);

    boolean emailExists(String email);

    List<Utilisateur> getFriends(int idUtilisateur);

    List<Utilisateur> getNonFriends(int idUtilisateur);

    public List<Utilisateur> searchNonFriends(int idUtilisateur, String nickname);

    boolean areFriends(int friend1, int friend2);

    boolean addFriend(int friend1, int friend2);

}

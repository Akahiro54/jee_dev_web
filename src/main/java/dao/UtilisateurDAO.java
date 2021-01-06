package dao;

import beans.Utilisateur;

import java.io.InputStream;
import java.util.List;


public interface UtilisateurDAO{


    Utilisateur getById(int id);

    Utilisateur getByEmail(String email);

    List<Utilisateur> getOtherUsers(int idUser);

    boolean add(Utilisateur utilisateur);

    boolean update(Utilisateur utilisateur, InputStream finput, Object... data);

    boolean delete(int idUser);

    boolean canLogin(Utilisateur utilisateur);

    boolean nicknameExists(String nickname);

    boolean emailExists(String email);

    boolean idExists(int idUser);

    List<Utilisateur> getAllUserOnSamePlacesAtTheSameTime(Utilisateur utilisateur);

    boolean updateContamine(Utilisateur utilisateur);

}

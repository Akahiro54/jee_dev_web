package dao;

import beans.Amis;
import beans.Utilisateur;

import java.util.List;

public interface AdminDAO {

    List<Utilisateur> getUtilisateur(int id);
    boolean delete(int id);
}

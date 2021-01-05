package dao;

import beans.Utilisateur;

import java.util.List;

public interface AdminDAO {

    List<Utilisateur> getUtilisateur(int id);
}

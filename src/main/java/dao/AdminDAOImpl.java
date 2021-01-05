package dao;

import beans.Utilisateur;
import tools.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminDAOImpl implements AdminDAO{

    private DAOFactory daoFactory;

    public AdminDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public List<Utilisateur> getUtilisateur(int idUtilisateur) {
        ArrayList<Utilisateur> listeUtilisateur = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        String request = "SELECT id,email,pseudo,nom,prenom,date_naissance,role FROM utilisateur";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()) {
                Utilisateur util = new Utilisateur();
                util.setId(result.getInt(1));
                util.setEmail(result.getString(2));
                util.setPseudo(result.getString(3));
                util.setNom(result.getString(4));
                util.setPrenom(result.getString(5));
                util.setDate(result.getDate(6));
                util.setRole(result.getString(7));
                listeUtilisateur.add(util);
            }
        } catch(Exception e) {
            System.out.println(e.toString());
            System.err.println("ID user: " + idUtilisateur);
        }
        return listeUtilisateur;
    }
}

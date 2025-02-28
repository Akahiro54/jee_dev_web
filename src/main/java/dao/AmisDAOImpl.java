package dao;

import beans.Amis;
import beans.Utilisateur;
import tools.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AmisDAOImpl implements AmisDAO{

    private DAOFactory daoFactory;

    public AmisDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }


    @Override
    public List<Utilisateur> getFriends(int idUtilisateur) {
        ArrayList<Utilisateur> listeAmis = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        // Get the current user friends, by checking the TWO columns
        String request = "SELECT u.pseudo, u.image, u.id FROM amis a INNER JOIN utilisateur u ON CASE WHEN a.ami1 = ? THEN a.ami2 ELSE a.ami1 END = u.id WHERE (a.ami1 = ? OR a.ami2 = ?)";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false, idUtilisateur, idUtilisateur, idUtilisateur);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()) {
                Utilisateur util = new Utilisateur();
                util.setPseudo(result.getString(1));
                util.setImage(Util.convertUserImage(result.getBytes(2)));
                util.setId(result.getInt(3));
                listeAmis.add(util);
            }
        } catch(Exception e) {
            System.err.println("Cannot get user friends: " + e.getMessage());
            System.err.println("ID user: " + idUtilisateur);
            System.err.println("Friend list : " + listeAmis);
        }
        return listeAmis;
    }


    @Override
    public List<Utilisateur> getNonFriends(int idUtilisateur) {
        ArrayList<Utilisateur> nonAmis = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        String request = "SELECT u.pseudo, u.image, u.id FROM utilisateur WHERE id != ? AND id NOT IN (SELECT u.id FROM amis a INNER JOIN utilisateur u ON CASE WHEN a.ami1 = ? THEN a.ami2 ELSE a.ami1 END = u.id WHERE a.ami1 = ? OR a.ami2 = ?)";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false, idUtilisateur, idUtilisateur, idUtilisateur, idUtilisateur);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()) {
                Utilisateur util = new Utilisateur();
                util.setPseudo(result.getString(1));
                util.setImage(Util.convertUserImage(result.getBytes(2)));
                util.setId(result.getInt(3));
                nonAmis.add(util);
            }
        } catch(Exception e) {
            System.err.println("Cannot get user friends: " + e.getMessage());
            System.err.println("ID user: " + idUtilisateur);
            System.err.println("Friend list : " + nonAmis);
        }
        return nonAmis;
    }


    public List<Utilisateur> searchNonFriends(int idUtilisateur, String nickname) {
        ArrayList<Utilisateur> nonAmis = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        String request = "SELECT  id, pseudo, image FROM utilisateur " +
                         "WHERE pseudo LIKE ? AND id != ? " +
                         "AND id NOT IN " +
                         "( " +
                             "SELECT u.id FROM amis a INNER JOIN utilisateur u ON CASE WHEN a.ami1 = ? THEN a.ami2 ELSE a.ami1 END = u.id WHERE a.ami1 = ? OR a.ami2 = ? " +
                             "UNION " +
                             "SELECT u.id FROM notification n INNER JOIN utilisateur u ON CASE WHEN n.source = ? THEN n.destination ELSE n.source END = u.id WHERE " +
                             "(n.source = ? OR n.destination = ?) AND n.type='ami' AND n.etat='non_lue'" +
                         ")";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false, nickname, idUtilisateur, idUtilisateur, idUtilisateur, idUtilisateur, idUtilisateur, idUtilisateur, idUtilisateur);
            preparedStatement.setString(1, "%" + nickname + "%");
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()) {
                Utilisateur util = new Utilisateur();
                util.setId(result.getInt(1));
                util.setPseudo(result.getString(2));
                util.setImage(Util.convertUserImage(result.getBytes(3)));
                nonAmis.add(util);
            }
        } catch(Exception e) {
            System.err.println("Cannot get user friends: " + e.getMessage());
            System.err.println("ID user: " + idUtilisateur);
            System.err.println("Nickname: " + nickname);
            System.err.println("Friend list : " + nonAmis);
            e.printStackTrace();
        }
        return nonAmis;
    }


    @Override
    public boolean areFriends(Amis amis) {
        boolean exists = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        String request = "SELECT count(*) FROM amis WHERE (ami1 = ? AND ami2 = ?) OR (ami1 = ? AND ami2 = ?)";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false, amis.getIdAmi1(), amis.getIdAmi2(), amis.getIdAmi2(), amis.getIdAmi1());
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                if(resultSet.getInt(1) >= 1) exists = true;
            }
        } catch (Exception e) {
            System.err.println("Cannot check if people are friends: " + e.getMessage());
            System.err.println("Friend 1 : " + amis.getIdAmi1() + ", friend 2 : " + amis.getIdAmi2());
        } finally {
            SQLTools.close(connection,resultSet,preparedStatement);
        }
        return exists;
    }


    @Override
    public boolean add(Amis amis) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedValue = null;
        boolean created = false;
        String request = "INSERT INTO amis (ami1, ami2) VALUES (?, ?)";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection, request, false, amis.getIdAmi1(),amis.getIdAmi2());
            preparedStatement.executeUpdate();
            created = true;
        } catch (Exception e) {
            System.err.println("Cannot add friends : " + e.getMessage());
            System.err.println("Friends IDS : " + amis.getIdAmi1() + ", " + amis.getIdAmi2());
            created = false;
        } finally {
            SQLTools.close(connection,preparedStatement);
        }
        return created;
    }


    @Override
    public Amis get(int idFriend1, int idFriend2) {
        ResultSet resultat = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        String request = "SELECT * FROM amis WHERE ami1 = ? AND ami2 = ?";
        Amis amis = new Amis();
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false, idFriend1, idFriend2);
            resultat = preparedStatement.executeQuery();
            if(resultat.next()) {
                amis = AmisDAOImpl.map(resultat);
            }
        } catch (Exception e) {
            System.err.println("Cannot get the friends object : " + e.getMessage());
            System.err.println("IDs given : " +  idFriend1 + ", " + idFriend2);
            e.printStackTrace();
        } finally {
            SQLTools.close(connection,resultat,preparedStatement);
        }
        return amis;
    }


    @Override
    public boolean delete(Amis amis) {
        boolean deleted = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        String request = "DELETE FROM amis WHERE (ami1 = ? AND ami2 = ?) OR (ami1 = ? AND ami2 = ?)";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false, amis.getIdAmi1(), amis.getIdAmi2(), amis.getIdAmi2(), amis.getIdAmi1());
            preparedStatement.executeUpdate();
            deleted = true;
        } catch(Exception e) {
            System.err.println("Cannot delete the friends : " + e.getMessage());
            System.err.println("Friends object : " + amis.toString());
            deleted =false;
        } finally {
            SQLTools.close(connection,resultSet,preparedStatement);
        }
        return deleted;
    }


    private static Amis map(ResultSet resultSet) throws SQLException {
        Amis amis = new Amis();
        amis.setIdAmi1(resultSet.getInt(1));
        amis.setIdAmi2(resultSet.getInt(2));
        return amis;
    }

}

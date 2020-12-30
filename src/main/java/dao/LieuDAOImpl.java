package dao;

import beans.Lieu;
import exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LieuDAOImpl implements LieuDAO{

    private DAOFactory daoFactory;

    public LieuDAOImpl(DAOFactory factory) {
        this.daoFactory = factory;
    }

    @Override
    public boolean placeExistsById(int idPlace) {
        boolean exists = false;
        Connection connection = null;
        ResultSet resultat = null;
        PreparedStatement preparedStatement = null;
        String request = "SELECT count(*) FROM lieu WHERE id = ?";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request, false, idPlace);
            resultat = preparedStatement.executeQuery();
            while(resultat.next()) {
                if(resultat.getInt(1) >= 1) exists = true;
            }
        } catch (Exception e) {
            System.err.println("Cannot get the place : " + e);
            System.err.println("Place ID given : " + idPlace);
            exists = false;
        }
        return exists;
    }

    @Override
    public boolean placeExistsByName(String namePlace) {
        boolean exists = false;
        Connection connection = null;
        ResultSet resultat = null;
        PreparedStatement preparedStatement = null;
        String request = "SELECT count(*) FROM lieu WHERE nom = ?";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request, false, namePlace);
            resultat = preparedStatement.executeQuery();
            while(resultat.next()) {
                if(resultat.getInt(1) >= 1) exists = true;
            }
        } catch (Exception e) {
            System.err.println("Cannot get the place : " + e);
            System.err.println("Place name given : " + namePlace);
            exists = false;
        }
        return exists;
    }

    @Override
    public List<Lieu> getAllPlaces() {
        ArrayList<Lieu> lieux = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultat = null;
        String request = "SELECT * FROM lieu";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false);
            resultat = preparedStatement.executeQuery();
            while (resultat.next()) {
                Lieu lieu = map(resultat);
                lieux.add(lieu);
            }
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return lieux;
    }

    @Override
    public boolean add(Lieu place) {
        boolean created = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedValue = null;
        String request = "INSERT INTO lieu (nom, description, adresse, latitude, longitude) VALUES (?, ?, ? ,?, ?);";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request, true, place.getNom(),place.getDescription(),place.getAdresse(),place.getLatitude(),place.getLatitude());
            preparedStatement.executeUpdate();
            generatedValue = preparedStatement.getGeneratedKeys();
            if(generatedValue.next()) place.setId(generatedValue.getInt(1));
            else throw new DAOException("Cannot get new ID");
            created = true;
        } catch (Exception e) {
            System.err.println("Cannot create the place.");
            System.err.println("Place Object : " + place.toString());
            created = false;
        } finally {
            SQLTools.close(connection,generatedValue,preparedStatement);
        }
        return created;
    }

    @Override
    public boolean update(Lieu place, Object... data) {
        return false;
    }

    @Override
    public boolean delete(Lieu place) {
        return false;
    }


    private static Lieu map(ResultSet resultSet) throws SQLException {
        Lieu lieu = new Lieu();
        lieu.setId(resultSet.getInt(1));
        lieu.setNom(resultSet.getString(2));
        lieu.setDescription(resultSet.getString(3));
        lieu.setAdresse(resultSet.getString(4));
        lieu.setLatitude(resultSet.getDouble(5));
        lieu.setLongitude(resultSet.getDouble(6));
        return lieu;
    }
}

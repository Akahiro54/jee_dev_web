package dao;

import beans.Lieu;
import exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
        } finally {
            SQLTools.close(connection, resultat, preparedStatement);
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
        } finally {
            SQLTools.close(connection, resultat, preparedStatement);
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
        } finally {
            SQLTools.close(connection, resultat, preparedStatement);
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
            preparedStatement = SQLTools.initPreparedRequest(connection,request, true, place.getNom(),place.getDescription(),place.getAdresse(),place.getLatitude(),place.getLongitude());
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
        boolean updated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        String request = null;
        try {
            connection = daoFactory.getConnection();
            request = "UPDATE lieu SET nom = ?, description = ?, adresse = ?, latitude = ?, longitude = ? WHERE id = ? ";
            preparedStatement = connection.prepareStatement(request);
            preparedStatement.setObject(1,place.getNom());
            preparedStatement.setObject(2,place.getDescription());
            preparedStatement.setObject(3,place.getAdresse());
            preparedStatement.setObject(4,place.getLatitude());
            preparedStatement.setObject(5,place.getLongitude());
            preparedStatement.setObject(6,data[0]);
            preparedStatement.executeUpdate();

            updated = true;
        } catch(Exception e) {
            System.err.println("Cannot update the place : " + e.getMessage());
            System.err.println("Place object : " + place.toString());
            System.err.println("New data : " + Arrays.toString(data));
            updated = false;
        } finally {
            SQLTools.close(connection,preparedStatement);
        }
        return updated;
    }


    @Override
    public boolean delete(int idLieu) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        String request = "DELETE FROM lieu WHERE id = ?";
        boolean deleted = false;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false,idLieu);
            preparedStatement.executeUpdate();
            deleted = true;
        } catch(Exception e) {
            System.err.println("Cannot delete place with id " + idLieu + " : " + e.getMessage());
        } finally {
            SQLTools.close(connection,preparedStatement);
        }
        return deleted;
    }


    @Override
    public Lieu get(int idPlace) {
        ResultSet resultat = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        String request = "SELECT * FROM lieu WHERE id = ? ";
        Lieu lieu = new Lieu();
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false, idPlace);
            resultat = preparedStatement.executeQuery();
            if(resultat.next()) {
                lieu = LieuDAOImpl.map(resultat);
            }
        } catch (Exception e) {
            System.err.println("Cannot get the place : " + e.getMessage());
            System.err.println("ID given : " + idPlace);
            e.printStackTrace();
        } finally {
            SQLTools.close(connection,resultat,preparedStatement);
        }
        return lieu;
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

package dao;

import java.sql.*;

public class SQLTools {

    public static PreparedStatement initPreparedRequest(Connection connection, String request, boolean returnGeneratedKeys, Object... data ) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement( request, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
        for ( int i = 0; i < data.length; i++ ) {
            preparedStatement.setObject( i + 1, data[i] );
        }
        return preparedStatement;
    }

    public static void close(Connection connection, ResultSet resultSet, Statement statement) {
        close(resultSet);
        close(connection,statement);
    }

    public static void close(Connection connection, Statement statement) {
        if(statement != null) {
            try{
                statement.close();
            }catch (SQLException sqe) {
                System.err.println("Cannot close statement : " + sqe.getMessage());
            }
        }
        close(connection);
    }

    public static void close(Connection connection) {
        if(connection != null)  {
            try {
                connection.close();
            } catch(SQLException sqe) {
                System.err.println("Cannot close connection : " + sqe.getMessage());
            }
        }
    }

    public static void close(ResultSet resultSet) {
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch(SQLException sqe) {
                System.err.println("Cannot close ResultSet : " + sqe.getMessage());
            }
        }
    }

}

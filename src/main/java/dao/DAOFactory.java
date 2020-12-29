package dao;

import exceptions.DAOException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {
    private static final String PROPERTIES_FILE = "/dao.properties";
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_PORT = "port";
    private static final String PROPERTY_DATABASE = "database";
    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_USERNAME = "username";
    private static final String PROPERTY_PASSWORD = "password";

    private static volatile DAOFactory factory;
    private String url, port,database, username, password;

    private DAOFactory(String url, String port, String database, String username, String password) {
        this.url = url;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password=  password;
    }

    public static DAOFactory getInstance() throws DAOException {
        if(factory == null) {
            synchronized (DAOFactory.class) {
                if(factory == null) {
                    Properties props = new Properties();
                    String url, port, database, driver, username, password;
                    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                    InputStream propsFile = classLoader.getResourceAsStream(PROPERTIES_FILE);
                    if(propsFile == null) throw new DAOException("Cannot find properties file at : " + PROPERTIES_FILE + classLoader.toString());
                    try {
                        props.load(propsFile);
                        url = props.getProperty(PROPERTY_URL);
                        port = props.getProperty(PROPERTY_PORT);
                        database = props.getProperty(PROPERTY_DATABASE);
                        driver = props.getProperty(PROPERTY_DRIVER);
                        username = props.getProperty(PROPERTY_USERNAME);
                        password = props.getProperty(PROPERTY_PASSWORD);
                    }catch(Exception e) {
                        throw new DAOException("Cannot load properties file at : " + PROPERTIES_FILE + ". " + e.getMessage());
                    }
                    try {
                        Class.forName(driver);
                    } catch (Exception e) {
                        throw new DAOException("Cannot find driver : " + driver);
                    }

                    factory = new DAOFactory(url, port, database, username, password);
                }
            }
        }
        return factory;
    }

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url+":"+port+"/"+database,username,password);
    }

    public UtilisateurDAO getUtilisateurDAO() {
        return new UtilisateurDAOImpl(this);
    }


}

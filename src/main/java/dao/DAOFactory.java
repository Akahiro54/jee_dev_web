package dao;

import exceptions.DAOException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {
//  FIXME : properties file is never compiled with IntelliJ
//   Already tried : add in artifact, module, classpath, resources in maven,....

//    private static final String PROPERTIES_FILE = "/WEB-INF/dao-info.properties";
    private static final String URL = "jdbc:mysql://localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "jee_database";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

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
//                    Properties props = new Properties();
//                    String url, port, database, driver, username, password;
//                    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//                    InputStream propsFile = classLoader.getResourceAsStream(PROPERTIES_FILE);
//                    if(propsFile == null) throw new DAOException("Cannot find properties file at : " + PROPERTIES_FILE + classLoader.toString());
//                    try {
//                        props.load(propsFile);
//                        url = props.getProperty(PROPERTY_URL);
//                        port = props.getProperty(PROPERTY_PORT);
//                        database = props.getProperty(PROPERTY_DATABASE);
//                        driver = props.getProperty(PROPERTY_DRIVER);
//                        username = props.getProperty(PROPERTY_USERNAME);
//                        password = props.getProperty(PROPERTY_PASSWORD);
//                    }catch(Exception e) {
//                        throw new DAOException("Cannot load properties file at : " + PROPERTIES_FILE + ". " + e.getMessage());
//                    }
                    try {
                        Class.forName(DRIVER);
                    } catch (Exception e) {
                        throw new DAOException("Cannot find driver : " + DRIVER);
                    }

                    factory = new DAOFactory(URL, PORT, DATABASE, USERNAME, PASSWORD);
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

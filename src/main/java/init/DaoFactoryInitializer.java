package init;

import dao.DAOFactory;
import exceptions.DAOException;
import tools.Util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DaoFactoryInitializer implements ServletContextListener {

    private DAOFactory daoFactory;

    @Override
    public void contextInitialized( ServletContextEvent event ) {
        ServletContext servletContext = event.getServletContext();
        try {
            this.daoFactory = DAOFactory.getInstance();
            servletContext.setAttribute( Util.ATT_DAO_FACTORY, this.daoFactory );
        } catch(DAOException dae) {
            System.err.println("Cannot initialize DAO Factory : " + dae.getMessage());
        }

    }

}

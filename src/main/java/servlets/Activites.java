package servlets;

import beans.Activite;
import beans.Lieu;
import beans.Utilisateur;
import dao.ActiviteDAO;
import dao.DAOFactory;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.TreeMap;

public class Activites extends HttpServlet {

    private ActiviteDAO activiteDAO;


    @Override
    public void init() throws ServletException {
        this.activiteDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getActiviteDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);
        if(utilisateur == null)  {
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        }  else  {
            TreeMap<Activite, Lieu> activites = new TreeMap<Activite, Lieu>(activiteDAO.getUserActivitiesWithPlaces(utilisateur.getId()));
            req.setAttribute("activites", activites);
            req.getRequestDispatcher("/user-restricted/activites.jsp").forward(req,resp);
        }
    }


}

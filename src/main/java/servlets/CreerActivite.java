package servlets;

import beans.Activite;
import beans.Lieu;
import beans.Utilisateur;
import dao.*;
import forms.ActiviteForm;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class CreerActivite extends HttpServlet {

    private ActiviteDAO activiteDAO;
    private LieuDAO lieuDAO;


    @Override
    public void init() throws ServletException {
        this.activiteDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getActiviteDAO();
        this.lieuDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getLieuDAO();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Lieu> lieux = new ArrayList<Lieu>(lieuDAO.getAllPlaces());
        req.setAttribute(Util.ATT_FORM_PLACES, lieux);
        req.getRequestDispatcher("/user-restricted/creer_activite.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);
        if(utilisateur == null)  {
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        }  else  {
            ArrayList<Lieu> lieux = new ArrayList<Lieu>(lieuDAO.getAllPlaces());
            req.setAttribute(Util.ATT_FORM_PLACES, lieux);
            ActiviteForm form = new ActiviteForm(activiteDAO, lieuDAO);
            Activite activite = form.ajouterActivite(req, utilisateur.getId());
            req.setAttribute(Util.ATT_FORM, form);
            req.setAttribute(Util.ATT_FORM_ACTIVITY, activite);
            if(form.getErrors().isEmpty()) {
                resp.sendRedirect(req.getContextPath()+"/user-restricted/activites.jsp");
            } else {
                req.getRequestDispatcher("/user-restricted/creer_activite.jsp").forward(req,resp);
            }
        }
    }
}


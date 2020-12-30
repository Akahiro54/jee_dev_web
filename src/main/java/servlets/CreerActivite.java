package servlets;

import beans.Activite;
import beans.Lieu;
import beans.Utilisateur;
import dao.ActiviteDAO;
import dao.DAOFactory;
import dao.UtilisateurDAO;
import forms.ActiviteForm;
import dao.LieuTable;
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


    @Override
    public void init() throws ServletException {
        this.activiteDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getActiviteDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Lieu> lieux = LieuTable.getAvailablePlaces();
        req.setAttribute("lieux", lieux);
        req.getRequestDispatcher("/user-restricted/creer_activite.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);
        if(utilisateur == null)  {
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        }  else  {
            ArrayList<Lieu> lieux = LieuTable.getAvailablePlaces();
            req.setAttribute("lieux", lieux);
            ActiviteForm form = new ActiviteForm(activiteDAO);
            Activite activite = form.ajouterActivite(req, utilisateur.getId());
            req.setAttribute("form", form);
            req.setAttribute("activity", activite);
            if(form.getErrors().isEmpty()) {
                resp.sendRedirect(req.getContextPath()+"/user-restricted/activites.jsp");
            } else {
                req.getRequestDispatcher("/user-restricted/creer_activite.jsp").forward(req,resp);
            }
        }
    }
}


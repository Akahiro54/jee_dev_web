package servlets;

import beans.Activite;
import beans.Utilisateur;
import dao.ActiviteDAO;
import dao.DAOFactory;
import dao.LieuDAO;
import forms.ActiviteForm;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class ModifierActivite extends HttpServlet {

    private ActiviteDAO activiteDAO;
    private LieuDAO lieuDAO;
    private int idActivite;


    @Override
    public void init() throws ServletException {
        this.activiteDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getActiviteDAO();
        this.lieuDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getLieuDAO();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<beans.Lieu> lieux = new ArrayList<beans.Lieu>(lieuDAO.getAllPlaces());
        req.setAttribute(Util.ATT_FORM_PLACES, lieux);
        String id = req.getParameter("a");
        idActivite = Integer.parseInt(id);

        Activite activite = activiteDAO.getActivityById(idActivite);
        if(activite == null) { // if no user, redirect to login page
            resp.sendRedirect(req.getContextPath()+"/admin-restricted/panel_admin");
        } else {
            req.setAttribute(Util.ATT_FORM_ACTIVITY,activite);
            this.getServletContext().getRequestDispatcher( "/admin-restricted/modifier_activite.jsp").forward( req, resp );

        }
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);
        if(utilisateur == null)  {
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        }  else  {
            ArrayList<beans.Lieu> lieux = new ArrayList<>(lieuDAO.getAllPlaces());
            req.setAttribute(Util.ATT_FORM_PLACES, lieux);
            ActiviteForm form = new ActiviteForm(activiteDAO, lieuDAO);
            form.modifierActivite(req, idActivite);
            req.setAttribute(Util.ATT_FORM, form);
            Activite activite = activiteDAO.getActivityById(idActivite);
            req.setAttribute(Util.ATT_FORM_ACTIVITY, activite);
            if(form.getErrors().isEmpty()) {
                resp.sendRedirect(req.getContextPath()+"/admin-restricted/panel_admin");
            } else {
                req.getRequestDispatcher("/admin-restricted/modifier_activite.jsp").forward(req,resp);
            }
        }
    }
}

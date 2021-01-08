package servlets;

import beans.Utilisateur;
import dao.DAOFactory;
import dao.UtilisateurDAO;
import forms.ModifierUtilisateurForm;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
public class ModifierUtilisateur extends HttpServlet {

    private UtilisateurDAO utilisateurDAO;
    private int idUtil;


    @Override
    public void init() throws ServletException {
        this.utilisateurDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getUtilisateurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("u");
        idUtil = Integer.parseInt(id);

        Utilisateur utilisateur = utilisateurDAO.getById(idUtil);

        if(utilisateur == null) { // if no user, redirect to login page
            resp.sendRedirect(req.getContextPath()+"/connexion");
        } else {
            req.setAttribute(Util.ATT_FORM_USER,utilisateur);
            this.getServletContext().getRequestDispatcher( "/admin-restricted/modifier_utilisateur.jsp").forward( req, resp );
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        ModifierUtilisateurForm form = new ModifierUtilisateurForm(utilisateurDAO);
        form.modifierUtilisateur(req,idUtil);
        req.setAttribute(Util.ATT_FORM, form);
        Utilisateur utilisateur = utilisateurDAO.getById(idUtil);
        req.setAttribute(Util.ATT_FORM_USER, utilisateur);


        if(form.getErrors().isEmpty()) {
            resp.sendRedirect(req.getContextPath()+"/admin-restricted/panel_admin"); // Returns to the main page
        } else {
            req.getRequestDispatcher("/admin-restricted/modifier_utilisateur.jsp").forward(req, resp);
        }
    }


}

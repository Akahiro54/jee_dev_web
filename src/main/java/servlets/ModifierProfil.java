package servlets;

import beans.Utilisateur;
import dao.DAOFactory;
import dao.UtilisateurDAO;
import forms.ModifierProfilForm;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@MultipartConfig
public class ModifierProfil extends HttpServlet {

    private UtilisateurDAO utilisateurDAO;


    @Override
    public void init() throws ServletException {
        this.utilisateurDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getUtilisateurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute(Util.ATT_SESSION_USER);

        if(utilisateur == null) { // if no user, redirect to login page
            resp.sendRedirect(req.getContextPath()+"/connexion");
        } else {
            req.setAttribute(Util.ATT_FORM_USER,utilisateur);
            this.getServletContext().getRequestDispatcher( "/user-restricted/modifier_profil.jsp" ).forward( req, resp );
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute(Util.ATT_SESSION_USER);


        ModifierProfilForm form = new ModifierProfilForm(utilisateurDAO);
        form.modifierUtilisateur(req);
        req.setAttribute(Util.ATT_FORM, form);
        req.setAttribute(Util.ATT_FORM_USER, utilisateur);

        if(form.getErrors().isEmpty()) {
            resp.sendRedirect(req.getContextPath()+"/user-restricted/profil"); // Returns to the main page
        } else {
            req.getRequestDispatcher("/user-restricted/modifier_profil.jsp").forward(req, resp);
        }
     }



}

package servlets;

import beans.Utilisateur;
import forms.ModifierProfilForm;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ModifierProfil extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute(Util.ATT_SESSION_USER);
        if(utilisateur == null) { // if no user, redirect to login page
            resp.sendRedirect(req.getContextPath()+"/connexion");
        } else {
            req.setAttribute("utilisateur",utilisateur);
            this.getServletContext().getRequestDispatcher( "/user-restricted/modifier_profil.jsp" ).forward( req, resp );
        }
    }

    //FIXME : check autorizations before posting
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("sessionUtilisateur");

        ModifierProfilForm form = new ModifierProfilForm();
        form.modifierUtilisateur(req);
        req.setAttribute("form", form);
        req.setAttribute("utilisateur", utilisateur);

        if(form.getErrors().isEmpty()) {
            resp.sendRedirect(req.getContextPath()+"/user-restricted/profil"); // Returns to the main page
        } else {
            req.getRequestDispatcher("/user-restricted/modifier_profil.jsp").forward(req, resp);
        }


     }

}

package servlets;

import beans.Utilisateur;
import forms.ConnexionForm;
import sql.SQLConnector;
import sql.UtilisateurTable;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Connecter extends HttpServlet {

    public static final String ATT_USER = "utilisateur";
    public static final String ATT_FORM = "form";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute(Util.ATT_SESSION_USER);
        req.setAttribute("utilisateur",utilisateur);
        req.getRequestDispatcher("/connexion.jsp").forward(req, resp);
  }


    // TODO Finish this method
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ConnexionForm form = new ConnexionForm();
        Utilisateur tmpUser = form.connecterUtilisateur(req);
        if(UtilisateurTable.login(tmpUser)) {
            Utilisateur utilisateur = UtilisateurTable.RecupInfoUser(tmpUser.getEmail());
            /* Récupération de la session depuis la requête */
            HttpSession session = req.getSession();
            session.setAttribute(Util.ATT_SESSION_USER, utilisateur);

            /* Stockage du formulaire et du bean dans l'objet request */
            req.setAttribute(ATT_FORM, form);
            req.setAttribute(ATT_USER, utilisateur);

            resp.sendRedirect(req.getContextPath()+"/user-restricted/profil");
        } else {
            req.getServletContext().getRequestDispatcher("/connexion.jsp").forward(req, resp);
        }

    }
}

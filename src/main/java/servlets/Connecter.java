package servlets;

import beans.Utilisateur;
import forms.ConnexionForm;
import sql.SQLConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Connecter extends HttpServlet {

    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String ATT_USER = "utilisateur";
    public static final String ATT_FORM = "form";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/connexion.jsp").forward(req, resp);
  }


    // TODO Finish this method
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ConnexionForm form = new ConnexionForm();

        Utilisateur utilisateur = form.connecterUtilisateur(req);

            if(SQLConnector.getConnection().login(utilisateur)) {

                Utilisateur util = SQLConnector.getConnection().RecupInfoUser(utilisateur.getEmail());
                utilisateur.setPrenom(util.getPrenom());
                utilisateur.setNom(util.getNom());
                utilisateur.setDate(util.getDate());

                /* Récupération de la session depuis la requête */
                HttpSession session = req.getSession();
                session.setAttribute(ATT_SESSION_USER, utilisateur);

                /* Stockage du formulaire et du bean dans l'objet request */
                req.setAttribute(ATT_FORM, form);
                req.setAttribute(ATT_USER, utilisateur);

                //resp.sendRedirect(req.getContextPath()+"/profil.jsp");
                req.getServletContext().getRequestDispatcher("/profil.jsp").forward(req, resp);

            } else {
                req.getServletContext().getRequestDispatcher("/connexion.jsp").forward(req, resp);
            }

    }
}

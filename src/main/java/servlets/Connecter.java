package servlets;

import beans.Utilisateur;
import dao.DAOFactory;
import dao.UtilisateurDAO;
import forms.ConnexionForm;
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

    private UtilisateurDAO utilisateurDAO;


    @Override
    public void init() throws ServletException {
        this.utilisateurDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getUtilisateurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/connexion.jsp").forward(req, resp);
  }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            ConnexionForm form = new ConnexionForm(utilisateurDAO);
            Utilisateur user = form.connecterUtilisateur(req);
            req.setAttribute(ATT_FORM, form);
            req.setAttribute(ATT_USER, user);
            if(form.getErrors().isEmpty()) {
                HttpSession session = req.getSession();
                session.setAttribute(Util.ATT_SESSION_USER, user);
                resp.sendRedirect(req.getContextPath()+"/user-restricted/profil");
            } else {
                req.getServletContext().getRequestDispatcher("/connexion.jsp").forward(req, resp);
            }
      }
}

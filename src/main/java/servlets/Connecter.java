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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Connecter extends HttpServlet {

    private UtilisateurDAO utilisateurDAO;

    @Override
    public void init() throws ServletException {
        this.utilisateurDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getUtilisateurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/connexion.jsp").forward(req, resp);		// forward the request to the jsp login form
  }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            ConnexionForm form = new ConnexionForm(utilisateurDAO);
            Utilisateur user = form.connecterUtilisateur(req);
            req.setAttribute(Util.ATT_FORM, form);
            req.setAttribute(Util.ATT_FORM_USER, user);
            if(form.getErrors().isEmpty()) { // login success
                if(user.getDateContamination() != null) { // if user has covid
                    if(ChronoUnit.DAYS.between(user.getDateContamination(), LocalDate.now()) >= 15) { // for 15 days or more
                        user.setDateContamination(null);
                        user.setContamine(false);
                        utilisateurDAO.updateContamine(user); // reset user covid state in db
                    }
                }
                HttpSession session = req.getSession();
                session.setAttribute(Util.ATT_SESSION_USER, user);
                resp.sendRedirect(req.getContextPath()+"/user-restricted/profil"); // Goes to the profile page
            } else {
                req.getServletContext().getRequestDispatcher("/connexion.jsp").forward(req, resp); // stays on login page and display errors
            }
      }
}

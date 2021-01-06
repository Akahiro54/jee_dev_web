package servlets;

import beans.Utilisateur;
import dao.DAOFactory;
import dao.UtilisateurDAO;
import forms.InscriptionForm;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AjouterUtilisateur extends HttpServlet {

    private UtilisateurDAO utilisateurDAO;

    @Override
    public void init() throws ServletException {
        this.utilisateurDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getUtilisateurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/user-restricted/ajout_utilisateur.jsp").forward(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InscriptionForm form = new InscriptionForm(utilisateurDAO);
        Utilisateur userCreated = form.inscrireUtilisateur(req);
        req.setAttribute(Util.ATT_FORM, form);
        req.setAttribute(Util.ATT_FORM_USER, userCreated);
        if(form.getErrors().isEmpty()) { // ajout success
            resp.sendRedirect(req.getContextPath()+"/user-restricted/pannel_admin");
        } else {
            req.getRequestDispatcher("/user-restricted/ajout_utilisateur.jsp").forward(req, resp); // display errors
        }
    }

}

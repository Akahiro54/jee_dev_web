package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Utilisateur;
import dao.DAOFactory;
import dao.UtilisateurDAO;
import forms.InscriptionForm;
import tools.Util;

public class Inscription extends HttpServlet {

    private UtilisateurDAO utilisateurDAO;


    @Override
    public void init() throws ServletException {
        this.utilisateurDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getUtilisateurDAO();
    }

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// forward the request to the jsp register form
	    req.getRequestDispatcher("/inscription.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InscriptionForm form = new InscriptionForm(utilisateurDAO);

        Utilisateur userCreated = form.inscrireUtilisateur(req);

        req.setAttribute("form", form);
        req.setAttribute("user", userCreated);

        if(form.getErrors().isEmpty()) { // inscription success
            resp.sendRedirect(req.getContextPath()+"/index.jsp"); // Returns to the main page
        } else {
            req.getRequestDispatcher("/inscription.jsp").forward(req, resp); // stays on subscription page and display errors
        }
    }

}

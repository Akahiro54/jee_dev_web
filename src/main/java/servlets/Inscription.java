package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Utilisateur;
import forms.InscriptionForm;
import sql.SQLConnector;

public class Inscription extends HttpServlet {

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// forward the request to the jsp register form
	    req.getRequestDispatcher("/inscription.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InscriptionForm form = new InscriptionForm();

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

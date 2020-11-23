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
import sql.SQLConnector;

public class Inscription extends HttpServlet {

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// forward the request to the jsp register form
	    req.getRequestDispatcher("/formulaire.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get request parameters
        String userMail = req.getParameter("email");
        String userPass = req.getParameter("pass");

        if(userMail != null && userPass != null) {
            // TODO : user form & fields verification

            // Creates the user bean
            Utilisateur user = new Utilisateur();

            // Set user properties
            user.setEmail(userMail);
            user.setPass(userPass);

            // Tries to save the user to the database
            SQLConnector.getConnection().createUser(user);
        }


        // Returns to the main page
        resp.sendRedirect(req.getContextPath()+"/index.jsp");
	}

	
}

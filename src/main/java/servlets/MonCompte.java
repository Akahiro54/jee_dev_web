package servlets;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import beans.Utilisateur;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class MonCompte extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    	/* Création et initialisation du message. */
    	String userMail = req.getParameter("email");
    	String userPass = req.getParameter("pass");
    		
    	/* Création du bean */
    	Utilisateur user = new Utilisateur();
    	
    	/* Initialisation de ses propriétés */
    	user.setEmail(userMail);
    	user.setPass(userPass);
    		
    	/* Stockage du message et du bean dans l'objet req */
    	req.setAttribute( "user", user );
    		
    	/* Transmission de la paire d'objets req/response à notre JSP */
    	this.getServletContext().getRequestDispatcher( "/account.jsp" ).forward( req, resp );
    	
    	
    	/* OLD CODE FROM Formulaire.java
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

         Utilisateur utilisateurBean = new Utilisateur();

         if(email != null && pass != null) {
             if(email.equals(utilisateurBean.getEmail()) && pass.equals(utilisateurBean.getPass())) {
                 request.setAttribute("user", utilisateurBean);
                 this.getServletContext().getRequestDispatcher( "/index.jsp" ).forward( request, response );
             }
         }



         request.getRequestDispatcher("/formulaire.jsp").forward(request, response);
         */
    }

    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

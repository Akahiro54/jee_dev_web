package servlets;

import beans.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Profil extends HttpServlet  {
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
        this.getServletContext().getRequestDispatcher( "/profil.jsp" ).forward( req, resp );

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

}

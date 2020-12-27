package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Deconnecter extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        /* Récupération et destruction de la session en cours */
        HttpSession session = request.getSession();
        session.invalidate();

        this.getServletContext().getRequestDispatcher("/connexion").forward( request, response );
    }
}

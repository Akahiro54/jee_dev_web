package servlets;

import beans.Utilisateur;
import sql.SQLConnector;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Profil extends HttpServlet  {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute(Util.ATT_SESSION_USER);
        if(utilisateur == null) { // if no user, redirect to home page
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        } else {
            req.setAttribute("utilisateur",utilisateur);
            this.getServletContext().getRequestDispatcher( "/user-restricted/profil.jsp" ).forward( req, resp );
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ondiogndiongiondsiognoding");
    }



}

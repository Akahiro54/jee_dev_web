package servlets;

import beans.Activite;
import beans.Lieu;
import beans.Utilisateur;
import forms.ActiviteForm;
import sql.SQLConnector;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Activites extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);
        if(utilisateur == null)  {
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        }  else  {
            HashMap<Activite, String> activites = SQLConnector.getConnection().getMyActivities(utilisateur.getId());
            req.setAttribute("activites", activites);
            req.getRequestDispatcher("/user-restricted/activites.jsp").forward(req,resp);
        }
    }

    //TODO
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

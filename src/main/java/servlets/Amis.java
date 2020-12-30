package servlets;

import beans.Activite;
import beans.Utilisateur;
import dao.AmisTable;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

public class Amis extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);
        ArrayList<Utilisateur> listeamis;
        listeamis = AmisTable.getAmis(utilisateur.getId());
        req.setAttribute("listeamis", listeamis);

        if (utilisateur.getImage() != null)
        {
            String encode = Base64.getEncoder().encodeToString(utilisateur.getImage());
            req.setAttribute("imgBase", encode);
        }

        req.getRequestDispatcher("/user-restricted/amis.jsp").forward(req,resp);


    }

    //TODO
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }
}

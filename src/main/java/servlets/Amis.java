package servlets;

import beans.Utilisateur;
import dao.DAOFactory;
import dao.UtilisateurDAO;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

public class Amis extends HttpServlet {

    private UtilisateurDAO utilisateurDAO;


    @Override
    public void init() throws ServletException {
        this.utilisateurDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getUtilisateurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);
        ArrayList<Utilisateur> listeamis;
        listeamis = new ArrayList<>(utilisateurDAO.getAmis(utilisateur.getId()));
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
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);
        String greetings = "Salut," + utilisateur.getPseudo() + "!";
        resp.setContentType("text/plain");
        resp.getWriter().write(greetings);
    }
}

package servlets;

import beans.Utilisateur;
import com.google.gson.Gson;
import dao.DAOFactory;
import dao.UtilisateurDAO;
import tools.FormTools;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

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
        ArrayList<Utilisateur> listeAmis = new ArrayList<>(utilisateurDAO.getFriends(utilisateur.getId()));
        req.setAttribute("listeamis", listeAmis);
        HashMap<Integer,String> listeImage = new HashMap<>();
        for (Utilisateur listeami : listeAmis) {
            if (listeami.getImage() != null) {
                listeImage.put(listeami.getId(), Base64.getEncoder().encodeToString(listeami.getImage()));
            }
        }
        System.out.println(listeImage.get(7));
        req.setAttribute("listeImage", listeImage);
        req.getRequestDispatcher("/user-restricted/amis.jsp").forward(req,resp);


    }

    //TODO
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);
        resp.setContentType("text/plain");
        String s = (String)req.getParameter("nickSearch");
        boolean validated = false;

        /**
         * Search friends
         */
        if(s != null) {
            try {
                FormTools.validateNickname(s);
                FormTools.validateFieldSize(s);
                validated = true;
            } catch (Exception e) {
            }
            if (validated) {
                List<Utilisateur> utilisateurs = utilisateurDAO.searchNonFriends(utilisateur.getId(), s);
                Gson gson = new Gson();
                String result = gson.toJson(utilisateurs);
                resp.getWriter().write(result);
            } else {
                resp.getWriter().write("error");
            }
        }
        /**
         * Add friends
         */
        if(req.getParameter("ami") != null) {
            String error = "Impossible d'ajouter l'ami";
            String success = "Demande d'ami effectuée avec succès !";
            if(utilisateur != null) {
                int idUtilisateur = utilisateur.getId();
                try {
                    int idAmi = Integer.parseInt(req.getParameter("ami"));
                    if(!utilisateurDAO.areFriends(idAmi,idUtilisateur)) {
                        if(utilisateurDAO.addFriend(idUtilisateur, idAmi)) {
                           resp.getWriter().write(success);
                        }
                    } else {
                        error += ": vous êtes déjà ami avec cette personne.";
                        resp.getWriter().write(error);
                    }
                }catch(Exception e) {
                    resp.getWriter().write(error);
                }
            }
        }
    }
}

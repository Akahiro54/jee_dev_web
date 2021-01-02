package servlets;

import beans.Notification;
import beans.TypeNotification;
import beans.Utilisateur;
import com.google.gson.Gson;
import dao.AmisDAO;
import dao.DAOFactory;
import dao.NotificationDAO;
import dao.UtilisateurDAO;
import tools.FormTools;
import tools.JQueryAnswer;
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

    private AmisDAO amisDAO;
    private NotificationDAO notificationDAO;
    private UtilisateurDAO utilisateurDAO;

    @Override
    public void init() throws ServletException {
        this.amisDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getAmisDAO();
        this.notificationDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getNotificationDAO();
        this.utilisateurDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getUtilisateurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);
        ArrayList<Utilisateur> listeAmis = new ArrayList<>(amisDAO.getFriends(utilisateur.getId()));
        req.setAttribute("listeamis", listeAmis);
        HashMap<Integer,String> listeImage = new HashMap<>();
        for (Utilisateur listeami : listeAmis) {
            if (listeami.getImage() != null) {
                listeImage.put(listeami.getId(), Base64.getEncoder().encodeToString(listeami.getImage()));
            }
        }
        req.setAttribute("listeImage", listeImage);
        req.getRequestDispatcher("/user-restricted/amis.jsp").forward(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);
        resp.setContentType("text/plain");
        String s = (String)req.getParameter("nickSearch");
        if(utilisateur != null) {
            /**
             * Search friends
             */
            if(s != null) {
                boolean validated = false;
                try {
                    FormTools.validateNickname(s);
                    FormTools.validateFieldSize(s);
                    validated = true;
                } catch (Exception e) {
                }
                if (validated) {
                    List<Utilisateur> utilisateurs = amisDAO.searchNonFriends(utilisateur.getId(), s);
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
                JQueryAnswer fail = new JQueryAnswer(false, "Impossible d'ajouter l'ami");
                JQueryAnswer success = new JQueryAnswer(true, "Demande d'ami effectuée avec succès !");
                int idUtilisateur = utilisateur.getId();
                try {
                    int idAmi = Integer.parseInt(req.getParameter("ami"));
                    if(utilisateurDAO.idExists(idAmi)) {
                        beans.Amis amis = new beans.Amis();
                        amis.setIdAmi1(idUtilisateur);
                        amis.setIdAmi2(idAmi);
                        // if they are not friends and there is no unread friend request between the users
                        if(!amisDAO.areFriends(amis) && !notificationDAO.hasAlreadyAFriendRequest(idUtilisateur, idAmi)) {
                            Notification notification = Notification.buildNotification(utilisateur, idAmi, TypeNotification.AMI);
                            if(notificationDAO.add(notification)) {
                                resp.getWriter().write(new Gson().toJson(success));
                            }
                        } else {
                            fail.appendMessage(": vous êtes déjà ami ou possédez déjà une demande d'ami avec cette personne.");
                            resp.getWriter().write(new Gson().toJson(fail));
                        }
                    } else {
                        fail.appendMessage(": l'ami que vous essayez d'ajouter n'existe pas.");
                        resp.getWriter().write(new Gson().toJson(fail));
                    }
                }catch(Exception e) {
                    resp.getWriter().write(new Gson().toJson(fail));
                }
            }
        }
    }
}

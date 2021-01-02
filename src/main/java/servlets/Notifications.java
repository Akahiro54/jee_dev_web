package servlets;

import beans.EtatNotification;
import beans.Notification;
import beans.Utilisateur;
import com.google.gson.Gson;
import dao.AmisDAO;
import dao.DAOFactory;
import dao.NotificationDAO;
import dao.UtilisateurDAO;
import tools.JQueryAnswer;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Notifications extends HttpServlet {

    private NotificationDAO notificationDAO;
    private AmisDAO amisDAO;
    private UtilisateurDAO utilisateurDAO;
    @Override
    public void init() throws ServletException {
        this.notificationDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getNotificationDAO();
        this.amisDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getAmisDAO();
        this.utilisateurDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getUtilisateurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);
        if(utilisateur != null) {
            ArrayList<Notification> notifications = new ArrayList<>(notificationDAO.getNotifications(utilisateur.getId(), EtatNotification.NON_LUE));
            req.setAttribute(Util.ATT_FORM_NOTIFICATIONS, notifications);
            req.getRequestDispatcher("/user-restricted/notifications.jsp").forward(req,resp);
        } else {
            // redirect home
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);
        HashMap<String, String> results = new HashMap<>();
        JQueryAnswer success = new JQueryAnswer(true, "");
        JQueryAnswer fail = new JQueryAnswer(false, "");
        //if the user is still logged in
        if(utilisateur != null) {
            /**
             * Answer a friend request
             */
            String ami = (String)req.getParameter("ami");   //get friend id parameter
            String action = (String)req.getParameter("action"); //get action (accept or decline friend request)
            String id = (String)req.getParameter("id");     //get notification id parameter
            int idUtilisateur = utilisateur.getId();    //get current user id

            // check if all the parameters are set to start trying to accept or decline
            if(ami != null && action != null && id != null) {
                success.appendMessage("Demande");
                fail.appendMessage("Impossible d'accepter ou refuser la demande d'ami");
                resp.setContentType("text/plain");          // set answer content type
                // try to parse int parameters, in case of failure returns an error
                try {
                    int idAmi = Integer.parseInt(ami);
                    int idNotif = Integer.parseInt(id);
                    Notification n = notificationDAO.get(idNotif);
                    // checks if the source of the notification is still an user of the application, otherwise returns an error
                    // this condition should never be thrown except if the user tries to execute the post manually
                    if(utilisateurDAO.idExists(idAmi)) {
                        // checks if the notification object with the id given exists
                        // and if the source and destination friends are correct
                        if(n != null && n.getUtilisateurSource() == idAmi && n.getUtilisateurDestination() == idUtilisateur) {
                            beans.Amis amis = new beans.Amis();
                            amis.setIdAmi1(idAmi);
                            amis.setIdAmi2(idUtilisateur);
                            // checks if the action is one of the authorized actions
                            switch (action) {
                                case "accept":  //if the user wants to accept the demand, tries to update the amis objects
                                    if(amisDAO.add(amis) && notificationDAO.changeState(n, EtatNotification.LUE)) {
                                        success.appendMessage(" acceptée avec succès !");
                                        resp.getWriter().write(new Gson().toJson(success));
                                    } else {
                                        fail.appendMessage(". Merci de réessayer plus tard");
                                        resp.getWriter().write(new Gson().toJson(fail));
                                    }
                                    break;
                                case "decline": //if the user wants to decline the demand, tries to update the amis objects
                                    if(notificationDAO.changeState(n, EtatNotification.LUE)) {
                                        success.appendMessage(" refusée avec succès !");
                                        resp.getWriter().write(new Gson().toJson(success));
                                    } else {
                                        fail.appendMessage(". Merci de réessayer plus tard");
                                        resp.getWriter().write(new Gson().toJson(fail));
                                    }
                                    break;
                                default:
                                    fail.appendMessage(" : action inconnue.");
                                    resp.getWriter().write(new Gson().toJson(fail));
                                    break;
                            }
                        } else {
                            fail.appendMessage(" : la notification n'existe pas ou l'ami ne correspond pas.");
                            resp.getWriter().write(new Gson().toJson(fail));  }
                    } else {
                        fail.appendMessage(" : l'utilisateur ayant envoyé la demande n'existe pas ou plus.");
                        resp.getWriter().write(new Gson().toJson(fail));
                    }
                } catch (Exception e) { resp.getWriter().write(new Gson().toJson(fail)); }
            }


            /**
             * Mark a notification as read
             */
            String notification = (String)req.getParameter("setRead");
            if(notification != null) {
                success.appendMessage("Message marqué comme lu.");
                fail.appendMessage("Impossible de marquer le message comme lu, merci de réessayer plus tard");
                try {
                    int idNotif = Integer.parseInt(notification);
                    Notification n = notificationDAO.get(idNotif);
                    if(n != null && n.getUtilisateurDestination() == idUtilisateur) {
                        if(notificationDAO.changeState(n, EtatNotification.LUE)) {
                            resp.getWriter().write(new Gson().toJson(success));
                        } else {
                            resp.getWriter().write(new Gson().toJson(fail));
                        }
                    } else {
                        resp.getWriter().write(new Gson().toJson(fail));
                    }
                } catch (Exception e) {
                    resp.getWriter().write(new Gson().toJson(fail));
                }
            }
        }

    }

}

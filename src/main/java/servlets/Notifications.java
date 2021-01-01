package servlets;

import beans.EtatNotification;
import beans.Notification;
import beans.Utilisateur;
import dao.AmisDAO;
import dao.DAOFactory;
import dao.NotificationDAO;
import dao.UtilisateurDAO;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

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

    // 1. get current user
    // 2. get the req notification id
    // 3. check if the notification exists with the current user as destination and is unread (else error);
    // 4. if so, get the notification type.
    //          Case 1 : this is a friend notification
    //              5. check if the friend request exists and if its states is EN ATTENTE (else error);
    //              6. try to change the friend state
    //              7. if that's okay, update the notification state
    //          Case 2 : this is a covid notification
    //              5. try to update the notification state
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);
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
                resp.setContentType("text/plain");          // set answer content type
                String error = "Impossible d'accepter ou refuser la demande d'ami";
                String success = "Demande ";
                // try to parse int parameters, in case of failure returns an error
                try {
                    int idAmi = Integer.parseInt(ami);
                    int idNotif = Integer.parseInt(id);
                    Notification n = notificationDAO.get(idNotif);
                    // checks if the source of the notication is still an user of the application, otherwise returns an error
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
                                        resp.getWriter().write(success + " acceptée avec succès !");
                                    } else {
                                        resp.getWriter().write(error + ". Merci de réessayer plus tard");
                                    }
                                    break;
                                case "decline": //if the user wants to decline the demand, tries to update the amis objects
                                    if(notificationDAO.changeState(n, EtatNotification.LUE)) {
                                        resp.getWriter().write(success + "refusée avec succès !");
                                    } else {
                                        resp.getWriter().write(error + ". Merci de réessayer plus tard");
                                    }
                                    break;
                                default:
                                    resp.getWriter().write(error + " : action inconnue.");
                                    break;
                            }
                        } else { resp.getWriter().write(error + " : la notification n'existe pas ou l'ami ne correspond pas.");  }
                    } else {  resp.getWriter().write(error + " : l'utilisateur ayant envoyé la demande n'existe pas ou plus."); }
                } catch (Exception e) { resp.getWriter().write(error); }
            }
        }

    }

}

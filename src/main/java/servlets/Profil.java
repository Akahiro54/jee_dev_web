package servlets;

import beans.Notification;
import beans.TypeNotification;
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
import java.time.LocalDate;
import java.util.*;

public class Profil extends HttpServlet  {

    private AmisDAO amisDAO;
    private UtilisateurDAO utilisateurDAO;
    private NotificationDAO notificationDAO;

    @Override
    public void init() throws ServletException {
        this.amisDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getAmisDAO();
        this.notificationDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getNotificationDAO();
        this.utilisateurDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getUtilisateurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute(Util.ATT_SESSION_USER);

        if (utilisateur.getImage() != null)
        {
            String encode = Base64.getEncoder().encodeToString(utilisateur.getImage());
            req.setAttribute("imgBase", encode);
        }


        if(utilisateur == null) { // if no user, redirect to home page
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        } else {
            req.setAttribute(Util.ATT_FORM_USER,utilisateur);
            this.getServletContext().getRequestDispatcher( "/user-restricted/profil.jsp" ).forward( req, resp );
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * Triggered when the user says he is positive to COVID-19.
         */
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("sessionUtilisateur");

        String paramCovid = (String)req.getParameter("covid");
        if(paramCovid != null && utilisateur != null) {
            resp.setContentType("text/plain");
            JQueryAnswer success = new JQueryAnswer(true, "");
            JQueryAnswer fail = new JQueryAnswer(false, "Une erreur est survenue : ");
            switch(paramCovid) {
                case "1":
                    int idUtilisateur = utilisateur.getId();
                    List<Utilisateur> amis = amisDAO.getFriends(idUtilisateur);
                    List<Utilisateur> casContacts = utilisateurDAO.getAllUserOnSamePlacesAtTheSameTime(utilisateur);
                    Set<Utilisateur> toNotify = new HashSet<>(amis);
                    toNotify.addAll(casContacts);
                    ArrayList<Notification> notifications = new ArrayList<>();
                    for(Utilisateur u : toNotify) {
                        notifications.add(Notification.buildNotification(utilisateur, u.getId(), TypeNotification.COVID));
                    }
                    if(notificationDAO.addMultiple(notifications)) { // All notifications were sent
                        utilisateur.setContamine(true);
                        utilisateur.setDateContamination(LocalDate.now());
                        if(utilisateurDAO.updateContamine(utilisateur)) {
                            success.appendMessage(utilisateur.getDateContamination().toString());
                            resp.getWriter().write(new Gson().toJson(success));
                        } else {
                            fail.appendMessage("impossible de modifier votre profil utilisateur. Merci de réessayer plus tard.");
                        }
                    } else {
                        fail.appendMessage("impossible d'envoyer les notifications aux autres utilisateurs'. Merci de réessayer plus tard.");
                    }
                break;
                default:
                break;
            }
        }
    }



}

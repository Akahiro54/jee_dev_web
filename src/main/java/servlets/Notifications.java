package servlets;

import beans.EtatNotification;
import beans.Notification;
import beans.Utilisateur;
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

    @Override
    public void init() throws ServletException {
        this.notificationDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getNotificationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);
        if(utilisateur != null) {
            ArrayList<Notification> notifications = new ArrayList<>(notificationDAO.getNotifications(utilisateur.getId(), EtatNotification.NON_LUE));
            req.setAttribute("notifications", notifications);
            req.getRequestDispatcher("/user-restricted/notifications.jsp").forward(req,resp);
        } else {
            // redirect home
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

}

package filters;

import beans.Utilisateur;
import dao.DAOFactory;
import dao.NotificationDAO;
import tools.Util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class NotificationsFilter implements Filter {
    private NotificationDAO notificationDAO;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            NotificationDAO notificationDAO = ((DAOFactory)servletRequest.getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getNotificationDAO();
            HttpSession session =  ((HttpServletRequest)servletRequest).getSession();
            Utilisateur currentUser = ((Utilisateur)session.getAttribute(Util.ATT_SESSION_USER));
            if(currentUser != null) {
                if(notificationDAO.hasNotifications(currentUser.getId())) {
                    servletRequest.setAttribute("hasNotifications", true);
                } else {
                    servletRequest.setAttribute("hasNotifications", false);
                }
            } else {
                servletRequest.setAttribute("hasNotifications", false);
            }
        } catch(Exception e) {
            servletRequest.setAttribute("hasNotifications", false);
            System.err.println("Cannot check notifications, skipping");
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}

package servlets;

import beans.Notification;
import beans.TypeNotification;
import beans.Utilisateur;
import dao.AdminDAO;
import dao.AmisDAO;
import dao.DAOFactory;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class PannelAdmin extends HttpServlet {

    private AdminDAO adminDAO;

    @Override
    public void init() throws ServletException {
        this.adminDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getAdminDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute(Util.ATT_SESSION_USER);

        ArrayList<Utilisateur> listeUtilisateur = new ArrayList<>(adminDAO.getUtilisateur(utilisateur.getId()));
        req.setAttribute("listeUtilisateur", listeUtilisateur);


        if (utilisateur == null) { // if no user, redirect to home page
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        } else {
            req.setAttribute(Util.ATT_FORM_USER, utilisateur);
            this.getServletContext().getRequestDispatcher("/user-restricted/pannel_admin.jsp").forward(req, resp);
        }

        String delete = (String) req.getParameter("delete");
        if (utilisateur != null && delete != null) {
                int idUtilisateur = Integer.parseInt(delete);

                if (adminDAO.delete(idUtilisateur)) {
                    resp.sendRedirect(req.getContextPath() + "/user-restricted/pannel_admin");
                }
            }

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}

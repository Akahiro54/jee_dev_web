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

public class PannelAdmin extends HttpServlet {

    private UtilisateurDAO utilisateurDAO;

    @Override
    public void init() throws ServletException {
        this.utilisateurDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getUtilisateurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute(Util.ATT_SESSION_USER);
        if (utilisateur != null) {
            ArrayList<Utilisateur> listeUtilisateur = new ArrayList<>(utilisateurDAO.getOtherUsers(utilisateur.getId()));
            req.setAttribute("listeUtilisateur", listeUtilisateur);
            String delete = (String) req.getParameter("delete");
            if (delete != null) {
                try {
                    int idUtilisateur = Integer.parseInt(delete);
                    if (utilisateurDAO.delete(idUtilisateur)) {
                        resp.sendRedirect(req.getContextPath() + "/user-restricted/pannel_admin");
                        return;
                    }
                } catch (Exception e) { }
            }
            this.getServletContext().getRequestDispatcher("/user-restricted/pannel_admin.jsp").forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}

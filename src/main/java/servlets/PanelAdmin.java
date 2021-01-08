package servlets;

import beans.Activite;
import beans.Lieu;
import beans.Utilisateur;
import dao.ActiviteDAO;
import dao.DAOFactory;
import dao.LieuDAO;
import dao.UtilisateurDAO;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PanelAdmin extends HttpServlet {

    private UtilisateurDAO utilisateurDAO;
    private ActiviteDAO activiteDAO;
    private LieuDAO lieuDAO;

    @Override
    public void init() throws ServletException {
        this.utilisateurDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getUtilisateurDAO();
        this.activiteDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getActiviteDAO();
        this.lieuDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getLieuDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute(Util.ATT_SESSION_USER);
        if (utilisateur != null) {
            ArrayList<Utilisateur> listeUtilisateur = new ArrayList<>(utilisateurDAO.getOtherUsers(utilisateur.getId()));
            req.setAttribute("listeUtilisateur", listeUtilisateur);

            List<Activite> listeActivite = activiteDAO.getAllActivities();
            req.setAttribute("listeActivite",listeActivite);

            List<Lieu> listeLieux = lieuDAO.getAllPlaces();
            req.setAttribute("listeLieux",listeLieux);

            String delete = req.getParameter("delete");
            String deleteActivite = req.getParameter("deleteActivite");
            String deleteLieu = req.getParameter("deleteLieu");

            if (delete != null) {
                try {
                    int idUtilisateur = Integer.parseInt(delete);
                    if (utilisateurDAO.delete(idUtilisateur)) {
                        resp.sendRedirect(req.getContextPath() + "/admin-restricted/panel_admin");
                        return;
                    }
                } catch (Exception e) { }
            }

            if (deleteActivite != null) {
                try {
                    int idActivite = Integer.parseInt(deleteActivite);
                    if (activiteDAO.delete(idActivite)) {
                        resp.sendRedirect(req.getContextPath() + "/admin-restricted/panel_admin");
                        return;
                    }
                } catch (Exception e) { }
            }

            if (deleteLieu != null) {
                try {
                    int idLieu = Integer.parseInt(deleteLieu);
                    if (lieuDAO.delete(idLieu)) {
                        resp.sendRedirect(req.getContextPath() + "/admin-restricted/panel_admin");
                        return;
                    }
                } catch (Exception e) { }
            }


            this.getServletContext().getRequestDispatcher("/admin-restricted/panel_admin.jsp").forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}

package servlets;

import beans.Utilisateur;
import dao.ActiviteDAO;
import dao.DAOFactory;
import dao.LieuDAO;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Activite extends HttpServlet {

    private LieuDAO lieuDAO;
    private ActiviteDAO activiteDAO;

    @Override
    public void init() throws ServletException {
        this.lieuDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getLieuDAO();
        this.activiteDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getActiviteDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);
        if(utilisateur == null)  {
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        }  else  {
            String strLieu = (String)req.getParameter("lieu");
            String strActivite = (String)req.getParameter("activite");
            if(strLieu == null || strActivite == null) {
                resp.sendRedirect(req.getContextPath()+"/user-restricted/activites");
            } else {
                try {
                    int idLieu = Integer.parseInt(strLieu);
                    int idActivite = Integer.parseInt(strActivite);

                    if(lieuDAO.placeExistsById(idLieu)
                       && activiteDAO.activityExists(idActivite)
                       && activiteDAO.isPlaceInActivity(idActivite, idLieu)
                       && activiteDAO.isUserInActivity(idActivite, utilisateur.getId()) ) {
                        beans.Activite activite = activiteDAO.get(idActivite);
                        beans.Lieu lieu = lieuDAO.get(idLieu);
                        if(activite != null && lieu != null) {
                            req.setAttribute("activite", activite);
                            req.setAttribute("lieu",lieu);
                            req.getRequestDispatcher("/user-restricted/activite.jsp").forward(req,resp);
                        } else {
                            resp.sendRedirect(req.getContextPath()+"/user-restricted/activites");
                        }
                    } else {
                        resp.sendRedirect(req.getContextPath()+"/user-restricted/activites");
                    }
                } catch (Exception e) {
                    resp.sendRedirect(req.getContextPath()+"/user-restricted/activites");
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
    }
}

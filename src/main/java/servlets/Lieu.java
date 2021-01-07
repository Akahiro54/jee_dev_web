package servlets;

import beans.Utilisateur;
import dao.DAOFactory;
import dao.LieuDAO;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Lieu extends HttpServlet {

    private LieuDAO lieuDAO;


    @Override
    public void init() throws ServletException {
        this.lieuDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getLieuDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);
        if(utilisateur == null)  {
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        }  else  {
            String strLieu = (String)req.getParameter("lieu");
            if(strLieu == null) {
                resp.sendRedirect(req.getContextPath()+"/user-restricted/activites");
            } else {
                try {
                    int idLieu = Integer.parseInt(strLieu);
                    if(lieuDAO.placeExistsById(idLieu)) {
                        req.setAttribute("lieu",lieuDAO.get(idLieu));
                        req.getRequestDispatcher("/user-restricted/lieu.jsp").forward(req,resp);
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
        super.doPost(req, resp);
    }
}

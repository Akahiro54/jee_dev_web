package servlets;

import beans.Lieu;
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
import java.util.ArrayList;

public class Lieux extends HttpServlet {

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
            ArrayList<Lieu> lieux = new ArrayList<>(lieuDAO.getAllPlaces());
            req.setAttribute("places", lieux);
            req.getRequestDispatcher("/user-restricted/lieux.jsp").forward(req,resp);
        }
    }



}

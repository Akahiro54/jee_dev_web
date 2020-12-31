package servlets;

import beans.Lieu;
import dao.ActiviteDAO;
import dao.DAOFactory;
import dao.LieuDAO;
import forms.LieuForm;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreerLieu extends HttpServlet {

    private LieuDAO lieuDAO;

    @Override
    public void init() throws ServletException {
        this.lieuDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getLieuDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/user-restricted/creer_lieu.jsp").forward(req, resp);
    }

    //TODO
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LieuForm form = new LieuForm(lieuDAO);
        Lieu lieu = form.ajouterLieu(req);
        req.setAttribute(Util.ATT_FORM, form);
        req.setAttribute(Util.ATT_FORM_PLACE, lieu);
        if(form.getErrors().isEmpty()) {
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        } else {
            req.getRequestDispatcher("/user-restricted/creer_lieu.jsp").forward(req, resp);
        }
    }
}

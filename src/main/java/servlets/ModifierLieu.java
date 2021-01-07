package servlets;

import beans.Lieu;
import dao.DAOFactory;
import dao.LieuDAO;
import forms.LieuForm;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ModifierLieu extends HttpServlet {

    private LieuDAO lieuDAO;
    private int idLieu;

    @Override
    public void init() throws ServletException {
        this.lieuDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getLieuDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("l");
        idLieu = Integer.parseInt(id);
        Lieu lieu = lieuDAO.get(idLieu);
        req.setAttribute(Util.ATT_FORM_PLACE,lieu);
        this.getServletContext().getRequestDispatcher( "/user-restricted/modifier_lieu.jsp").forward( req, resp );

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LieuForm form = new LieuForm(lieuDAO);
        Lieu lieu = form.modifierLieu(req,idLieu);
        req.setAttribute(Util.ATT_FORM, form);
        req.setAttribute(Util.ATT_FORM_PLACE, lieu);
        if(form.getErrors().isEmpty()) {
            resp.sendRedirect(req.getContextPath()+"/user-restricted/pannel_admin");
        } else {
            req.getRequestDispatcher("/user-restricted/modifier_lieu.jsp").forward(req, resp);
        }
    }
}

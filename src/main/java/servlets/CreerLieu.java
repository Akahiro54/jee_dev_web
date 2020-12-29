package servlets;

import beans.Lieu;
import forms.LieuForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreerLieu extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/user-restricted/creer_lieu.jsp").forward(req, resp);
    }

    //TODO
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LieuForm form = new LieuForm();
        Lieu lieu = form.ajouterLieu(req);
        req.setAttribute("form", form);
        req.setAttribute("place", lieu);
        if(form.getErrors().isEmpty()) {
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        } else {
            System.err.println(form.getErrors());
            req.getRequestDispatcher("/user-restricted/creer_lieu.jsp").forward(req, resp);
        }
    }
}

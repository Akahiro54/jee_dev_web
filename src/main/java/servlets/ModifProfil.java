package servlets;

import beans.Utilisateur;
import forms.InscriptionForm;
import forms.ModifProfilForm;
import sql.SQLConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ModifProfil extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("sessionUtilisateur");
        req.setAttribute("utilisateur",utilisateur);
        this.getServletContext().getRequestDispatcher( "/modifprofil.jsp" ).forward( req, resp );

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ModifProfilForm modifProfilForm= new ModifProfilForm();
        try {
            modifProfilForm.modifierUtilisateur(req);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath()+"/profil"); // Returns to the main page
    }

}

package servlets;

import beans.Utilisateur;
import forms.ConnexionForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Connecter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/connexion.jsp").forward(req, resp);
  }


    // TODO Finish this method
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ConnexionForm form = new ConnexionForm();

        if(form.connecterUtilisateur(req)) {
            System.out.println("SUCCESS ! Here set sesion variable");
        } else {
            System.out.println("FAILED ! Login failed");
        }
    }
}

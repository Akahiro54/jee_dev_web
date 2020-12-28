package forms;

import beans.Utilisateur;
import sql.SQLConnector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static tools.FormulaireValidation.*;
import static tools.FormulaireValidation.validateFirstname;

public class ModifProfilForm {

    public void modifierUtilisateur(HttpServletRequest request ) throws Exception {
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("sessionUtilisateur");


        String prenom = request.getParameter("modifprenom");
        String nom = request.getParameter("modifnom");
        String email = request.getParameter("modifemail");
        Date date = Date.valueOf(request.getParameter("modifdate"));
        String ancienEmail = utilisateur.getEmail();
        String motdepasse = request.getParameter("motdepasse");
        String confirmation = request.getParameter("confirmation");

        System.out.println(motdepasse);

        validateMail(email);
        utilisateur.setEmail(email);

        validateFirstname(prenom);
        utilisateur.setPrenom(prenom);

        validateLastname(nom);
        utilisateur.setNom(nom);

        //validatePasswords(motdepasse,confirmation);
        //utilisateur.setPass(motdepasse);

        utilisateur.setDate(date);

        SQLConnector.getConnection().ModifInfoUser(prenom,nom,email,date,ancienEmail);

    }



}

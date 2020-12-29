package forms;

import beans.Utilisateur;
import sql.SQLConnector;
import tools.FormFields;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;

import java.util.HashMap;
import java.util.Map;

import static tools.FormulaireValidation.*;
import static tools.FormulaireValidation.validateFirstname;

public class ModifProfilForm {

    private Map<String,String> errors = new HashMap<String, String>();

    public void modifierUtilisateur(HttpServletRequest request ) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("sessionUtilisateur");


        String prenom = request.getParameter("modifprenom");
        String nom = request.getParameter("modifnom");
        String email = request.getParameter("modifemail");
        String date = request.getParameter("modifdate");
        String ancienEmail = utilisateur.getEmail();

        InputStream inputStream = null;

        String test = request.getParameter("modifphoto");
        Part filePart = request.getPart("modifphoto");

        if (filePart != null) {

            // Prints out some information
            // for debugging
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());

            // Obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        }
        //utilisateur.setImage(photo);

        validerChamps(utilisateur,prenom,nom,date,email);
        Date date2 = Date.valueOf(request.getParameter("modifdate"));

        if ( errors.isEmpty() ) {
            if (!SQLConnector.getConnection().ModifInfoUser(prenom,nom,email,date2,ancienEmail,inputStream)) {
                addError(FormFields.DATABASE.getFieldName(), Util.DATABASE_ERROR_MESSAGE);
            }
        }

    }

    private void validerChamps(Utilisateur utilisateur,String prenom, String nom, String date, String email)
    {
        try {
            validateMail(email);
            utilisateur.setEmail(email);
        } catch (Exception e) {
            addError("modifemail", e.getMessage());
        }

        try {
            validateFirstname(prenom);
            utilisateur.setPrenom(prenom);
        } catch (Exception e) {
            addError("modifprenom", e.getMessage());
        }

        try {
            validateLastname(nom);
            utilisateur.setNom(nom);
        } catch (Exception e) {
            addError("modifnom", e.getMessage());
        }

        try {
           if(validateBirthdate(date) != null);
            Date date3 = Date.valueOf(date);
            utilisateur.setDate(date3);
        } catch (Exception e) {
            addError("modifdate", e.getMessage());
        }

    }


    public Map<String, String> getErrors() {
        return errors;
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des errors.
     */
    private void addError(String champ, String message) {
        errors.put(champ, message);
    }




}

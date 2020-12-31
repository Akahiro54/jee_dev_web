package forms;

import beans.Utilisateur;
import dao.DAOFactory;
import dao.UtilisateurDAO;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.Date;

import java.util.HashMap;
import java.util.Map;

import static tools.FormTools.*;

@MultipartConfig(maxFileSize = 1699999999)
public class ModifierProfilForm {

    private Map<String,String> errors = new HashMap<String, String>();
    private UtilisateurDAO utilisateurDAO;

    public ModifierProfilForm(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    public void modifierUtilisateur(HttpServletRequest request ) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("sessionUtilisateur");

        String prenom = request.getParameter("modifprenom");
        String nom = request.getParameter("modifnom");
        String email = request.getParameter("modifemail");
        String date = request.getParameter("modifdate");
        String ancienEmail = utilisateur.getEmail();

        InputStream inputStream = null;

        Part filePart = request.getPart("modifphoto");
        String nomImage = getNomFichier(filePart);

        if (filePart != null) {

            inputStream = filePart.getInputStream();

            if (nomImage.equals("\"\""))
            {
            }
            else
            {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                int nRead;
                byte[] data = new byte[16384];

                while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                utilisateur.setImage(buffer.toByteArray());
            }
        }

        validerChamps(utilisateur,prenom,nom,date,email);
        Date date2 = Date.valueOf(date);

        if ( errors.isEmpty() ) {
            if (nomImage.equals("\"\""))
            {
                if (!utilisateurDAO.update(utilisateur,null, prenom, nom, email, date2, ancienEmail)) {
                    addError(Util.GENERIC_DATABASE_FIELD, Util.DATABASE_ERROR_MESSAGE);
                }
            }
            else{
                if (!utilisateurDAO.update(utilisateur,filePart.getInputStream(), prenom, nom, email, date2,nomImage,ancienEmail)) {
                    addError(Util.GENERIC_DATABASE_FIELD, Util.DATABASE_ERROR_MESSAGE);
                }
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
            validateName(prenom);
            utilisateur.setPrenom(prenom);
        } catch (Exception e) {
            addError("modifprenom", e.getMessage());
        }

        try {
            validateName(nom);
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

    private static String getNomFichier( Part part ) {

        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
            if ( contentDisposition.trim().startsWith("filename") ) {
                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 );
            }
        }
        return null;
    }




}

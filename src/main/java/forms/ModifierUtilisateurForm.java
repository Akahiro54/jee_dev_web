package forms;

import beans.Activite;
import beans.Utilisateur;
import dao.UtilisateurDAO;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static tools.FormTools.*;

@MultipartConfig(maxFileSize = 1699999999)
public class ModifierUtilisateurForm {


    private Map<String,String> errors = new HashMap<String, String>();
    private UtilisateurDAO utilisateurDAO;

    public ModifierUtilisateurForm(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    public void modifierUtilisateur(HttpServletRequest request, int idUtilisateur) throws IOException, ServletException {
        Utilisateur utilisateur = new Utilisateur(); // initialize utilisateur

        String prenom = request.getParameter("modifprenom");
        String nom = request.getParameter("modifnom");
        String email = request.getParameter("modifemail");
        String date = request.getParameter("modifdate");
        String role = request.getParameter("modifrole");
        String pseudo = request.getParameter("modifpseudo");

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
                utilisateur.setImage(Util.convertUserImage(buffer.toByteArray()));
            }
        }

        validerChamps(utilisateur,prenom,nom,date,email,pseudo);
        Date date2 = Date.valueOf(date);


        if ( errors.isEmpty() ) {
            if (nomImage.equals("\"\""))
            {
                if (!utilisateurDAO.updateFromAdmin(utilisateur,null, prenom, nom, email, date2,role,pseudo, idUtilisateur)) {
                    addError(Util.GENERIC_DATABASE_FIELD, Util.DATABASE_ERROR_MESSAGE);
                }
            }
            else{
                if (!utilisateurDAO.updateFromAdmin(utilisateur,filePart.getInputStream(), prenom, nom, email, date2,nomImage,role,pseudo,idUtilisateur)) {
                    addError(Util.GENERIC_DATABASE_FIELD, Util.DATABASE_ERROR_MESSAGE);
                }
            }
        }

    }

    private void validerChamps(Utilisateur utilisateur,String prenom, String nom, String date, String email, String pseudo)
    {
        try {
            validateMail(email);
            utilisateur.setEmail(email);
        } catch (Exception e) {
            addError("modifemail", e.getMessage());
        }

        try {
            validateName(prenom);
            validateFieldSize(prenom);
            utilisateur.setPrenom(prenom);
        } catch (Exception e) {
            addError("modifprenom", e.getMessage());
        }

        try {
            validateName(pseudo);
            validateFieldSize(pseudo);
            utilisateur.setPseudo(pseudo);
        } catch (Exception e) {
            addError("modifpseudo", e.getMessage());
        }


        try {
            validateName(nom);
            validateFieldSize(nom);
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

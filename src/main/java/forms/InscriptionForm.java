package forms;

import beans.Utilisateur;
import sql.SQLConnector;
import tools.FormFields;
import tools.Messages;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static tools.FormulaireValidation.*;

public class InscriptionForm {

    private Map<String,String> errors = new HashMap<String, String>();

    public Utilisateur inscrireUtilisateur(HttpServletRequest request ) {
        Utilisateur utilisateur = new Utilisateur(); // initialize user
        FormFields fields = FormFields.FIELD_MAIL; //initialize fields to first field
        for(int i = 0 ; i < FormFields.values().length - 2; i++) { // iterate over fields except the last (database)
            try {
            String currentField = fields.getFieldName();
            String currentFieldValue = getFieldValue(request, currentField);
//            System.out.println("Current field : " + currentField + ", current field value : " + currentFieldValue);
            if(fields == FormFields.FIELD_PASSWORD) { // if special field who also needs confirmation
                    String confirmationField = FormFields.FIELD_CONFIRMATION.getFieldName();
                    String confirmationFieldValue = getFieldValue(request, confirmationField);
                    validateField(fields, utilisateur, currentFieldValue, confirmationFieldValue);
            } else { // else treat field
                    validateField(fields, utilisateur, currentFieldValue);
            }
            } catch (Exception e) {
                addError(fields.getFieldName(), e.getMessage());
            }
            fields = fields.next();
        }
        // if there are no errors
        if ( errors.isEmpty() ) {
            // Tries to save the user to the database
            if (!SQLConnector.getConnection().createUser(utilisateur)) {
                addError(FormFields.DATABASE.getFieldName(), Messages.DATABASE_ERROR_MESSAGE);
            }
        }
    return utilisateur;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    private void validateField(FormFields field, Utilisateur utilisateur, String... data) throws Exception{
        switch(field) {
            case FIELD_MAIL:
                utilisateur.setEmail(data[0]);
                validateMail(data[0]);
                break;
            case FIELD_PASSWORD:
                validatePasswords(data[0], data[1]);
                utilisateur.setPass(data[0]);
                break;
            case FIELD_LASTNAME:
                utilisateur.setNom(data[0]);
                validateLastname(data[0]);
                break;
            case FIELD_FIRSTNAME:
                utilisateur.setPrenom(data[0]);
                validateFirstname(data[0]);
                break;
            case FIELD_DATENAISS:
                Date date = validateBirthdate(data[0]);
                utilisateur.setDate(date);
                break;
            default:
            case DATABASE:
            case FIELD_CONFIRMATION:
                break;
        }
    }


    /*
     * Ajoute un message correspondant au champ spécifié à la map des errors.
     */
    private void addError( String champ, String message ) {
        errors.put( champ, message );
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getFieldValue(HttpServletRequest request, String fieldName ) {
        String valeur = request.getParameter( fieldName );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur.trim();
        }
    }
}

package forms;

import beans.Utilisateur;
import sql.SQLConnector;
import sql.UtilisateurTable;
import tools.Util;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static tools.FormTools.*;

public class InscriptionForm {

    private Map<String,String> errors = new HashMap<String, String>();

    public Utilisateur inscrireUtilisateur(HttpServletRequest request ) {
        Utilisateur utilisateur = new Utilisateur(); // initialize user
        InscriptionFields fields = InscriptionFields.FIELD_MAIL; //initialize fields to first field
        for(int i = 0 ; i < InscriptionFields.values().length - 1; i++) { // iterate over fields except the confirmation
            try {
            String currentField = fields.getFieldName();
            String currentFieldValue = getFieldValue(request, currentField);
            if(fields == InscriptionFields.FIELD_PASSWORD) { // if special field who also needs confirmation
                    String confirmationField = InscriptionFields.FIELD_CONFIRMATION.getFieldName();
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

        if(UtilisateurTable.userExistsMail(utilisateur.getEmail())) {
            addError(InscriptionFields.FIELD_MAIL.getFieldName(), "Vous possédez déjà un compte avec cette adresse email.");
        }
        if(UtilisateurTable.userExistsNickname(utilisateur.getPseudo())) {
            addError(InscriptionFields.FIELD_PSEUDO.getFieldName(), "Ce pseudonyme existe déjà.");
        }

        // if there are no errors
        if ( errors.isEmpty() ) {
            // Tries to save the user to the database
            if (!UtilisateurTable.createUser(utilisateur)) {
                addError(Util.GENERIC_DATABASE_FIELD, Util.DATABASE_ERROR_MESSAGE);
            }
        }
    return utilisateur;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    private void validateField(InscriptionFields field, Utilisateur utilisateur, String... data) throws Exception{
        switch(field) {
            case FIELD_MAIL:
                utilisateur.setEmail(data[0]);
                validateMail(data[0]);
                break;
            case FIELD_PSEUDO:
                utilisateur.setPseudo(data[0]);
                validateNickname(data[0]);
                validateFieldSize(data[0]);
                break;
            case FIELD_PASSWORD:
                validatePasswords(data[0], data[1]);
                utilisateur.setPass(data[0]);
                break;
            case FIELD_LASTNAME:
                utilisateur.setNom(data[0]);
                validateFieldSize(data[0]);
                validateName(data[0]);
                break;
            case FIELD_FIRSTNAME:
                utilisateur.setPrenom(data[0]);
                validateFieldSize(data[0]);
                validateName(data[0]);
                break;
            case FIELD_DATENAISS:
                Date date = validateBirthdate(data[0]);
                utilisateur.setDate(date);
                break;
            default:
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

}

enum InscriptionFields {
    FIELD_MAIL("email"),
    FIELD_PSEUDO("pseudo"),
    FIELD_PASSWORD("motdepasse"),
    FIELD_CONFIRMATION("confirmation"),
    FIELD_LASTNAME("nom"),
    FIELD_FIRSTNAME("prenom"),
    FIELD_DATENAISS("date_naissance");


    static public final InscriptionFields[] values = values();
    private String fieldName;

    InscriptionFields(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    /**
     * Method used to iterate over the available InscriptionFields
     * This method skips the confirmation field as its treated
     * @return the field after the current InscriptionFields
     */
    public InscriptionFields next() {
        InscriptionFields field = values[(this.ordinal()+1) % values.length];
        if(field == InscriptionFields.FIELD_CONFIRMATION) field = field.next();
        return field;
    }
}
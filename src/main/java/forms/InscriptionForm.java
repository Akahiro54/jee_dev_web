package forms;

import beans.Utilisateur;
import sql.SQLConnector;
import tools.Messages;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static tools.FormulaireValidation.*;

public class InscriptionForm {

    private Map<String,String> errors = new HashMap<String, String>();

    public Utilisateur inscrireUtilisateur(HttpServletRequest request ) {
        Utilisateur utilisateur = new Utilisateur(); // initialize user
        InscriptionFields fields = InscriptionFields.FIELD_MAIL; //initialize fields to first field
        for(int i = 0 ; i < InscriptionFields.values().length - 2; i++) { // iterate over fields except the last (database)
            try {
            String currentField = fields.getFieldName();
            String currentFieldValue = getFieldValue(request, currentField);
//            System.out.println("Current field : " + currentField + ", current field value : " + currentFieldValue);
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
        // if there are no errors
        if ( errors.isEmpty() ) {
            // Tries to save the user to the database
            if (!SQLConnector.getConnection().createUser(utilisateur)) {
                addError(InscriptionFields.DATABASE.getFieldName(), Messages.DATABASE_ERROR_MESSAGE);
            }
        }
    return utilisateur;
    }

    public Map<String, String> getErreurs() {
        return errors;
    }

    private void validateField(InscriptionFields field, Utilisateur utilisateur, String... data) throws Exception{
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
                validateFirstname(data[0]);
                utilisateur.setPrenom(data[0]);
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

}
enum InscriptionFields {
    FIELD_MAIL("email"),
    FIELD_PASSWORD("motdepasse"),
    FIELD_CONFIRMATION("confirmation"),
    FIELD_LASTNAME("nom"),
    FIELD_FIRSTNAME("prenom"),
    DATABASE("database");


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
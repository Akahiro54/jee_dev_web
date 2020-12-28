package forms;

import beans.Utilisateur;
import sql.SQLConnector;
import tools.Messages;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


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

    public Map<String, String> getErrors() {
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
                utilisateur.setPrenom(data[0]);
                validateFirstname(data[0]);
                break;
            case FIELD_DATENAISS:
                Date date = validateBirthdate(data[0]);
                utilisateur.setDate(date);
            default:
            case DATABASE:
            case FIELD_CONFIRMATION:
                break;
        }
    }

    private Date validateBirthdate(String birthdate ) throws Exception {
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(birthdate == null) {
            throw new Exception("Merci d'entrer une date de naissance");
        } else {
            dateFormat.setLenient(false);
            try {
                date = dateFormat.parse(birthdate.trim());
            } catch(ParseException e) {
                throw new Exception("Merci d'entrer une date de naissance valide");
            }
        }
        if(!isAdult(date)) {
            throw new Exception("Il faut être majeur pour pouvoir utiliser l'application");
        }
        return date;
    }

    private boolean isAdult(Date birthdate) {
        Calendar birthCal = Calendar.getInstance(Locale.FRANCE);
        birthCal.setTime(birthdate);
        Calendar todayCal = Calendar.getInstance(Locale.FRANCE);
        todayCal.setTime(new Date());
        int age = todayCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);
        if (    birthCal.get(Calendar.MONTH) > todayCal.get(Calendar.MONTH) ||
                (birthCal.get(Calendar.MONTH) == todayCal.get(Calendar.MONTH) &&
                 birthCal.get(Calendar.DATE) > todayCal.get(Calendar.DATE))) {
            age--;
        }
        System.out.printf("User age : %d\n", age);
        return age >= 18;
    }

    private void validateMail( String email ) throws Exception {
        if ( email != null ) {
            if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
                throw new Exception( "Merci de saisir une adresse mail valide." );
            }
        } else {
            throw new Exception( "Merci de saisir une adresse mail." );
        }
    }

    private void validatePasswords( String motDePasse, String confirmation ) throws Exception {
        if ( motDePasse != null && confirmation != null ) {
            if ( !motDePasse.equals( confirmation ) ) {
                throw new Exception( "Les mots de passe entrés sont différents, merci de les saisir à nouveau." );
            } else if ( motDePasse.length() < 3 ) {
                throw new Exception( "Les mots de passe doivent contenir au moins 3 caractères." );
            }
        } else {
            throw new Exception( "Merci de saisir et confirmer votre mot de passe." );
        }
    }

    private void validateLastname( String nom ) throws Exception {
        if ( nom != null && nom.length() < 3 ) {
            throw new Exception( "Le nom doit contenir au moins 3 caractères." );
        }
    }

    private void validateFirstname( String nom ) throws Exception {
        if ( nom != null && nom.length() < 3 ) {
            throw new Exception( "Le nom doit contenir au moins 3 caractères." );
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
enum InscriptionFields {
    FIELD_MAIL("email"),
    FIELD_PASSWORD("motdepasse"),
    FIELD_CONFIRMATION("confirmation"),
    FIELD_LASTNAME("nom"),
    FIELD_FIRSTNAME("prenom"),
    FIELD_DATENAISS("date_naissance"),
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
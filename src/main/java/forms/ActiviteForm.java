package forms;


import beans.Activite;
import beans.Utilisateur;
import sql.SQLConnector;
import tools.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static tools.FormTools.*;

public class ActiviteForm {

    private Map<String,String> errors = new HashMap<String, String>();


    public Activite ajouterActivite(HttpServletRequest request, int idUser) {
        Activite activite = new Activite(); // initialize activity
        activite.setIdUtilisateur(idUser);
        ActiviteFields fields = ActiviteFields.FIELD_NAME; //initialize fields to first field
        for(int i = 0 ; i < InscriptionFields.values().length; i++) { // iterate over fields
            try {
                String currentField = fields.getFieldName();
                String currentFieldValue = getFieldValue(request, currentField);
                validateField(fields, activite, currentFieldValue);
            } catch (Exception e) {
                addError(fields.getFieldName(), e.getMessage());
            }
            fields = fields.next();
        }
        // if all fields are set correctly, compare dates + times and check
        if(activite.getDateDebut() != null && activite.getHeureDebut() != null && activite.getDateFin() != null && activite.getHeureFin() != null) {
            LocalDateTime debut = LocalDateTime.of(activite.getDateDebut(), activite.getHeureDebut());
            LocalDateTime fin = LocalDateTime.of(activite.getDateFin(), activite.getHeureFin());
            if(debut.isAfter(fin)) {
                addError(ActiviteFields.FIELD_DATE_BEGIN.getFieldName(), "La date de début ne peut être avant la date de fin.");
            } else if(debut.isEqual(fin)) {
                addError(ActiviteFields.FIELD_DATE_BEGIN.getFieldName(), "Les dates de début et de fin ne peuvent être identiques.");
            }
        }

        // if there are no errors
        if ( errors.isEmpty() ) {
//             Tries to save the activity to the database
            if (!SQLConnector.getConnection().createActivity(activite)) {
                addError(Util.GENERIC_DATABASE_FIELD, Util.DATABASE_ERROR_MESSAGE);
            }
        }
        return activite;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    private void validateField(ActiviteFields field, Activite activite, String... data) throws Exception{
        switch(field) {
            case FIELD_NAME:
                activite.setNom(data[0]);
                validateName(data[0]);
                break;
            case FIELD_DATE_BEGIN:
                LocalDate date_begin = validateDate(data[0]);
                activite.setDateDebut(date_begin);
                break;
            case FIELD_DATE_END:
                LocalDate date_end = validateDate(data[0]);
                activite.setDateFin(date_end);
                break;
            case FIELD_TIME_BEGIN:
                LocalTime time_begin = validateTime(data[0]);
                activite.setHeureDebut(time_begin);
                break;
            case FIELD_TIME_END:
                LocalTime time_end = validateTime(data[0]);
                activite.setHeureFin(time_end);
                break;
            case FIELD_PLACE:
                int id = placeExists(data[0]);
                if(id != -1) activite.setIdLieu(id);
                break;
            default:
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

enum ActiviteFields {
    FIELD_NAME("nom_activite"),
    FIELD_DATE_BEGIN("date_debut"),
    FIELD_DATE_END("date_fin"),
    FIELD_TIME_BEGIN("heure_debut"),
    FIELD_TIME_END("heure_fin"),
    FIELD_PLACE("lieu");


    static public final ActiviteFields[] values = values();
    private String fieldName;

    ActiviteFields(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    /**
     * Method used to iterate over the available ActiviteFields
     * This method skips the confirmation field as its treated
     * @return the field after the current ActiviteFields
     */
    public ActiviteFields next() {
        ActiviteFields field = values[(this.ordinal()+1) % values.length];
//        if(field == ActiviteFields.FIELD_CONFIRMATION) field = field.next();
        return field;
    }
}
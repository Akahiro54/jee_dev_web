package forms;

import beans.Lieu;
import sql.SQLConnector;
import tools.Util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static tools.FormTools.*;

public class LieuForm {

    private Map<String,String> errors = new HashMap<String, String>();


    public Lieu ajouterLieu(HttpServletRequest request) {
        Lieu lieu = new Lieu(); // initialize activity
        LieuFields fields = LieuFields.FIELD_NAME; //initialize fields to first field
        for(int i = 0 ; i < LieuFields.values().length; i++) { // iterate over fields
            try {
                String currentField = fields.getFieldName();
                String currentFieldValue = getFieldValue(request, currentField);
                validateField(fields, lieu, currentFieldValue);
            } catch (Exception e) {
                addError(fields.getFieldName(), e.getMessage());
            }
            fields = fields.next();
        }

        if(SQLConnector.getConnection().placeExistsByName(lieu.getNom())) {
            addError(LieuFields.FIELD_NAME.getFieldName(), "Un lieu avec un nom identique existe déjà");
        }
        // if there are no errors
        if ( errors.isEmpty() ) {
        // Tries to save the activity to the database
            if (!SQLConnector.getConnection().createPlace(lieu)) {
                addError(Util.GENERIC_DATABASE_FIELD, Util.DATABASE_ERROR_MESSAGE);
            }
        }
        return lieu;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    private void validateField(LieuFields field, Lieu lieu, String... data) throws Exception{
        switch(field) {
            case FIELD_NAME:
                validateSomeSpecialChars(data[0]);
                lieu.setNom(data[0]);
                validateFieldSize(data[0]);
                break;
            case FIELD_DESCRIPTION:
                validateSomeSpecialChars(data[0]);
                lieu.setDescription(data[0]);
                break;
            case FIELD_ADDRESS:
                validateSomeSpecialChars(data[0]);
                lieu.setAdresse(data[0]);
                validateFieldSize(data[0]);
                break;
            case FIELD_LATITUDE:
                double latitude = validateCoordinates(data[0]);
                lieu.setLatitude(latitude);
                break;
            case FIELD_LONGITUDE:
                double longitude = validateCoordinates(data[0]);
                lieu.setLongitude(longitude);
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

enum LieuFields {
    FIELD_NAME("nom_lieu"),
    FIELD_DESCRIPTION("description_lieu"),
    FIELD_ADDRESS("adresse_lieu"),
    FIELD_LATITUDE("latitude"),
    FIELD_LONGITUDE("longitude");


    static public final LieuFields[] values = values();
    private String fieldName;

    LieuFields(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    /**
     * Method used to iterate over the available LieuFields
     * This method skips the confirmation field as its treated
     * @return the field after the current LieuFields
     */
    public LieuFields next() {
        LieuFields field = values[(this.ordinal()+1) % values.length];
        return field;
    }
}
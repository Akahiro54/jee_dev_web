package forms;

import beans.Utilisateur;
import dao.UtilisateurDAO;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import static tools.FormTools.*;

public class ConnexionForm {

    private Map<String,String> errors = new HashMap<String, String>();
    private UtilisateurDAO utilisateurDAO;

    public ConnexionForm(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    public Utilisateur connecterUtilisateur(HttpServletRequest request) {
        Utilisateur user = new Utilisateur();
        ConnecterFields fields= ConnecterFields.FIELD_MAIL;
            for(int i = 0 ; i < ConnecterFields.values().length ; i++) {
                try {
                    String currentField = fields.getFieldName();
                    String currentFieldValue = getFieldValue(request, currentField);
                    validateField(fields, user, currentFieldValue);
                    fields = fields.next();
                } catch(Exception e) {
                    addError(fields.getFieldName(), e.getMessage());
                }
            }
            if(errors.isEmpty()) {
                if(!utilisateurDAO.canLogin(user)) {
                    addError(ConnecterFields.FIELD_MAIL.getFieldName(), "Connexion impossible : identifiant ou mot de passe incorrect.");
                } else {
                    user = utilisateurDAO.getByEmail(user.getEmail());
                }
            }
        return user;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    private void validateField(ConnecterFields field, Utilisateur utilisateur, String... data) throws Exception{
        switch(field) {
            case FIELD_MAIL:
                validateFieldSize(data[0]);
                validateMail(data[0]);
                utilisateur.setEmail(data[0]);
                break;
            case FIELD_PASSWORD:
                validateFieldSize(data[0]);
                utilisateur.setPass(data[0]);
                break;
            default:
                break;
        }
    }
    private void addError( String champ, String message ) {
        errors.put( champ, message );
    }

}

enum ConnecterFields {
    FIELD_MAIL("email"),
    FIELD_PASSWORD("motdepasse");

    static public final ConnecterFields[] values = values();
    private String fieldName;

    ConnecterFields(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public ConnecterFields next() {
        ConnecterFields field = values[(this.ordinal()+1) % values.length];
        return field;
    }
}
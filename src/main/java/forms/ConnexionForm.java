package forms;

import beans.Utilisateur;
import sql.SQLConnector;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ConnexionForm {

    private static final String CHAMP_EMAIL  = "email";
    private static final String CHAMP_PASS   = "motdepasse";
    private Map<String, String> erreurs = new HashMap<String, String>();
    private Boolean resultat;

    public Utilisateur connecterUtilisateur(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("motdepasse");
        Utilisateur user = new Utilisateur();

        /* Validation du champ email. */
        try {
            validationEmail(email);
        } catch (Exception e) {
            setErreur(CHAMP_EMAIL, e.getMessage());
        }
        user.setEmail(email);

        /* Validation du champ mot de passe. */
        try {
            validationMotDePasse(password);
        } catch (Exception e) {
            setErreur(CHAMP_PASS, e.getMessage());
        }
        user.setPass(password);

        /* Initialisation du résultat global de la validation. */
        if (erreurs.isEmpty()) {
            resultat = true;
        } else {
            resultat = false;
        }

        return user;

    }

    /**
     * Valide l'adresse email saisie.
     */
    private void validationEmail(String email) throws Exception {
        if (email != null && !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" )) {
            throw new Exception("Merci de saisir une adresse mail valide.");
        }
    }

    /**
     * Valide le mot de passe saisi.
     */
    private void validationMotDePasse(String motDePasse) throws Exception {
        if (motDePasse != null) {
            if (motDePasse.length() < 1) {
                throw new Exception("Le mot de passe doit contenir au moins 1 caractère.");
            }
        } else {
            throw new Exception("Merci de saisir votre mot de passe.");
        }
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur(String champ, String message) {
        erreurs.put(champ, message);
    }

    public Boolean getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }


}

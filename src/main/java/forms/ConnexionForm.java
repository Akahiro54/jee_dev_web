package forms;

import beans.Utilisateur;
import sql.SQLConnector;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ConnexionForm {

    public Utilisateur connecterUtilisateur(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("motdepasse");
        Utilisateur user = new Utilisateur();

        user.setEmail(email);
        user.setPass(password);

        return user;

    }

}

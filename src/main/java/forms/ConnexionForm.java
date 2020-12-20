package forms;

import beans.Utilisateur;
import sql.SQLConnector;

import javax.servlet.http.HttpServletRequest;

public class ConnexionForm {



    public boolean connecterUtilisateur(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("motdepasse");
        Utilisateur user = new Utilisateur();
        user.setEmail(email);
        user.setPass(password);
        return SQLConnector.getConnection().login(user);
    }
}

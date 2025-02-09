package tools;

import java.util.Base64;

public class Util {

    public static String ATT_FORM = "form";
    public static String ATT_FORM_USER = "user";
    public static String ATT_FORM_ACTIVITY = "activity";
    public static String ATT_FORM_PLACE = "place";
    public static String ATT_FORM_PLACES = "places";
    public static String ATT_FORM_NOTIFICATIONS = "notifications";


    public static String JSON_ERROR_KEY = "error";
    public static String JSON_SUCCESS_KEY = "success";

    public static String NOTIF_MSG_FRIEND = "Vous avez une nouvelle demande d'ami de la part de ";
    public static String NOTIF_MSG_COVID = "Vous avez été en contact avec une personne positive au COVID-19. Faites vous tester dès que possible !";
    public static String NOTIF_MSG_UNFRIEND = " vous a supprimé de ses amis.";
    public static String MSG_FRIEND_ACCEPT = " a accepté votre demande d'ami";
    public static String MSG_FRIEND_DECLINE = " a refusé votre demande d'ami";

    public static String ATT_SESSION_USER = "sessionUtilisateur";
    public static String ATT_DAO_FACTORY = "daofactory";

    public static String DATABASE_ERROR_MESSAGE = "Impossible de communiquer avec la base de données, merci de réessayer plus tard.";

    public static String GENERIC_DATABASE_FIELD = "database";

    public static String convertUserImage(byte[] imageBytes) {
        if(imageBytes != null) {
            return Base64.getEncoder().encodeToString(imageBytes);
        }
        return "";
    }

}

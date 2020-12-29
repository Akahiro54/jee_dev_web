package tools;

import sql.LieuTable;
import sql.SQLConnector;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormTools {


    public static double validateCoordinates(String data) throws Exception {
        double coordinates = 0;
        if(data != null) {
            try {
                coordinates = Double.parseDouble(data);
            }catch (NumberFormatException nfe) {
                throw new Exception("Merci d'entrer des coordonnées valides");
            }
        }
        return coordinates;
    }

    public static void validateSomeSpecialChars(String data) throws Exception {
        if(data != null) {
            if(!data.matches("[A-Za-z0-9À-ÖØ-öø-ÿ\\s-,]+")) {
                throw new Exception("Le champ ne peut contenir que des lettres, chiffres, accents, espaces, tirets (-) et virgules.");
            }
        }
    }

    public static void validateNickname(String nickname) throws Exception {
        if(nickname != null) {
            if(!nickname.matches("[A-Za-z0-9_-]+")) {
                throw new Exception("Le pseudo ne peut contenir que des lettres, chiffres et tirets (-,_).");
            }
        }
    }
    public static void validateName(String data) throws Exception {
        if(data != null) {
            if(!data.matches("[A-Za-z\\sÀ-ÖØ-öø-ÿ-]+")) {
                throw new Exception("Le champ ne peut contenir que des lettres, accents, espaces et tirets (-).");
            }
        }
    }
//    public static void validateNoSpecialChars(String data) throws Exception {
//        if(data != null) {
//            if(!data.matches("[^\W\s_]+")) {
//                throw new Exception("Le champ ne pas peut contenir d'espaces ni de caractères spéciaux ou accentués.");
//            }
//        }
//    }



    public static LocalDate validateDate(String strDate) throws Exception {
        LocalDate date = null;
        if(strDate == null) throw new Exception("Merci d'entrer une date valide");
        try {
            date = LocalDate.parse(strDate);
        } catch(DateTimeParseException dpe) {
            throw new Exception("Merci d'entrer une date valide");
        }
        return date;
    }

    public static LocalTime validateTime(String strTime) throws Exception {
        LocalTime time = null;
        if(strTime == null) throw new Exception("Merci d'entrer une heure valide");
        try {
            time = LocalTime.parse(strTime);
        } catch(DateTimeParseException dpe) {
            throw new Exception("Merci d'entrer une heure valide");
        }
        return time;
    }

    public static int placeExists(String idPlace) throws Exception {
        int id = -1;
        try {
            id = Integer.parseInt(idPlace);
            if(!LieuTable.placeExistsById(id)) {
                throw new Exception("Merci de choisir un lieu existant.");
            }
        } catch (NumberFormatException nfe) {
            throw new Exception("Merci de choisir un lieu existant.");
        }
        return id;
    }

    public static void validateMail( String email ) throws Exception {
        if ( email != null ) {
            if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
                throw new Exception( "Merci de saisir une adresse mail valide." );
            }
        } else {
            throw new Exception( "Merci de saisir une adresse mail." );
        }
    }

    public static void validatePasswords( String motDePasse, String confirmation ) throws Exception {
        if ( motDePasse != null && confirmation != null ) {
            if ( !motDePasse.equals( confirmation ) ) {
                throw new Exception("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
            } else if ( motDePasse.length() < 3 || motDePasse.length() > 40) {
                throw new Exception("Le mots de passe doit contenir entre 3 et 40 caractères." );
            }
        } else {
            throw new Exception( "Merci de saisir et confirmer votre mot de passe." );
        }
    }


    public static void validateFieldSize(String nom ) throws Exception {
        if(nom == null) throw new Exception("Le champ ne peut être vide.");
        if(nom.length() < 3 || nom.length() > 255) throw new Exception( "Le champ doit contenir entre 3 et 255 caractères.");
    }


    public static Date validateBirthdate(String birthdate ) throws Exception {
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

    private static boolean isAdult(Date birthdate) {
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
//        System.out.printf("User age : %d\n", age);
        return age >= 18;
    }


    public static String getFieldValue(HttpServletRequest request, String fieldName ) {
        String valeur = request.getParameter( fieldName );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur.trim();
        }
    }


}

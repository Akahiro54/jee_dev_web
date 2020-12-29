package tools;

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
            if(!SQLConnector.getConnection().placeExists(id)) {
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
                throw new Exception( "Les mots de passe entrés sont différents, merci de les saisir à nouveau." );
            } else if ( motDePasse.length() < 3 ) {
                throw new Exception( "Les mots de passe doivent contenir au moins 3 caractères." );
            }
        } else {
            throw new Exception( "Merci de saisir et confirmer votre mot de passe." );
        }
    }

    public static void validateName(String nom ) throws Exception {
        if(nom == null) throw new Exception("Le nom ne peut être vide.");
        if(nom.length() < 3) throw new Exception( "Le nom doit contenir au moins 3 caractères.");
    }

    public static void validateFirstname( String prenom ) throws Exception {
        if(prenom == null) throw new Exception("Le prénom ne peut être vide.");
        if (prenom.length() < 3 ) throw new Exception( "Le prénom doit contenir au moins 3 caractères." );
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

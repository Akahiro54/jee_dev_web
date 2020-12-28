package tools;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormulaireValidation {

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

    public static void validateLastname( String nom ) throws Exception {
        if ( nom != null && nom.length() < 3 ) {
            throw new Exception( "Le nom doit contenir au moins 3 caractères." );
        }
    }

    public static void validateFirstname( String nom ) throws Exception {
        if ( nom != null && nom.length() < 3 ) {
            throw new Exception( "Le nom doit contenir au moins 3 caractères." );
        }
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


    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    public static String getFieldValue(HttpServletRequest request, String fieldName ) {
        String valeur = request.getParameter( fieldName );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur.trim();
        }
    }


}

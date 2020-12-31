package beans;

import java.time.LocalDateTime;

public class Notification {

    private int id;
    private String message;
    private LocalDateTime date;
    private EtatNotification etat;
    private int utilisateurSource;
    private int utilisateurDestination;

    public Notification() {
        this.etat = EtatNotification.NON_LUE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public EtatNotification getEtat() {
        return etat;
    }

    public void setEtat(EtatNotification etat) {
        if(etat == null) etat = EtatNotification.NON_LUE;
        this.etat = etat;
    }

    public int getUtilisateurSource() {
        return utilisateurSource;
    }

    public void setUtilisateurSource(int utilisateurSource) {
        this.utilisateurSource = utilisateurSource;
    }

    public int getUtilisateurDestination() {
        return utilisateurDestination;
    }

    public void setUtilisateurDestination(int utilisateurDestination) {
        this.utilisateurDestination = utilisateurDestination;
    }
}
enum EtatNotification {
    LUE("lue"),
    NON_LUE("non_lue"),
    ARCHIVEE("archivee");

    private String etatNotification;

    EtatNotification(String etatNotification) {
        this.etatNotification = etatNotification;
    }
}

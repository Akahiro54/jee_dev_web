package beans;

import tools.Util;

import java.time.LocalDateTime;

public class Notification {

    private int id;
    private String message;
    private LocalDateTime date;
    private EtatNotification etat;
    private TypeNotification type;
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

    public TypeNotification getType() {
        return type;
    }

    public void setType(TypeNotification type) {
        this.type = type;
    }


    public static Notification buildNotification(Utilisateur source, int idDestination, TypeNotification typeNotification) {
        Notification notification = new Notification();
        notification.setUtilisateurSource(source.getId());
        notification.setUtilisateurDestination(idDestination);
        notification.setType(typeNotification);
        switch(typeNotification) {
            case AMI:
                notification.setMessage(Util.NOTIF_MSG_FRIEND + source.getPseudo());
                break;
            case COVID:
                notification.setMessage(Util.NOTIF_MSG_COVID);
                break;
            case DEL_AMI:
                notification.setMessage(source.getPseudo() + Util.NOTIF_MSG_UNFRIEND);
                break;
            case ACC_AMI:
                notification.setMessage(source.getPseudo() + Util.MSG_FRIEND_ACCEPT);
                break;
            case REF_AMI:
                notification.setMessage(source.getPseudo() + Util.MSG_FRIEND_DECLINE);
                break;

        }
        notification.setEtat(EtatNotification.NON_LUE);
        notification.setDate(LocalDateTime.now());
        return notification;
    }
}


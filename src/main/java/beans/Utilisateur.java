package beans;

import java.time.LocalDate;
import java.util.Date;

public class Utilisateur {


    private int id;
    private String email;
    private String pseudo;
    private String pass;
    private String nom;
    private String prenom;
    private Date date;
    private byte[] image;
    private String nomImage;


    private boolean contamine;
    private LocalDate dateContamination;


    public Utilisateur() {}

    public Utilisateur(int id, String nom, String prenom, byte[] image) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.image = image;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getNomImage() {
        return nomImage;
    }

    public void setNomImage(String nomImage) {
        this.nomImage = nomImage;
    }

    public boolean isContamine() {
        return contamine;
    }

    public void setContamine(boolean contamine) {
        this.contamine = contamine;
    }

    public LocalDate getDateContamination() {
        return dateContamination;
    }

    public void setDateContamination(LocalDate dateContamination) {
        this.dateContamination = dateContamination;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", date=" + date +
                '}';
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}

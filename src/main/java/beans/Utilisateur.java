package beans;

import javax.servlet.http.Part;
import java.io.InputStream;
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


    public Utilisateur() {}

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

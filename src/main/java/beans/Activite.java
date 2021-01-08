package beans;

import java.time.LocalDate;
import java.time.LocalTime;

public class Activite implements Comparable<Activite> {

    private int id;
    private int idUtilisateur;
    private String nom;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private int idLieu;

    public Activite() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate fin) {
        this.dateFin = fin;
    }

    public int getIdLieu() {
        return idLieu;
    }

    public void setIdLieu(int idLieu) {
        this.idLieu = idLieu;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    @Override
    public String toString() {
        return "Activite{" +
                "id=" + id +
                ", idUtilisateur=" + idUtilisateur +
                ", nom='" + nom + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", heureDebut=" + heureDebut +
                ", heureFin=" + heureFin +
                ", idLieu=" + idLieu +
                '}';
    }


    @Override
    public int compareTo(Activite o) {
        int diffDebut = this.dateDebut.compareTo(o.getDateDebut());
        int diffFin = this.dateFin.compareTo(o.getDateFin());
        if(diffDebut == 0 && diffFin == 0) { // les dates de début  et de fin sont les memes
            return 1;
        } else if(diffDebut == 0) { // dates de début sont les memes, on retourne les dates de fin
            return diffFin;
        } else { // les dates de debut ne sont pas les memes
            return diffDebut;
        }
    }
}

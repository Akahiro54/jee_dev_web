package beans;

public enum TypeUtilisateur {
    ADMIN("admin"),
    USER("user");

    private String typeUtilisateur;

    TypeUtilisateur(String typeUtilisateur) {
        this.typeUtilisateur = typeUtilisateur;
    }

    public String getTypeNotification() {
        return typeUtilisateur;
    }
}

package beans;

public enum EtatNotification {
    LUE("lue"),
    NON_LUE("non_lue"),
    ARCHIVEE("archivee");

    private String etatNotification;

    EtatNotification(String etatNotification) {
        this.etatNotification = etatNotification;
    }

    public String getEtatNotification() {
        return etatNotification;
    }
}

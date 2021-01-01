package beans;

public enum EtatAmis {
    DEMANDE_ACCEPTEE("demande_acceptee"),
    DEMANDE_EN_ATTENTE("demande_en_attente"),
    DEMANDE_REFUSEE("demande_refusee");

    private String etatAmis;

    EtatAmis(String etatAmis) {
        this.etatAmis = etatAmis;
    }

    public String getEtatAmis() {
        return etatAmis;
    }
}

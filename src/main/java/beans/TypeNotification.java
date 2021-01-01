package beans;

public enum TypeNotification {
    AMI("ami"),
    COVID("covid");

    private String typeNotification;

    TypeNotification(String typeNotification) {
        this.typeNotification = typeNotification;
    }

    public String getTypeNotification() {
        return typeNotification;
    }
}
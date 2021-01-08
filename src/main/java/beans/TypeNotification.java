package beans;

public enum TypeNotification {
    AMI("ami"),
    COVID("covid"),
    DEL_AMI("del_ami"),
    ACC_AMI("acc_ami"),
    REF_AMI("ref_ami");

    private String typeNotification;

    TypeNotification(String typeNotification) {
        this.typeNotification = typeNotification;
    }

    public String getTypeNotification() {
        return typeNotification;
    }
}
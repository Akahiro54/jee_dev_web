package tools;

public enum FormFields {
    FIELD_MAIL("email"),
    FIELD_PASSWORD("motdepasse"),
    FIELD_CONFIRMATION("confirmation"),
    FIELD_LASTNAME("nom"),
    FIELD_FIRSTNAME("prenom"),
    FIELD_DATENAISS("date_naissance"),
    DATABASE("database");


    static public final FormFields[] values = values();
    private String fieldName;

    FormFields(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    /**
     * Method used to iterate over the available FormFields
     * This method skips the confirmation field as its treated
     * @return the field after the current FormFields
     */
    public FormFields next() {
        FormFields field = values[(this.ordinal()+1) % values.length];
        if(field == FormFields.FIELD_CONFIRMATION) field = field.next();
        return field;
    }
}
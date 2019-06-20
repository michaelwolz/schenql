package de.unitrier.dbis.sqlquerybuilder;

public class Field {
    private String fieldName;

    public Field(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getQueryString() {
        return Helper.encloseInApostrophe(this.fieldName);
    }
}

package de.unitrier.dbis.sqlquerybuilder;

public class Field {
    private String fieldName;

    public Field(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Field)) {
            return false;
        }
        Field c = (Field) o;
        return c.fieldName.equals(fieldName);
    }


    public String getQueryString() {
        return fieldName.equals("*") ? fieldName : Helper.encloseInApostrophe(fieldName);
    }
}

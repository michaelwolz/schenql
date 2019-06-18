package de.unitrier.dbis.schenql.sqlquerybuilder;

public class SQLField {
    private String fieldName;

    public SQLField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getQueryString() {
        return SQLHelper.encloseInApostrophe(this.fieldName);
    }
}

package de.unitrier.dbis.sqlquerybuilder;

public class Table {
    private String tableName;

    public Table(String tableName) {
        this.tableName = tableName;
    }

    public String getQueryString() {
        return Helper.encloseInApostrophe(this.tableName);
    }
}

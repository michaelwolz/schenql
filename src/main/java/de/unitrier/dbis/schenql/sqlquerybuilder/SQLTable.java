package de.unitrier.dbis.schenql.sqlquerybuilder;

public class SQLTable {
    private String tableName;

    public SQLTable(String tableName) {
        this.tableName = tableName;
    }

    public String getQueryString() {
        return SQLHelper.encloseInApostrophe(this.tableName);
    }
}

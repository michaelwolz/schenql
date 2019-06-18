package de.unitrier.dbis.schenql.sqlquerybuilder;

public class SQLGroupBy {
    private SQLTable tableName;
    private SQLField fieldName;

    SQLGroupBy(String fieldName) {
        this.fieldName = new SQLField(fieldName);
    }

    SQLGroupBy(String tableName, String fieldName) {
        this.tableName = new SQLTable(tableName);
        this.fieldName = new SQLField(fieldName);
    }

    public String createStatement() {
        return tableName != null ? tableName.getQueryString() + "." + fieldName.getQueryString() : fieldName.getQueryString();
    }
}

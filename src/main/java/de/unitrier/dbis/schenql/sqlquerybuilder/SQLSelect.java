package de.unitrier.dbis.schenql.sqlquerybuilder;

class SQLSelect {
    private SQLTable tableName;
    private SQLField fieldName;
    private String alias;

    SQLSelect(String fieldName) {
        this.fieldName = new SQLField(fieldName);
    }

    SQLSelect(String tableName, String fieldName) {
        this.tableName = new SQLTable(tableName);
        this.fieldName = new SQLField(fieldName);
    }

    SQLSelect(String tableName, String fieldName, String alias) {
        this.tableName = new SQLTable(tableName);
        this.fieldName = new SQLField(fieldName);
        this.alias = alias;
    }

    String getStatement() {
        String stmnt = "";
        if (tableName != null) {
            stmnt += tableName.getQueryString() + ".";
        }
        stmnt += fieldName.getQueryString();
        if (this.alias != null) {
            stmnt += " as `" + alias + "`";
        }
        return stmnt;
    }
}

package de.unitrier.dbis.schenql.compiler.SQLQueryBuilder;

class SQLSelect {
    private String tableName;
    private String fieldName;
    private String alias;

    SQLSelect(String fieldName) {
        this.fieldName = fieldName;
    }

    SQLSelect(String fieldName, String tableName) {
        this.fieldName = fieldName;
        this.tableName = tableName;
    }

    SQLSelect(String fieldName, String tableName, String alias) {
        this.fieldName = fieldName;
        this.tableName = tableName;
        this.alias = alias;
    }

    String getStatement() {
        String stmnt = "";
        if (this.tableName != null) {
            stmnt += "`" + this.tableName + "`.";
        }
        stmnt += "`" + this.fieldName + "`";
        if (this.alias != null) {
            stmnt += " as `" + alias + "`";
        }
        return stmnt;
    }
}

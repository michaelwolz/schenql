package de.unitrier.dbis.schenql.compiler.SQLQueryBuilder;

class SQLFrom {
    private String tableName;
    private String alias;
    private SQLQuery subQuery;

    SQLFrom(String tableName) {
        this.tableName = tableName;
    }

    SQLFrom(String tableName, String alias) {
        this.tableName = tableName;
        this.alias = alias;
    }

    SQLFrom(SQLQuery subQuery, String alias) {
        this.subQuery = subQuery;
        this.alias = alias;
    }

    private String createSubQueryStatement() {
        return "";
    }

    private String createDefaultStatement() {
        return "";
    }

    String getStatement() {
        if (subQuery != null) {
            return createSubQueryStatement();
        } else {
            return createDefaultStatement();
        }
    }
}

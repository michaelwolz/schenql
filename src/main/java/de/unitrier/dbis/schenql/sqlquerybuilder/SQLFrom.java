package de.unitrier.dbis.schenql.sqlquerybuilder;

class SQLFrom {
    private SQLTable tableName;
    private String alias;
    private SQLQuery subQuery;

    public SQLFrom(String tableName) {
        this.tableName = new SQLTable(tableName);
    }

    public SQLFrom(SQLQuery subQuery, String alias) {
        this.subQuery = subQuery;
        this.alias = alias;
    }

    private String createSubQueryStatement() {
        return "(" + subQuery.buildQuery() + ") as " + this.alias;
    }

    private String createDefaultStatement() {
        return alias != null ? tableName.getQueryString() + " as " + alias : tableName.getQueryString();
    }

    String getStatement() {
        if (subQuery != null) {
            return createSubQueryStatement();
        } else {
            return createDefaultStatement();
        }
    }
}

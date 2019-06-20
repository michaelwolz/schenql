package de.unitrier.dbis.sqlquerybuilder;

class From {
    private Table tableName;
    private String alias;
    private Query subQuery;

    public From(String tableName) {
        this.tableName = new Table(tableName);
    }

    public From(Query subQuery, String alias) {
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

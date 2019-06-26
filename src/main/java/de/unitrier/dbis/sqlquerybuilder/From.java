package de.unitrier.dbis.sqlquerybuilder;

import java.util.Objects;

class From {
    private Table tableName;
    private String alias;
    private Query subQuery;

    From(String tableName) {
        this.tableName = new Table(tableName);
    }

    From(Query subQuery, String alias) {
        this.subQuery = subQuery;
        this.subQuery.isSubQuery = true;
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof From)) {
            return false;
        }

        From c = (From) o;
        return Objects.equals(c.tableName, tableName) &&
                Objects.equals(c.alias, alias) &&
                Objects.equals(c.subQuery, subQuery);
    }

    private String createSubQueryStatement() {
        return "(" + subQuery.buildQuery() + ") as " + this.alias;
    }

    private String createDefaultStatement() {
        return alias != null ? tableName.getQueryString() + " as " + alias : tableName.getQueryString();
    }

    String createStatement() {
        if (subQuery != null) {
            return createSubQueryStatement();
        } else {
            return createDefaultStatement();
        }
    }
}

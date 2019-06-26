package de.unitrier.dbis.sqlquerybuilder.condition;

import de.unitrier.dbis.sqlquerybuilder.Query;

import java.util.Objects;

public class SubQueryCondition extends Condition {
    private Query subQuery;

    public SubQueryCondition(String fieldName, Query subQuery) {
        super(fieldName);
        this.subQuery = subQuery;
        this.subQuery.isSubQuery = true;
    }

    public SubQueryCondition(String tableName, String fieldName, Query subQuery) {
        super(tableName, fieldName);
        this.subQuery = subQuery;
        this.subQuery.isSubQuery = true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SubQueryCondition)) {
            return false;
        }

        if (!super.equals(o)) return false;

        SubQueryCondition c = (SubQueryCondition) o;
        return Objects.equals(c.subQuery, subQuery);
    }

    @Override
    public String createStatement() {
        String stmnt = "";
        stmnt += super.createStatement();
        stmnt += " IN (" + subQuery.buildQuery() + ")";
        return stmnt;
    }
}

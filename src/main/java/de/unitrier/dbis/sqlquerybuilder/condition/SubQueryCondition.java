package de.unitrier.dbis.sqlquerybuilder.condition;

import de.unitrier.dbis.sqlquerybuilder.Query;

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
    public String createStatement() {
        String stmnt = "";
        stmnt += super.createStatement();
        stmnt += " IN (" + subQuery.buildQuery() + ")";
        return stmnt;
    }
}

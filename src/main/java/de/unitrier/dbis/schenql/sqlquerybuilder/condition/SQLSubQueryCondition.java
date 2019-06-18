package de.unitrier.dbis.schenql.sqlquerybuilder.condition;

import de.unitrier.dbis.schenql.sqlquerybuilder.SQLQuery;

public class SQLSubQueryCondition extends SQLCondition {
    private SQLQuery subQuery;

    public SQLSubQueryCondition(String fieldName, SQLQuery subQuery) {
        super(fieldName);
        this.subQuery = subQuery;
    }

    public SQLSubQueryCondition(String tableName, String fieldName, SQLQuery subQuery) {
        super(tableName, fieldName);
        this.subQuery = subQuery;
    }

    @Override
    public String createStatement() {
        String stmnt = "";
        stmnt += super.createStatement();
        stmnt += " IN (" + subQuery.buildQuery() + ")";
        return stmnt;
    }
}

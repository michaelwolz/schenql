package de.unitrier.dbis.sqlquerybuilder.condition;

import de.unitrier.dbis.sqlquerybuilder.Field;
import de.unitrier.dbis.sqlquerybuilder.Table;

public abstract class Condition {
    protected Table tableName;
    protected Field fieldName;
    protected boolean or = false;
    protected boolean negate = false;

    Condition() {
    }

    Condition(String fieldName) {
        this.fieldName = new Field(fieldName);
    }

    Condition(String tableName, String fieldName) {
        this.tableName = new Table(tableName);
        this.fieldName = new Field(fieldName);
    }

    public void or() {
        or = true;
    }

    public void negate() {
        negate = true;
    }

    public String createStatement() {
        String stmnt = "";
        if (tableName != null) {
            stmnt += tableName.getQueryString() + "." + fieldName.getQueryString();
        } else {
            stmnt += fieldName.getQueryString();
        }
        return stmnt;
    }
}

package de.unitrier.dbis.sqlquerybuilder.condition;

import de.unitrier.dbis.sqlquerybuilder.Field;
import de.unitrier.dbis.sqlquerybuilder.Table;

import java.util.Objects;

public abstract class Condition {
    protected Table tableName;
    protected Field fieldName;
    protected boolean or = false;
    boolean negate = false;

    Condition() {
    }

    Condition(String fieldName) {
        this.fieldName = new Field(fieldName);
    }

    Condition(String tableName, String fieldName) {
        this.tableName = new Table(tableName);
        this.fieldName = new Field(fieldName);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Condition)) {
            return false;
        }

        Condition c = (Condition) o;
        return Objects.equals(c.tableName, tableName) &&
                Objects.equals(c.fieldName, fieldName) &&
                c.or == or &&
                c.negate == negate;
    }

    public void or() {
        or = true;
    }

    public void negate() {
        negate = true;
    }

    public String getFieldName() {
        return fieldName.toString();
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

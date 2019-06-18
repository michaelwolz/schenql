package de.unitrier.dbis.schenql.sqlquerybuilder.condition;

import de.unitrier.dbis.schenql.sqlquerybuilder.SQLField;
import de.unitrier.dbis.schenql.sqlquerybuilder.SQLTable;

public abstract class SQLCondition {
    protected SQLTable tableName;
    protected SQLField fieldName;
    public boolean or = false;
    public boolean negate = false;

    SQLCondition(String fieldName) {
        this.fieldName = new SQLField(fieldName);
    }

    SQLCondition(String tableName, String fieldName) {
        this.tableName = new SQLTable(tableName);
        this.fieldName = new SQLField(fieldName);
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

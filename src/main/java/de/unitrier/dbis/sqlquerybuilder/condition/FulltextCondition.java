package de.unitrier.dbis.sqlquerybuilder.condition;

import java.util.StringJoiner;

public class FulltextCondition extends Condition {
    String against;

    public FulltextCondition(String fieldName, String against) {
        super(fieldName);
    }

    public FulltextCondition(String tableName, String fieldName, String against) {
        super(tableName, fieldName);
    }

    @Override
    public String createStatement() {
        StringJoiner stmnt = new StringJoiner(" ");
        stmnt.add("MATCH (" + super.createStatement() + ")");
        stmnt.add("AGAINST (" + against + ")");
        return stmnt.toString();
    }
}

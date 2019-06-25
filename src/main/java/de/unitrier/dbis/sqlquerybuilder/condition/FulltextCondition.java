package de.unitrier.dbis.sqlquerybuilder.condition;

import de.unitrier.dbis.sqlquerybuilder.Helper;

import java.util.StringJoiner;

public class FulltextCondition extends Condition {
    String against;

    public FulltextCondition(String fieldName, String against) {
        super(fieldName);
        this.against = against;
    }

    public FulltextCondition(String tableName, String fieldName, String against) {
        super(tableName, fieldName);
        this.against = against;
    }

    @Override
    public String createStatement() {
        StringJoiner stmnt = new StringJoiner(" ");
        stmnt.add("MATCH (" + super.createStatement() + ")");
        stmnt.add("AGAINST (" + Helper.encloseInQMarks(against) + ")");
        return stmnt.toString();
    }
}

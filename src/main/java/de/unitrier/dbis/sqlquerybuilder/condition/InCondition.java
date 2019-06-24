package de.unitrier.dbis.sqlquerybuilder.condition;

import de.unitrier.dbis.sqlquerybuilder.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

public class InCondition extends Condition {
    ArrayList<String> values = new ArrayList<>();

    public InCondition(String fieldName) {
        super(fieldName);
    }

    public InCondition(String tableName, String fieldName) {
        super(tableName, fieldName);
    }

    public InCondition(String fieldName, String... values) {
        super(fieldName);
        this.values.addAll(Arrays.asList(values));
    }

    public InCondition(String tableName, String fieldName, String... values) {
        super(tableName, fieldName);
        this.values.addAll(Arrays.asList(values));
    }

    public void addValue(String value) {
        values.add(value);
    }

    @Override
    public String createStatement() {
        StringJoiner valueStr = new StringJoiner(",");
        values.forEach(value -> {
            valueStr.add(Helper.encloseInQMarks(value));
        });

        String stmnt = "";
        stmnt += super.createStatement();
        stmnt += " IN (" + valueStr.toString() + ")";
        return stmnt;
    }
}

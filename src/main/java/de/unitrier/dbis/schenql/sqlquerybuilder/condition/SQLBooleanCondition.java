package de.unitrier.dbis.schenql.sqlquerybuilder.condition;

import java.util.StringJoiner;

public class SQLBooleanCondition extends SQLCondition {
    private SQLBooleanOperator operator;
    private SQLConditionValue conditionValue;

    public SQLBooleanCondition(String fieldName, SQLBooleanOperator operator, String conditionValue) {
        super(fieldName);
        this.operator = operator;
        this.conditionValue = new SQLStringConditionValue(conditionValue);
    }

    public SQLBooleanCondition(String tableName, String fieldName,
                        SQLBooleanOperator operator, String conditionValue) {
        super(tableName, fieldName);
        this.operator = operator;
        this.conditionValue = new SQLStringConditionValue(conditionValue);
    }

    public SQLBooleanCondition(String fieldName, SQLBooleanOperator operator, int conditionValue) {
        super(fieldName);
        this.operator = operator;
        this.conditionValue = new SQLNumericConditionValue(conditionValue);
    }

    public SQLBooleanCondition(String tableName, String fieldName,
                               SQLBooleanOperator operator, int conditionValue) {
        super(tableName, fieldName);
        this.operator = operator;
        this.conditionValue = new SQLNumericConditionValue(conditionValue);
    }

    @Override
    public String createStatement() {
        StringJoiner stmnt = new StringJoiner(" ");
        stmnt.add(super.createStatement());
        switch (operator) {
            case LT:
                stmnt.add("<");
                break;
            case LTE:
                stmnt.add("<=");
                break;
            case GT:
                stmnt.add(">");
                break;
            case GTE:
                stmnt.add(">=");
                break;
            case EQUALS:
                stmnt.add("=");
                break;
        }
        stmnt.add(conditionValue.getConditionValue());
        return stmnt.toString();
    }
}

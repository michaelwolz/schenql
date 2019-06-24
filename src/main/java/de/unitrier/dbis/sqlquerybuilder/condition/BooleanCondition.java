package de.unitrier.dbis.sqlquerybuilder.condition;

import java.util.StringJoiner;

public class BooleanCondition extends Condition {
    private BooleanOperator operator;
    private ConditionValue conditionValue;

    public BooleanCondition(String fieldName) {
        super(fieldName);
    }

    public BooleanCondition(String tableName, String fieldName) {
        super(tableName, fieldName);
    }

    public BooleanCondition(String fieldName, BooleanOperator operator, String conditionValue) {
        super(fieldName);
        this.operator = operator;
        this.conditionValue = new StringConditionValue(conditionValue);
    }

    public BooleanCondition(String tableName, String fieldName,
                            BooleanOperator operator, String conditionValue) {
        super(tableName, fieldName);
        this.operator = operator;
        this.conditionValue = new StringConditionValue(conditionValue);
    }

    public BooleanCondition(String fieldName, BooleanOperator operator, int conditionValue) {
        super(fieldName);
        this.operator = operator;
        this.conditionValue = new NumericConditionValue(conditionValue);
    }

    public BooleanCondition(String tableName, String fieldName,
                            BooleanOperator operator, int conditionValue) {
        super(tableName, fieldName);
        this.operator = operator;
        this.conditionValue = new NumericConditionValue(conditionValue);
    }

    public void setOperator(BooleanOperator operator) {
        this.operator = operator;
    }

    public void setConditionValue(String condValue) {
        this.conditionValue = new StringConditionValue(condValue);
    }

    public void setConditionValue(int condValue) {
        this.conditionValue = new NumericConditionValue(condValue);
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
            case LIKE:
                stmnt.add("LIKE");
                break;
            case SOUNDSLIKE:
                stmnt.add("SOUNDS LIKE");
                break;
        }
        stmnt.add(conditionValue.getConditionValue());
        return stmnt.toString();
    }
}

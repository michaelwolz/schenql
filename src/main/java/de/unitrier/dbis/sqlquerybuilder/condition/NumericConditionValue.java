package de.unitrier.dbis.sqlquerybuilder.condition;

public class NumericConditionValue extends ConditionValue {
    private Integer conditionValue;

    public NumericConditionValue(Integer conditionValue) {
        super();
        this.conditionValue = conditionValue;
    }

    @Override
    String getConditionValue() {
        return conditionValue.toString();
    }
}

package de.unitrier.dbis.sqlquerybuilder.condition;

import de.unitrier.dbis.sqlquerybuilder.Helper;

public class StringConditionValue extends ConditionValue {
    private String conditionValue;

    public StringConditionValue(String conditionValue) {
        super();
        this.conditionValue = conditionValue;
    }

    @Override
    String getConditionValue() {
        return Helper.encloseInQMarks(conditionValue);
    }
}

package de.unitrier.dbis.schenql.sqlquerybuilder.condition;

import de.unitrier.dbis.schenql.sqlquerybuilder.SQLHelper;

public class SQLNumericConditionValue extends SQLConditionValue {
    private Integer conditionValue;

    public SQLNumericConditionValue(Integer conditionValue) {
        super();
        this.conditionValue = conditionValue;
    }

    @Override
    String getConditionValue() {
        return conditionValue.toString();
    }
}

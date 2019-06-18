package de.unitrier.dbis.schenql.sqlquerybuilder.condition;

import de.unitrier.dbis.schenql.sqlquerybuilder.SQLHelper;

public class SQLStringConditionValue extends SQLConditionValue {
    private String conditionValue;

    public SQLStringConditionValue(String conditionValue) {
        super();
        this.conditionValue = conditionValue;
    }

    @Override
    String getConditionValue() {
        return SQLHelper.encloseInQMarks(conditionValue);
    }
}

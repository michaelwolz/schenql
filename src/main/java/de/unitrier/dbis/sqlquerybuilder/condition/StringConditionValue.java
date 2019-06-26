package de.unitrier.dbis.sqlquerybuilder.condition;

import de.unitrier.dbis.sqlquerybuilder.Helper;

import java.util.Objects;

class StringConditionValue extends ConditionValue {
    private String conditionValue;

    StringConditionValue(String conditionValue) {
        super();
        this.conditionValue = conditionValue;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof  StringConditionValue)) {
            return false;
        }

        StringConditionValue c = (StringConditionValue) o;
        return Objects.equals(c.conditionValue, conditionValue);
    }

    @Override
    String getConditionValue() {
        return Helper.encloseInQMarks(conditionValue);
    }
}

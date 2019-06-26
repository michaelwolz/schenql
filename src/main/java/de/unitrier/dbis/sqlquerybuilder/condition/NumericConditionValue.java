package de.unitrier.dbis.sqlquerybuilder.condition;

import java.util.Objects;

public class NumericConditionValue extends ConditionValue {
    private Integer conditionValue;

    NumericConditionValue(Integer conditionValue) {
        super();
        this.conditionValue = conditionValue;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof  NumericConditionValue)) {
            return false;
        }

        NumericConditionValue c = (NumericConditionValue) o;
        return Objects.equals(c.conditionValue, conditionValue);
    }

    @Override
    String getConditionValue() {
        return conditionValue.toString();
    }
}

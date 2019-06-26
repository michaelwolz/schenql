package de.unitrier.dbis.sqlquerybuilder;

import java.util.Objects;

abstract class AbstractJoin {
    Field onField;
    Table onTable;

    abstract String createStatement();

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AbstractJoin)) {
            return false;
        }

        AbstractJoin c = (AbstractJoin) o;
        return Objects.equals(c.onTable, onTable) &&
                Objects.equals(c.onField, onField);
    }
}

package de.unitrier.dbis.sqlquerybuilder;

import de.unitrier.dbis.sqlquerybuilder.condition.Condition;

import java.util.Objects;

public class SelectCondition extends Selectable {
    private Condition c;
    private String alias;

    public SelectCondition(Condition condition, String alias) {
        c = condition;
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SelectCondition)) {
            return false;
        }

        SelectCondition c = (SelectCondition) o;

        return Objects.equals(c.c, this.c) &&
                Objects.equals(c.alias, alias);
    }

    @Override
    String createStatement() {
        return "(" + c.createStatement() + ") as " + Helper.encloseInApostrophe(alias);
    }
}

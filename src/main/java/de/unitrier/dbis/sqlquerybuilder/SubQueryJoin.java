package de.unitrier.dbis.sqlquerybuilder;

import java.util.Objects;
import java.util.StringJoiner;

public class SubQueryJoin extends AbstractJoin {
    private Query subQuery;
    private String alias;
    private Field joinField;

    public SubQueryJoin(Query subQuery, String alias, String joinField, String onTable, String onField) {
        this.onTable = new Table(onTable);
        this.onField = new Field(onField);
        this.joinField = new Field(joinField);
        this.subQuery = subQuery;
        this.subQuery.isSubQuery = true;
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SubQueryJoin)) {
            return false;
        }

        if (!super.equals(o)) return false;

        SubQueryJoin c = (SubQueryJoin) o;
        return Objects.equals(c.subQuery, subQuery) &&
                Objects.equals(c.joinField, joinField) &&
                Objects.equals(c.alias, alias);
    }

    @Override
    String createStatement() {
        StringJoiner stmnt = new StringJoiner(" ");
        stmnt.add("(" + subQuery.buildQuery() + ") as " + alias);
        stmnt.add("ON");
        stmnt.add(alias + "." + joinField.getQueryString());
        stmnt.add("=");
        stmnt.add(onTable.getQueryString() + "." + onField.getQueryString());
        return stmnt.toString();
    }
}

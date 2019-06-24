package de.unitrier.dbis.sqlquerybuilder;

import java.util.StringJoiner;

public class SubQueryJoin extends AbstractJoin {
    Query subQuery;
    String alias;
    Field joinField;

    public SubQueryJoin(Query subQuery, String alias, String joinField, String onTable, String onField) {
        this.onTable = new Table(onTable);
        this.onField = new Field(onField);
        this.joinField = new Field(joinField);
        this.subQuery = subQuery;
        this.alias = alias;
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

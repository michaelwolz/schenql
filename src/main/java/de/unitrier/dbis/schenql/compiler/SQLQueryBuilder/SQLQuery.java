package de.unitrier.dbis.schenql.compiler.SQLQueryBuilder;

import java.util.ArrayList;
import java.util.StringJoiner;

public class SQLQuery {
    private ArrayList<SQLSelect> select;
    private ArrayList<SQLFrom> from;
    private ArrayList<SQLJoin> joins;
    private ArrayList<SQLCondition> conditions;
    private ArrayList<SQLGroupBy> groupBy;
    private ArrayList<SQLOrderBy> orderBy;
    private int limit;

    public void addFrom() {
    }

    public void addJoin() {
    }

    public void addCondition() {
    }


    public void addGroupBy() {
    }

    public void addOrderBy() {
    }

    private String createSelectFields() {
        StringJoiner selectString = new StringJoiner(", ");
        for (SQLSelect selectField : select) {
            selectString.add(selectField.getStatement());
        }
        return selectString.toString();
    }

    private String createFrom() {
        StringJoiner fromString = new StringJoiner(", ");
        for (SQLFrom fromTable : from) {
            fromString.add(fromTable.getStatement());
        }
        return fromString.toString();
    }

    public String createQuery() {
        StringJoiner query = new StringJoiner(" ");
        query.add("SELECT");
        query.add(createSelectFields());
        query.add(createFrom());
        return query.toString();
    }
}

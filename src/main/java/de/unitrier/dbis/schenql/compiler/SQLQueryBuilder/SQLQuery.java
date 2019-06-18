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

    private String createJoins() {
    }

    private String createConditions() {
    }

    private String createGroupBy() {
    }

    private String createOrderBy() {
    }

    private String createLimit() {
        return "LIMIT " + this.limit;
    }

    public String buildQuery() {
        StringJoiner query = new StringJoiner(" ");
        query.add("SELECT");
        query.add(createSelectFields());
        query.add(createFrom());
        query.add(createJoins());
        query.add(createConditions());
        query.add(createGroupBy());
        query.add(createOrderBy());
        query.add(createLimit());
        return query.toString() + ";";
    }
}

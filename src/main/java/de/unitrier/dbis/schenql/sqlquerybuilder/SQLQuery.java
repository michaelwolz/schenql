package de.unitrier.dbis.schenql.sqlquerybuilder;

import de.unitrier.dbis.schenql.sqlquerybuilder.condition.*;

import java.util.ArrayList;
import java.util.StringJoiner;

public class SQLQuery {
    private ArrayList<SQLSelect> select = new ArrayList<>();
    private ArrayList<SQLFrom> from = new ArrayList<>();
    private ArrayList<SQLJoin> joins = new ArrayList<>();
    private ArrayList<SQLCondition> conditions = new ArrayList<>();
    private ArrayList<SQLGroupBy> groupBy = new ArrayList<>();
    private ArrayList<SQLOrderBy> orderBy = new ArrayList<>();
    private int limit = 0;

    public void addSelect(String selectField) {
        select.add(new SQLSelect(selectField));
    }

    public void addSelect(String tableName, String selectField) {
        select.add(new SQLSelect(tableName, selectField));
    }

    public void addFrom(String fromTable) {
        from.add(new SQLFrom(fromTable));
    }

    public void addFrom(SQLQuery subQuery, String alias) {
        from.add(new SQLFrom(subQuery, alias));
    }

    public void addJoin(String joinTable, String joinField, String onTable, String onField) {
        joins.add(new SQLJoin(joinTable, joinField, onTable, onField));
    }

    public void addCondition(SQLBooleanCondition condition) {
        conditions.add(condition);
    }

    public void addGroupBy(String groupByField) {
        groupBy.add(new SQLGroupBy(groupByField));
    }

    public void addGroupBy(String groupByTable, String groupByField) {
        groupBy.add(new SQLGroupBy(groupByTable, groupByField));
    }

    public void addOrderBy(String orderByField) {
        orderBy.add(new SQLOrderBy(orderByField));
    }

    public void addOrderBy(String orderByTable, String orderByField) {
        orderBy.add(new SQLOrderBy(orderByTable, orderByField));
    }

    public void addLimit(int limit) {
        this.limit = limit;
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
        StringJoiner joinString = new StringJoiner(" ");
        for (SQLJoin join : joins) {
            joinString.add("JOIN");
            joinString.add(join.createStatement());
        }
        return joinString.toString();
    }

    private String createConditions() {
        StringJoiner condString = new StringJoiner(" ");
        for (int i = 0; i < conditions.size(); i++) {
            if (i > 0) {
                SQLCondition cond = conditions.get(i);
                condString.add(cond.or ? "OR" : "AND");
                if (cond.negate) {
                    condString.add("NOT");
                }
            }
            condString.add(conditions.get(i).createStatement());
        }
        return condString.toString();
    }

    private String createGroupBy() {
        StringJoiner groupByString = new StringJoiner(", ");
        for (SQLGroupBy groupByField : groupBy) {
            groupByString.add(groupByField.createStatement());
        }
        return groupByString.toString();
    }

    private String createOrderBy() {
        StringJoiner orderByString = new StringJoiner(", ");
        for (SQLOrderBy orderByField : orderBy) {
            orderByString.add(orderByField.createStatement());
        }
        return orderByString.toString();
    }

    public String buildQuery() {
        StringJoiner query = new StringJoiner(" ");
        // SELECT
        query.add("SELECT");
        if (select.size() > 0) {
            query.add(createSelectFields());
        } else {
            query.add("*");
        }

        // FROM
        if (from.size() > 0) {
            query.add("FROM");
            query.add(createFrom());
        }

        // JOINS
        if (joins.size() > 0) {
            query.add(createJoins());
        }

        // CONDITIONS
        if (conditions.size() > 0) {
            query.add("WHERE");
            query.add(createConditions());
        }

        // GROUP BY
        if (groupBy.size() > 0) {
            query.add("GROUP BY");
            query.add(createGroupBy());
        }

        // ORDER BY
        if (orderBy.size() > 0) {
            query.add("ORDER BY");
            query.add(createOrderBy());
        }

        // LIMIT
        if (limit > 0) {
            query.add("LIMIT");
            query.add(Integer.toString(limit));
        }

        return query.toString() + ";";
    }
}

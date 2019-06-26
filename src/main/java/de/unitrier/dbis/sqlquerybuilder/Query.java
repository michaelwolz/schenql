package de.unitrier.dbis.sqlquerybuilder;

import de.unitrier.dbis.sqlquerybuilder.condition.Condition;
import de.unitrier.dbis.sqlquerybuilder.condition.ConditionGroup;

import java.util.ArrayList;
import java.util.StringJoiner;

public class Query {
    private ArrayList<Selectable> select = new ArrayList<>();
    private boolean distinct = false;
    private ArrayList<From> from = new ArrayList<>();
    private ArrayList<AbstractJoin> joins = new ArrayList<>();
    private ArrayList<Condition> conditions = new ArrayList<>();
    private ArrayList<Selectable> groupBy = new ArrayList<>();
    private ArrayList<OrderBy> orderBy = new ArrayList<>();
    private Integer limit;
    public boolean isSubQuery = false;

    public void addSelect(String selectField) {
        Select s = new Select(selectField);
        if (!select.contains(s)) select.add(s);
    }

    public void addSelect(String tableName, String selectField) {
        Select s = new Select(tableName, selectField);
        if (!select.contains(s)) select.add(s);
    }

    public void addSelect(Selectable s) {
        if (!select.contains(s)) select.add(s);
    }

    public void distinct() {
        this.distinct = true;
    }

    public void addFrom(String fromTable) {
        From f = new From(fromTable);
        if (!from.contains(f)) from.add(f);
    }

    public void addFrom(Query subQuery, String alias) {
        From f = new From(subQuery, alias);
        if (!from.contains(f)) from.add(f);
    }

    public void addJoin(SubQueryJoin subQueryJoin) {
        if (!joins.contains(subQueryJoin)) joins.add(subQueryJoin );
    }

    public void addJoin(String joinTable, String joinField, String onTable, String onField) {
        Join j = new Join(joinTable, joinField, onTable, onField);
        if (!joins.contains(j)) joins.add(j);
    }

    public void addCondition(Condition condition) {
        if (!conditions.contains(condition)) conditions.add(condition);
    }

    public void addGroupBy(String groupByField) {
        Selectable gb = new Selectable(groupByField);
        if (!groupBy.contains(gb)) groupBy.add(gb);
    }

    public void addGroupBy(String groupByTable, String groupByField) {
        Selectable gb = new Selectable(groupByTable, groupByField);
        if (!groupBy.contains(gb)) groupBy.add(gb);
    }

    public void addOrderBy(OrderBy orderBy) {
        if (!this.orderBy.contains(orderBy)) this.orderBy.add(orderBy);
    }

    public void addOrderBy(String orderByField) {
        OrderBy ob = new OrderBy(orderByField);
        if (!this.orderBy.contains(ob)) this.orderBy.add(ob);
    }

    public void addOrderBy(String orderByTable, String orderByField) {
        OrderBy ob = new OrderBy(orderByTable, orderByField);
        if (!this.orderBy.contains(ob)) this.orderBy.add(ob);
    }

    public void addLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public boolean selectIsEmpty() {
        return select.size() == 0;
    }

    // Creation

    private String createSelectFields() {
        StringJoiner selectString = new StringJoiner(", ");
        for (Selectable selectField : select) {
            selectString.add(selectField.createStatement());
        }
        return selectString.toString();
    }

    private String createFrom() {
        StringJoiner fromString = new StringJoiner(", ");
        for (From fromTable : from) {
            fromString.add(fromTable.createStatement());
        }
        return fromString.toString();
    }

    private String createJoins() {
        StringJoiner joinString = new StringJoiner(" ");
        for (AbstractJoin join : joins) {
            joinString.add("JOIN");
            joinString.add(join.createStatement());
        }
        return joinString.toString();
    }

    private String createConditions() {
        return ConditionGroup.createGroupedStatement(conditions);
    }

    private String createGroupBy() {
        StringJoiner groupByString = new StringJoiner(", ");
        for (Selectable groupByField : groupBy) {
            groupByString.add(groupByField.createStatement());
        }
        return groupByString.toString();
    }

    private String createOrderBy() {
        StringJoiner orderByString = new StringJoiner(", ");
        for (OrderBy orderByField : orderBy) {
            orderByString.add(orderByField.createStatement());
        }
        return orderByString.toString();
    }

    // Building

    public String buildQuery() {
        StringJoiner query = new StringJoiner(" ");

        // SELECT
        query.add("SELECT");
        if (distinct) query.add("DISTINCT");
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
        if (limit != null && limit > 0) {
            query.add("LIMIT");
            query.add(Integer.toString(limit));
        }

        // End
        if (!isSubQuery) {
            return query.toString() + ";";
        } else {
            return query.toString();
        }
    }
}

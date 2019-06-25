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
        select.add(new Select(selectField));
    }

    public void addSelect(String tableName, String selectField) {
        select.add(new Select(tableName, selectField));
    }

    public void addSelect(Selectable selectField) {
        select.add(selectField);
    }

    public void distinct() {
        this.distinct = true;
    }

    public void addFrom(String fromTable) {
        from.add(new From(fromTable));
    }

    public void addFrom(Query subQuery, String alias) {
        from.add(new From(subQuery, alias));
    }

    public void addJoin(SubQueryJoin subQueryJoin) {
        joins.add(subQueryJoin);
    }

    public void addJoin(String joinTable, String joinField, String onTable, String onField) {
        joins.add(new Join(joinTable, joinField, onTable, onField));
    }

    public void addCondition(Condition condition) {
        conditions.add(condition);
    }

    public void addGroupBy(String groupByField) {
        groupBy.add(new Selectable(groupByField));
    }

    public void addGroupBy(String groupByTable, String groupByField) {
        groupBy.add(new Selectable(groupByTable, groupByField));
    }

    public void addOrderBy(OrderBy orderBy) {
        this.orderBy.add(orderBy);
    }

    public void addOrderBy(String orderByField) {
        orderBy.add(new OrderBy(orderByField));
    }

    public void addOrderBy(String orderByTable, String orderByField) {
        this.orderBy.add(new OrderBy(orderByTable, orderByField));
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

package de.unitrier.dbis.sqlquerybuilder;

import java.util.Objects;

public class OrderBy {
    private Table tableName;
    private Field fieldName;
    private AggregateFunction function;
    private String sortOrder = "ASC";

    public OrderBy(String fieldName) {
        this.fieldName = new Field(fieldName);
    }

    public OrderBy(String tableName, String fieldName) {
        this.tableName = new Table(fieldName);
        this.fieldName = new Field(fieldName);
    }

    public OrderBy(AggregateFunction function) {
        this.function = function;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OrderBy)) {
            return false;
        }

        OrderBy c = (OrderBy) o;
        return Objects.equals(c.tableName, tableName) &&
                Objects.equals(c.fieldName, fieldName) &&
                Objects.equals(c.function, function) &&
                Objects.equals(c.sortOrder, sortOrder);
    }

    public void setFieldName(String fieldName) {
        this.fieldName.setFieldName(fieldName);
    }

    public void setSortOrder(String order) {
        if (order.equals("ASC") || order.equals("DESC")) {
            sortOrder = order;
        } else {
            System.out.println("Warning: " + order + " is not a valid sort order for SQL! - Ignoring that.");
        }
    }

    public String createStatement() {
        String stmnt = "";
        if (function != null) {
            stmnt += function.createStatement();
        } else {
            if (tableName != null) {
                stmnt += tableName.getQueryString() + ".";
            }
            stmnt += fieldName.getQueryString();
        }
        return stmnt + " " + sortOrder;
    }
}

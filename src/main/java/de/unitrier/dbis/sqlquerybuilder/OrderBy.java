package de.unitrier.dbis.sqlquerybuilder;

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

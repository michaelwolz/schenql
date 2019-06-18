package de.unitrier.dbis.schenql.sqlquerybuilder;

public class SQLOrderBy {
    private SQLTable tableName;
    private SQLField fieldName;
    private String sortOrder = "ASC";

    SQLOrderBy(String fieldName) {
        this.fieldName = new SQLField(fieldName);
    }

    SQLOrderBy(String tableName, String fieldName) {
        this.tableName = new SQLTable(tableName);
        this.fieldName = new SQLField(fieldName);
    }

    public void setSortOrder(String order) {
        if (order.equals("ASC") || order.equals("DESC")) {
            sortOrder = order;
        } else {
            System.out.println("Warning: " + order + " is not a valid sort order for SQL! - Ignoring that.");
        }
    }

    public String createStatement() {
        return (tableName != null ?
                tableName.getQueryString() + "." + fieldName.getQueryString() : fieldName.getQueryString()
        ) + " " + sortOrder;
    }
}

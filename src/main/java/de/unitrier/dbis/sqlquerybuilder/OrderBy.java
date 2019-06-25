package de.unitrier.dbis.sqlquerybuilder;

public class OrderBy {
    private Table tableName;
    private Field fieldName;
    private String sortOrder = "ASC";

    public OrderBy(String fieldName) {
        this.fieldName = new Field(fieldName);
    }

    public OrderBy(String tableName, String fieldName) {
        this.tableName = new Table(tableName);
        this.fieldName = new Field(fieldName);
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

package de.unitrier.dbis.sqlquerybuilder;

public class GroupBy {
    private Table tableName;
    private Field fieldName;

    GroupBy(String fieldName) {
        this.fieldName = new Field(fieldName);
    }

    GroupBy(String tableName, String fieldName) {
        this.tableName = new Table(tableName);
        this.fieldName = new Field(fieldName);
    }

    public String createStatement() {
        return tableName != null ? tableName.getQueryString() + "." + fieldName.getQueryString() : fieldName.getQueryString();
    }
}

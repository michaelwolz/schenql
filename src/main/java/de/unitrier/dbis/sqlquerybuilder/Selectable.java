package de.unitrier.dbis.sqlquerybuilder;

public class Selectable {
    private Table tableName;
    private Field fieldName;

    Selectable(String fieldName) {
        this.fieldName = new Field(fieldName);
    }

    Selectable(String tableName, String fieldName) {
        this.tableName = new Table(tableName);
        this.fieldName = new Field(fieldName);
    }

    String createStatement() {
        String stmnt = "";
        if (tableName != null) {
            stmnt += tableName.getQueryString() + ".";
        }
        stmnt += fieldName.getQueryString();
        return stmnt;
    }
}

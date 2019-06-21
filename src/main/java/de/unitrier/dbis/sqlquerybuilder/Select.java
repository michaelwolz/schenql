package de.unitrier.dbis.sqlquerybuilder;

public class Select {
    private Table tableName;
    private Field fieldName;
    private String alias;

    Select(String fieldName) {
        this.fieldName = new Field(fieldName);
    }

    Select(String tableName, String fieldName) {
        this.tableName = new Table(tableName);
        this.fieldName = new Field(fieldName);
    }

    Select(String tableName, String fieldName, String alias) {
        this.tableName = new Table(tableName);
        this.fieldName = new Field(fieldName);
        this.alias = alias;
    }

    String getStatement() {
        String stmnt = "";
        if (tableName != null) {
            stmnt += tableName.getQueryString() + ".";
        }
        stmnt += fieldName.getQueryString();
        if (this.alias != null) {
            stmnt += " as `" + alias + "`";
        }
        return stmnt;
    }
}

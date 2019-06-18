package de.unitrier.dbis.schenql.sqlquerybuilder;

public class SQLJoin {
    private SQLTable joinTable;
    private SQLField joinField;
    private SQLTable onTable;
    private SQLField onField;

    public SQLJoin(String joinTable, String joinField, String onTable, String onField) {
        this.joinTable = new SQLTable(joinTable);
        this.joinField = new SQLField(joinField);
        this.onTable = new SQLTable(onTable);
        this.onField = new SQLField(onField);
    }

    public String createStatement() {
        return joinTable.getQueryString() +
                " ON " +
                joinTable.getQueryString() +
                "." +
                joinField.getQueryString() +
                " = " +
                onTable.getQueryString() +
                "." +
                onField.getQueryString();
    }
}

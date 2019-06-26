package de.unitrier.dbis.sqlquerybuilder;

public class Join extends AbstractJoin {
    private Table joinTable;
    private Field joinField;
    private Table onTable;
    private Field onField;

    Join(String joinTable, String joinField, String onTable, String onField) {
        this.joinTable = new Table(joinTable);
        this.joinField = new Field(joinField);
        this.onTable = new Table(onTable);
        this.onField = new Field(onField);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Join)) {
            return false;
        }

        Join c = (Join) o;
        return c.joinTable.equals(joinTable) &&
                c.joinField.equals(joinField) &&
                c.onTable.equals(onTable) &&
                c.onField.equals(onField);
    }

    @Override
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

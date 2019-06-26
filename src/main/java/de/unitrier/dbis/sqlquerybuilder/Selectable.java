package de.unitrier.dbis.sqlquerybuilder;

class Selectable {
    protected Table tableName;
    protected Field fieldName;

    Selectable(String fieldName) {
        this.fieldName = new Field(fieldName);
    }

    Selectable(String tableName, String fieldName) {
        this.tableName = new Table(tableName);
        this.fieldName = new Field(fieldName);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Selectable)) {
            return false;
        }

        Selectable c = (Selectable) o;
        return c.tableName.equals(tableName) && c.fieldName.equals(fieldName);
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

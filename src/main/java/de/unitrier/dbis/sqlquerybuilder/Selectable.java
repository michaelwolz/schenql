package de.unitrier.dbis.sqlquerybuilder;

class Selectable {
    protected Table tableName;
    protected Field fieldName;

    Selectable() {
    }

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
        return tableName.equals(c.tableName) && fieldName.equals(c.fieldName);
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

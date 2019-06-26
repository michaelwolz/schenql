package de.unitrier.dbis.sqlquerybuilder;

public class Table {
    private String tableName;

    public Table(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Table)) {
            return false;
        }
        Table c = (Table) o;
        return c.tableName.equals(tableName);
    }

    public String getQueryString() {
        return Helper.encloseInApostrophe(this.tableName);
    }
}

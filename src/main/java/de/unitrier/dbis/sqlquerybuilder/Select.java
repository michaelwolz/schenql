package de.unitrier.dbis.sqlquerybuilder;

import java.util.Objects;

public class Select extends Selectable {
    private String alias;

    Select(String fieldName) {
        super(fieldName);
    }

    Select(String tableName, String fieldName) {
        super(tableName, fieldName);
    }

    Select(String tableName, String fieldName, String alias) {
        super(tableName, fieldName);
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Select)) {
            return false;
        }

        Select c = (Select) o;

        return Objects.equals(c.tableName, super.tableName) &&
                Objects.equals(c.fieldName, super.fieldName) &&
                Objects.equals(c.alias, alias);
    }

    @Override
    String createStatement() {
        String stmnt = super.createStatement();
        if (this.alias != null) {
            stmnt += " as `" + Helper.encloseInApostrophe(alias) + "`";
        }
        return stmnt;
    }
}

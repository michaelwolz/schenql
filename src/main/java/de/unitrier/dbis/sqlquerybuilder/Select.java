package de.unitrier.dbis.sqlquerybuilder;

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
    String createStatement() {
        String stmnt = super.createStatement();
        if (this.alias != null) {
            stmnt += " as `" + Helper.encloseInApostrophe(alias) + "`";
        }
        return stmnt;
    }
}

package de.unitrier.dbis.sqlquerybuilder;

public class AggregateFunction extends Selectable {
    private String function;
    private String alias;

    public AggregateFunction(String fieldName, String function) {
        super(fieldName);
        this.function = function;
    }

    public AggregateFunction(String tableName, String fieldName, String function) {
        super(tableName, fieldName);
        this.function = function;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AggregateFunction)) {
            return false;
        }

        AggregateFunction c = (AggregateFunction) o;
        return c.tableName.equals(super.tableName) &&
                c.fieldName.equals(super.fieldName) &&
                c.alias.equals(alias) &&
                c.function.equals(function);
    }

    @Override
    public String createStatement() {
        String stmnt = function + "(" + super.createStatement() + ")";
        if (alias != null) {
            stmnt += " as " + Helper.encloseInApostrophe(alias);
        }
        return stmnt;
    }
}

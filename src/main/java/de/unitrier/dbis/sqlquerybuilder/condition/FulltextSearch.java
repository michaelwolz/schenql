package de.unitrier.dbis.sqlquerybuilder.condition;

public class FulltextSearch extends Condition {
    public FulltextSearch(String fieldName) {
        super(fieldName);
    }

    public FulltextSearch(String tableName, String fieldName) {
        super(tableName, fieldName);
    }

    @Override
    public String createStatement() {
        return null;
    }
}

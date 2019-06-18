package de.unitrier.dbis.schenql.sqlquerybuilder.condition;

public class SQLFulltextSearch extends SQLCondition {
    public SQLFulltextSearch(String fieldName) {
        super(fieldName);
    }

    public SQLFulltextSearch(String tableName, String fieldName) {
        super(tableName, fieldName);
    }

    @Override
    public String createStatement() {
        return null;
    }
}

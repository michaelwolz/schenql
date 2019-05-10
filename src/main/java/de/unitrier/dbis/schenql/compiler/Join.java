package de.unitrier.dbis.schenql.compiler;

public class Join {
    private final String tableName;
    private final String key;
    private final String joinKey;

    public Join(String tableName, String key, String joinKey) {
        this.tableName = tableName;
        this.key = key;
        this.joinKey = joinKey;

    }

    public String getTableName() {
        return tableName;
    }

    public String getKey() {
        return key;
    }

    public String getJoinKey() {
        return joinKey;
    }
}

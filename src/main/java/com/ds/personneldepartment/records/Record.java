package com.ds.personneldepartment.records;

public abstract class Record {
    private final String tableName;
    private final String databasePath;
    private long id = -1;

    public Record(String tableName, String databasePath) {
        this.tableName = tableName;
        this.databasePath = databasePath;
    }

    public Record(String tableName, String databasePath, long id) {
        this.tableName = tableName;
        this.databasePath = databasePath;
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public String getDatabasePath() {
        return databasePath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

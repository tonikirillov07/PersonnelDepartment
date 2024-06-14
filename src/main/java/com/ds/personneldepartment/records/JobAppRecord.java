package com.ds.personneldepartment.records;

public class JobAppRecord extends Record{
    private String app;

    public JobAppRecord(String tableName, String databasePath, String app) {
        super(tableName, databasePath);
        this.app = app;
    }

    public JobAppRecord(String tableName, String databasePath, long id, String app) {
        super(tableName, databasePath, id);
        this.app = app;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}

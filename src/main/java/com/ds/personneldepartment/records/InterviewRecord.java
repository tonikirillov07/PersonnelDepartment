package com.ds.personneldepartment.records;

public class InterviewRecord extends Record{
    private String name;

    public InterviewRecord(String tableName, String databasePath, String name) {
        super(tableName, databasePath);
        this.name = name;
    }

    public InterviewRecord(String tableName, String databasePath, long id, String name) {
        super(tableName, databasePath, id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

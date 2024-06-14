package com.ds.personneldepartment.records;

public class VacanciesRecord extends Record{
    private String name, division, country;

    public VacanciesRecord(String tableName, String databasePath, String name, String division, String country) {
        super(tableName, databasePath);
        this.name = name;
        this.division = division;
        this.country = country;
    }

    public VacanciesRecord(String tableName, String databasePath, long id, String name, String division, String country) {
        super(tableName, databasePath, id);
        this.name = name;
        this.division = division;
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

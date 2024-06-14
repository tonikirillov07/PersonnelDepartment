package com.ds.personneldepartment.records;

public class StaffRecord extends Record{
    private String name, surname, idnp, post, address, telephone, company, division;
    private boolean isPassedLaborLaw, mayBeDismissed;

    public StaffRecord(String tableName, String databasePath, String name, String surname, String idnp, String post, String address, String telephone, String company, String division, boolean isPassedLaborLaw, boolean mayBeDismissed) {
        super(tableName, databasePath);
        this.name = name;
        this.surname = surname;
        this.idnp = idnp;
        this.post = post;
        this.address = address;
        this.telephone = telephone;
        this.company = company;
        this.division = division;
        this.isPassedLaborLaw = isPassedLaborLaw;
        this.mayBeDismissed = mayBeDismissed;
    }

    public StaffRecord(String tableName, String databasePath, long id, String name, String surname, String idnp, String post, String address, String telephone, String company, String division, boolean isPassedLaborLaw, boolean mayBeDismissed) {
        super(tableName, databasePath, id);
        this.name = name;
        this.surname = surname;
        this.idnp = idnp;
        this.post = post;
        this.address = address;
        this.telephone = telephone;
        this.company = company;
        this.division = division;
        this.isPassedLaborLaw = isPassedLaborLaw;
        this.mayBeDismissed = mayBeDismissed;
    }

    public boolean isMayBeDismissed() {
        return mayBeDismissed;
    }

    public void setMayBeDismissed(boolean mayBeDismissed) {
        this.mayBeDismissed = mayBeDismissed;
    }

    public boolean isPassedLaborLaw() {
        return isPassedLaborLaw;
    }

    public void setPassedLaborLaw(boolean passedLaborLaw) {
        isPassedLaborLaw = passedLaborLaw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getIdnp() {
        return idnp;
    }

    public void setIdnp(String idnp) {
        this.idnp = idnp;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }
}

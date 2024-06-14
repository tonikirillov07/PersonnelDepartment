package com.ds.personneldepartment.records;

import com.ds.personneldepartment.database.DatabaseService;
import com.ds.personneldepartment.database.tables.Interviews;
import com.ds.personneldepartment.database.tables.JobApplications;
import com.ds.personneldepartment.database.tables.Staff;
import com.ds.personneldepartment.database.tables.Vacancies;
import com.ds.personneldepartment.utils.dialogs.ErrorDialog;

import java.sql.PreparedStatement;
import java.util.Objects;

public final class RecordsWriter {
    public static void addStaff(StaffRecord staffRecord){
        try {
            String add = "INSERT INTO " + Staff.TABLE_NAME + "(" + Staff.NAME_ROW + "," + Staff.SURNAME_ROW + "," + Staff.IDNP_ROW + "," + Staff.ADDRESS_ROW + "," + Staff.TELEPHONE_ROW + "," + Staff.POST_ROW + "," + Staff.COMPANY_ROW + "," + Staff.DIVISION_ROW + "," + Staff.IS_PASSED_LABOR_LAW_ROW + "," + Staff.MAY_BE_DISMISSED_ROW +") VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(staffRecord.getDatabasePath())).prepareStatement(add);
            preparedStatement.setString(1, staffRecord.getName());
            preparedStatement.setString(2, staffRecord.getSurname());
            preparedStatement.setString(3, staffRecord.getIdnp());
            preparedStatement.setString(4, staffRecord.getAddress());
            preparedStatement.setString(5, staffRecord.getTelephone());
            preparedStatement.setString(6, staffRecord.getPost());
            preparedStatement.setString(7, staffRecord.getCompany());
            preparedStatement.setString(8, staffRecord.getDivision());
            preparedStatement.setBoolean(9, staffRecord.isPassedLaborLaw());
            preparedStatement.setBoolean(10, staffRecord.isMayBeDismissed());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void addVacancies(VacanciesRecord vacanciesRecord){
        try {
            String add = "INSERT INTO " + Vacancies.TABLE_NAME + "(" + Vacancies.NAME_ROW + "," + Vacancies.DIVISION_ROW + "," + Vacancies.COUNTRY_ROW +") VALUES(?,?,?)";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(vacanciesRecord.getDatabasePath())).prepareStatement(add);
            preparedStatement.setString(1, vacanciesRecord.getName());
            preparedStatement.setString(2, vacanciesRecord.getDivision());
            preparedStatement.setString(3, vacanciesRecord.getCountry());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void addJobApplication(JobAppRecord jobAppRecord){
        try {
            String add = "INSERT INTO " + JobApplications.TABLE_NAME + "(" + JobApplications.APP_ROW +") VALUES(?)";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(jobAppRecord.getDatabasePath())).prepareStatement(add);
            preparedStatement.setString(1, jobAppRecord.getApp());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void addInterviews(InterviewRecord interviewRecord){
        try {
            String add = "INSERT INTO " + Interviews.TABLE_NAME + "(" + Interviews.NAME_ROW +") VALUES(?)";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(interviewRecord.getDatabasePath())).prepareStatement(add);
            preparedStatement.setString(1, interviewRecord.getName());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }
}

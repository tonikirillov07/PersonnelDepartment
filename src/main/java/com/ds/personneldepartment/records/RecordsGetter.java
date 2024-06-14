package com.ds.personneldepartment.records;

import com.ds.personneldepartment.Constants;
import com.ds.personneldepartment.database.DatabaseService;
import com.ds.personneldepartment.database.tables.Interviews;
import com.ds.personneldepartment.database.tables.Staff;
import com.ds.personneldepartment.database.tables.Vacancies;
import com.ds.personneldepartment.utils.dialogs.ErrorDialog;
import com.ds.personneldepartment.utils.settings.SettingsManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ds.personneldepartment.Constants.CURRENT_DATABASE_FILE_KEY;
import static com.ds.personneldepartment.database.tables.DefaultTablesConstants.ID_ROW;

public final class RecordsGetter {
    @Contract(pure = true)
    private static @NotNull String getSelectRequest(String tableName){
        return "SELECT * FROM " + tableName + " ORDER BY " + ID_ROW + " ASC";
    }

    public static @Nullable List<StaffRecord> getAllStaffs(){
        try{
            String selectAll = getSelectRequest(Staff.TABLE_NAME);
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY))).prepareStatement(selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<StaffRecord> staffRecords = new ArrayList<>();
            while (resultSet.next()){
                staffRecords.add(new StaffRecord(Staff.TABLE_NAME, SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY), resultSet.getLong(ID_ROW), resultSet.getString(Staff.NAME_ROW), resultSet.getString(Staff.SURNAME_ROW),
                        resultSet.getString(Staff.IDNP_ROW), resultSet.getString(Staff.POST_ROW), resultSet.getString(Staff.ADDRESS_ROW), resultSet.getString(Staff.TELEPHONE_ROW),
                        resultSet.getString(Staff.COMPANY_ROW), resultSet.getString(Staff.DIVISION_ROW), DatabaseService.getBoolean(resultSet.getString(Staff.IS_PASSED_LABOR_LAW_ROW)),
                        DatabaseService.getBoolean(resultSet.getString(Staff.MAY_BE_DISMISSED_ROW))));
            }

            return staffRecords;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public static @NotNull List<String> getAllDivisions(){
        List<String> divisionsList = new ArrayList<>();
        Objects.requireNonNull(getAllStaffs()).forEach(staffRecord -> divisionsList.add(staffRecord.getDivision()));

        return divisionsList;
    }

    public static @NotNull List<StaffRecord> getStaffsByDivision(String division){
        List<StaffRecord> staffRecords = new ArrayList<>();
        Objects.requireNonNull(getAllStaffs()).forEach(staffRecord -> {
            if(staffRecord.getDivision().equals(division))
                staffRecords.add(staffRecord);
        });

        return staffRecords;
    }

    public static @NotNull List<StaffRecord> getAllNotPassedLaborLaw(){
        List<StaffRecord> staffRecords = new ArrayList<>();
        Objects.requireNonNull(getAllStaffs()).forEach(staffRecord -> {
            if(!staffRecord.isPassedLaborLaw())
                staffRecords.add(staffRecord);
        });

        return staffRecords;
    }

    public static @NotNull List<StaffRecord> getAllMayBeDismissed(){
        List<StaffRecord> staffRecords = new ArrayList<>();
        Objects.requireNonNull(getAllStaffs()).forEach(staffRecord -> {
            if(staffRecord.isMayBeDismissed())
                staffRecords.add(staffRecord);
        });

        return staffRecords;
    }

    public static @Nullable List<VacanciesRecord> getAllVacancies(){
        try{
            String selectAll = getSelectRequest(Vacancies.TABLE_NAME);
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY))).prepareStatement(selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<VacanciesRecord> vacanciesRecords = new ArrayList<>();
            while (resultSet.next()){
                vacanciesRecords.add(new VacanciesRecord(Vacancies.TABLE_NAME, SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY), resultSet.getLong(ID_ROW), resultSet.getString(Vacancies.NAME_ROW)));
            }

            return vacanciesRecords;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public static @Nullable List<InterviewRecord> getAllInterviews(){
        try{
            String selectAll = getSelectRequest(Interviews.TABLE_NAME);
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY))).prepareStatement(selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<InterviewRecord> interviewRecords = new ArrayList<>();
            while (resultSet.next()){
                interviewRecords.add(new InterviewRecord(Vacancies.TABLE_NAME, SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY), resultSet.getString(Interviews.NAME_ROW)));
            }

            return interviewRecords;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }
}

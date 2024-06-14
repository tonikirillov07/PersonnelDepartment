package com.ds.personneldepartment.database;

import com.ds.personneldepartment.utils.dialogs.ErrorDialog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

import static com.ds.personneldepartment.database.tables.DefaultTablesConstants.ID_ROW;

public class DatabaseService {
    public static @Nullable Connection getConnection(String databasePath){
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + databasePath);
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public static @Nullable String getValue(String row, long id, String tableName, String databasePath){
        return getValueWithWhereValue(row, ID_ROW, String.valueOf(id), tableName, databasePath);
    }

    public static @Nullable String getValueWithWhereValue(String row, String whereRow, String whereValue, String tableName, String databasePath){
        try {
            String select = "SELECT " + row + " FROM " + tableName + " WHERE " + whereRow + "='" + whereValue + "'";

            PreparedStatement preparedStatement = Objects.requireNonNull(getConnection(databasePath)).prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();
            String returnValue = resultSet.getString(1);

            preparedStatement.close();
            resultSet.close();

            return returnValue;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public static void changeValue(String row, String newValue, long id, String tableName, String databasePath){
        try{
            String change = "UPDATE " + tableName + " SET " + row + "=" + "'" + newValue + "'" + " WHERE " + ID_ROW + "=" + "'" + id + "'";

            PreparedStatement preparedStatement = Objects.requireNonNull(getConnection(databasePath)).prepareStatement(change);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void deleteRecord(long id, String tableName, String databasePath){
        try{
            String delete = "DELETE FROM " + tableName + " WHERE " + ID_ROW + "='" + id + "'";

            PreparedStatement preparedStatement = Objects.requireNonNull(getConnection(databasePath)).prepareStatement(delete);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static boolean getBoolean(@NotNull String value){
        return value.equals("1") | value.equals("true") | value.equals("yes");
    }

    public static void deleteAllRecords(String databasePath, String tableName){
        try{
            String deleteAll = "DELETE FROM " + tableName;
            PreparedStatement preparedStatement = Objects.requireNonNull(getConnection(databasePath)).prepareStatement(deleteAll);
            preparedStatement.executeUpdate();

            preparedStatement.close();

            resetAutoincrement(databasePath, tableName);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private static void resetAutoincrement(String databasePath, String tableName){
        try {
            String resetAI = "DELETE FROM sqlite_sequence WHERE name='" + tableName + "'";
            PreparedStatement preparedStatement = Objects.requireNonNull(getConnection(databasePath)).prepareStatement(resetAI);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }
}

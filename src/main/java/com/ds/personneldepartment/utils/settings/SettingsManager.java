package com.ds.personneldepartment.utils.settings;

import com.ds.personneldepartment.utils.dialogs.ErrorDialog;
import org.jetbrains.annotations.Nullable;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Properties;

public class SettingsManager {
    private static final Properties properties = new Properties();
    private static final String SETTINGS_PATH = "settings.properties";

    static {
        try{
            FileInputStream fileInputStream = new FileInputStream(SETTINGS_PATH);
            properties.load(fileInputStream);
            fileInputStream.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static @Nullable String getValue(String key){
        return (String) properties.get(key);
    }

    public static void changeValue(String key, String newValue){
        try {
            properties.setProperty(key, newValue);

            FileWriter fileOutputStream = new FileWriter(SETTINGS_PATH);
            properties.store(fileOutputStream, null);
            fileOutputStream.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }
}

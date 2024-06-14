package com.ds.personneldepartment.utils;

import com.ds.personneldepartment.Constants;
import com.ds.personneldepartment.Main;
import com.ds.personneldepartment.additionalNodes.AdditionalTextField;
import com.ds.personneldepartment.utils.dialogs.ErrorDialog;
import com.ds.personneldepartment.utils.dialogs.InfoDialog;
import com.ds.personneldepartment.utils.settings.SettingsManager;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Utils {
    public static @NotNull List<AdditionalTextField> getEmptyFieldsFromArray(AdditionalTextField[] textFields){
        List<AdditionalTextField> textInputControlList = new ArrayList<>();

        Arrays.stream(textFields).toList().forEach(textInputControl -> {
            if (textInputControl == null)
                return;

            if (textInputControl.getText().trim().isEmpty())
                textInputControlList.add(textInputControl);
        });

        return textInputControlList;
    }

    @Contract("_ -> new")
    public static @NotNull Image getImage(String path){
        return new Image(Objects.requireNonNull(Main.class.getResourceAsStream(path)));
    }

    public static void applyBackgroundImageToPane(String backgroundPath, @NotNull Pane pane, double backgroundWidth, double backgroundHeight){
        BackgroundImage backgroundImage = new BackgroundImage(getImage(backgroundPath),
                BackgroundRepeat.REPEAT, BackgroundRepeat.ROUND, BackgroundPosition.DEFAULT, new BackgroundSize(backgroundWidth, backgroundHeight, false, false, true, true));
        pane.setBackground(new Background(backgroundImage));
    }

    public static void saveCurrentFile(String pathToSave){
        try {
            File file = new File(pathToSave);

            if(file.exists())
                file.delete();

            File currentDatabase = new File(Objects.requireNonNull(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY)));
            Files.copy(currentDatabase.toPath(), file.getParentFile().toPath().resolve(file.getName()));

            InfoDialog.show("Ваш файл успешно сохранен в " + file.getAbsolutePath());
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private static @NotNull FileChooser createFileChooser(String title, List<FileChooser.ExtensionFilter> extensionFilterList){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(extensionFilterList);

        return fileChooser;
    }

    public static @Nullable File openFileDialog(String title, Stage stage, List<FileChooser.ExtensionFilter> extensionFilterList){
        return createFileChooser(title, extensionFilterList).showOpenDialog(stage);
    }

    public static @Nullable File openSaveDialog(String title, Stage stage, List<FileChooser.ExtensionFilter> extensionFilterList){
        return createFileChooser(title, extensionFilterList).showSaveDialog(stage);
    }

    public static void defaultCategoryMenuItemsAction(@NotNull MenuItem menuItem, @NotNull MenuButton menuButton){
        menuButton.setText(menuItem.getText());
    }

    public static boolean checkPhoneNumber(@NotNull String phoneNumber){
        if(phoneNumber.length() < 10){
            ErrorDialog.show(new Exception("Введите корректный номер телефона. Длина телефона меньше 10"));
            return false;
        }

        if(!phoneNumber.contains("+")){
            ErrorDialog.show(new Exception("Введите корректный номер телефона. Номер не содержит +"));
            return false;
        }

        for (char c : phoneNumber.toCharArray()) {
            if(!Character.isDigit(c) & c != '+'){
                ErrorDialog.show(new Exception("Введите корректный номер телефона. Неподдерживаемые символы"));
                return false;
            }
        }

        return true;
    }
}

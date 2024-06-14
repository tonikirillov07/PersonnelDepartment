package com.ds.personneldepartment;

import com.ds.personneldepartment.pages.MainPage;
import com.ds.personneldepartment.utils.Utils;
import com.ds.personneldepartment.utils.settings.SettingsManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import static com.ds.personneldepartment.Constants.CURRENT_DATABASE_FILE_KEY;
import static com.ds.personneldepartment.utils.Utils.openFileDialog;
import static com.ds.personneldepartment.utils.Utils.openSaveDialog;

public class MainController {
    @FXML
    private VBox mainVbox;

    @FXML
    private MenuBar menuBar;

    @FXML
    void initialize() {
        Utils.applyBackgroundImageToPane("images/background.png", mainVbox, mainVbox.getWidth(), mainVbox.getHeight());

        initMenuBar();
        openMainPage();
    }

    private void openMainPage(){
        MainPage mainPage = new MainPage(null, mainVbox, "Данные");
        mainPage.open();
    }

    private void initMenuBar(){
        VBox.setMargin(menuBar, new Insets(15d, 0d, 0d, 0d));
        menuBar.setPadding(new Insets(5d));

        Menu menuFile = new Menu("Файл");
        initMenuFile(menuFile);

        menuBar.getMenus().add(menuFile);
    }

    private void initMenuFile(@NotNull Menu menuFile){
        MenuItem menuItemSave = new MenuItem("Сохранить");
        MenuItem menuItemLoad = new MenuItem("Загрузить");

        menuItemLoad.setOnAction(actionEvent -> loadOutsideFile());
        menuItemSave.setOnAction(actionEvent -> saveCurrentFile());

        menuFile.getItems().addAll(menuItemSave, menuItemLoad);
    }

    private void saveCurrentFile(){
        File selectedFile = openSaveDialog("Выберет файл для сохранения", getStage(), List.of(new FileChooser.ExtensionFilter("База данных", "*.db*")));
        if(selectedFile != null){
            Utils.saveCurrentFile(selectedFile.getAbsolutePath());
        }
    }

    private void loadOutsideFile(){
        File selectedFile = openFileDialog("Выберет файл", getStage(), List.of(new FileChooser.ExtensionFilter("База данных", "*.db*")));
        if(selectedFile != null){
            SettingsManager.changeValue(CURRENT_DATABASE_FILE_KEY, selectedFile.getAbsolutePath());
        }
    }

    private Stage getStage(){
        return (Stage) mainVbox.getScene().getWindow();
    }

}

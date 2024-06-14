package com.ds.personneldepartment.utils.dialogs;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

import static com.ds.personneldepartment.utils.Utils.getImage;

public class InfoDialog {
    public static void show(String message){
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");
            alert.setHeaderText(message);

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(getImage("images/info.png"));

            alert.showAndWait();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }
}

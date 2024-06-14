package com.ds.farmacy.utils.dialogs;

import com.ds.farmacy.utils.Utils;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class InfoDialog {
    public static void show(String message){
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");
            alert.setHeaderText(message);

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(Utils.getImage("images/info.png"));

            alert.showAndWait();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }
}

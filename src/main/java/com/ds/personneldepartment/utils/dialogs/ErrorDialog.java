package com.ds.personneldepartment.utils.dialogs;

import javafx.scene.control.Alert;
import org.jetbrains.annotations.NotNull;

public final class ErrorDialog {
    public static void show(@NotNull Exception e){
        e.printStackTrace();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("An exception has occurred");
        alert.setHeaderText(e.toString());
        alert.setContentText("Cause: " + e.getCause());

        alert.showAndWait();

    }
}

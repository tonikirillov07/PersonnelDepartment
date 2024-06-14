package com.ds.personneldepartment.additionalNodes;

import com.ds.personneldepartment.Constants;
import com.ds.personneldepartment.Main;
import javafx.scene.control.MenuButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class AdditionalMenuButton extends MenuButton {
    public AdditionalMenuButton(String s) {
        super(s);

        setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.FONT_BOLD_ITALIC_PATH), 16d));
        setTextFill(Color.WHITE);
        getStyleClass().add("menu-button-custom");
    }
}

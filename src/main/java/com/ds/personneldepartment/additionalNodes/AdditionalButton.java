package com.ds.personneldepartment.additionalNodes;

import com.ds.personneldepartment.Constants;
import com.ds.personneldepartment.Main;
import com.ds.personneldepartment.utils.actionsListeners.IOnAction;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

public class AdditionalButton extends Button {
    private final double width;
    private final double height;
    private final Color textColor;
    private final String backgroundColor;

    public AdditionalButton(String s, double width, double height, String color, Color textColor) {
        super(s);
        this.width = width;
        this.height = height;
        this.backgroundColor = color;
        this.textColor = textColor;

        init();
    }

    private void init() {
        setCursor(Cursor.HAND);
        setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.FONT_BOLD_ITALIC_PATH), 14d));
        setPrefSize(width, height);
        setMinSize(width, height);
        getStyleClass().add("button-next");
        setEffect(new DropShadow());

        setBackgroundColor(backgroundColor);
        setTextColor(textColor);

    }

    public void setBackgroundColor(@NotNull String color){
        setStyle(getStyle() + "-fx-background-color: " + color + ";");
    }

    public void setTextColor(@NotNull Color color){
        setTextFill(color);
    }

    public void addAction(IOnAction onAction){
        setOnAction(actionEvent -> {
            onAction.onAction();
        });
    }
}

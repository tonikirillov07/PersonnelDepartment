package com.ds.personneldepartment.additionalNodes;

import com.ds.personneldepartment.Constants;
import com.ds.personneldepartment.Main;
import com.ds.personneldepartment.utils.Animations;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Tile extends VBox {
    private final double width, height;
    public static final double DEFAULT_WIDTH = 606d;
    public static final double DEFAULT_HEIGHT = 645d;
    private Label titleLabel;

    public Tile(double width, double height) {
        this.width = width;
        this.height = height;

        init();
    }

    private void init() {
        setWidth(width);
        setHeight(height);
        setMaxWidth(width);
        getStyleClass().add("tile");
        setAlignment(Pos.TOP_CENTER);
        setEffect(new DropShadow());
    }

    public void addTitle(String title){
        titleLabel = new Label(title);
        titleLabel.getStyleClass().add("header-label");
        titleLabel.setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.FONT_BOLD_ITALIC_PATH), 32));
        VBox.setMargin(titleLabel, new Insets(15, 0, 0,0));

        addChild(titleLabel);
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public void animate(){
        Animations.addTranslateByUpAnimationToNode(this, true);
    }

    public void addChild(Node node){
        getChildren().add(node);
    }
}

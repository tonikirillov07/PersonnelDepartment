package com.ds.personneldepartment.pages;

import com.ds.personneldepartment.additionalNodes.Tile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class Page {
    private final Page prevoiusPage;
    private final VBox contentVbox;
    private final String title;
    private Tile tile;
    private boolean isOpen;

    protected Page(Page prevoiusPage, VBox contentVbox, String title) {
        this.prevoiusPage = prevoiusPage;
        this.contentVbox = contentVbox;
        this.title = title;

        applyDefaultPagePaddings();
    }

    private void applyDefaultPagePaddings(){
        getContentVbox().setPadding(new Insets(0d, 150d, 0d, 150d));
    }

    private void createTile() {
        tile = new Tile(Tile.DEFAULT_WIDTH, Tile.DEFAULT_HEIGHT);
        tile.animate();

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(vBox, Priority.ALWAYS);
        vBox.getChildren().add(tile);

        addNodeToPage(vBox);
    }

    public void reopen(){
        close();
        open();
    }

    public void open(){
        if(prevoiusPage != null)
            prevoiusPage.close();

        createTile();
        getContentVbox().setOnKeyPressed(keyEvent -> {});

        onOpen();
        isOpen = true;
    }

    public void loadDefaultTileSettings(){
        if(getTile() != null) {
            getTile().setSpacing(10d);
            getTile().addTitle(getTitle());
        }else{
            throw new NullPointerException("Tile is null");
        }
    }

    public void close(){
        contentVbox.getChildren().clear();
        isOpen = false;
    }

    public void goToPreviousPage(){
        getPrevoiusPage().open();
    }

    public void addNodeToPage(Node node){
        contentVbox.getChildren().add(node);
    }

    public void addNodeToTile(Node node){
        if(tile != null)
            tile.getChildren().add(node);
        else
            createTile();
    }

    public Stage getStage(){
        return getContentVbox().getScene() != null ? ((Stage) getContentVbox().getScene().getWindow()) : null;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public String getTitle() {
        return title;
    }

    public Page getPrevoiusPage() {
        return prevoiusPage;
    }

    public Tile getTile() {
        return tile;
    }

    public VBox getContentVbox() {
        return contentVbox;
    }

    public abstract void onOpen();
}

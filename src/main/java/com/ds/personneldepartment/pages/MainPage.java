package com.ds.personneldepartment.pages;

import com.ds.personneldepartment.additionalNodes.AdditionalButton;
import com.ds.personneldepartment.additionalNodes.AdditionalMenuButton;
import com.ds.personneldepartment.database.tables.Staff;
import com.ds.personneldepartment.records.RecordsGetter;
import com.ds.personneldepartment.records.StaffRecord;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import static com.ds.personneldepartment.records.RecordsGetter.*;
import static com.ds.personneldepartment.utils.Utils.defaultCategoryMenuItemsAction;

public class MainPage extends Page{
    private TableView tableView;
    private VBox scrollPaneContentVbox;

    public MainPage(Page prevoiusPage, VBox contentVbox, String title) {
        super(prevoiusPage, contentVbox, title);
    }

    @Override
    public void onOpen() {
        loadDefaultTileSettings();

        createScrollPane();
        createCategoryHbox();
        createTableView();
        createButtonsHbox();
    }

    private void createScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        scrollPaneContentVbox = new VBox();
        scrollPaneContentVbox.setAlignment(Pos.TOP_CENTER);
        scrollPane.setContent(scrollPaneContentVbox);

        addNodeToTile(scrollPane);
    }

    private void createCategoryHbox() {
        HBox hBox = new HBox();

        HBox divisionHbox = new HBox();
        divisionHbox.setAlignment(Pos.CENTER_LEFT);
        divisionHbox.setPadding(new Insets(10d));
        HBox.setHgrow(divisionHbox, Priority.ALWAYS);

        AdditionalMenuButton anotherMenuButton = new AdditionalMenuButton("Другое");
        MenuItem vacanciesMenuButton = new MenuItem("Вакантные места");
        MenuItem interviewsMenuButton = new MenuItem("Лица, намеченные на собеседование");
        MenuItem jobApplicationsMenuButton = new MenuItem("Заявление на прием");
        MenuItem notPassedLaborLawMenuButton = new MenuItem("Не прошедшие курс трудового права");
        MenuItem dismissedMenuButton = new MenuItem("Лица, привлекаемые к увольнению");
        anotherMenuButton.getItems().addAll(vacanciesMenuButton, interviewsMenuButton, jobApplicationsMenuButton, notPassedLaborLawMenuButton, dismissedMenuButton);

        AdditionalMenuButton divisionsMenuButton = new AdditionalMenuButton("Подразделения");
        getAllDivisions().forEach(division -> {
            MenuItem menuItem = new MenuItem(division);
            menuItem.setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(menuItem, divisionsMenuButton);
                anotherMenuButton.setText("Другое");

                displayStaffs(RecordsGetter.getStaffsByDivision(menuItem.getText()));
            });

            divisionsMenuButton.getItems().add(menuItem);
        });

        MenuItem allMenuItems = new MenuItem("Все");
        allMenuItems.setOnAction(actionEvent -> {
            defaultCategoryMenuItemsAction(allMenuItems, divisionsMenuButton);
            displayStaffs(Objects.requireNonNull(getAllStaffs()));
        });
        divisionsMenuButton.getItems().add(allMenuItems);

        divisionHbox.getChildren().add(divisionsMenuButton);

        HBox anotherHbox = new HBox();
        anotherHbox.setAlignment(Pos.CENTER_RIGHT);
        anotherHbox.setPadding(new Insets(10d));
        HBox.setHgrow(anotherHbox, Priority.ALWAYS);

        notPassedLaborLawMenuButton.setOnAction(actionEvent -> {
            defaultCategoryMenuItemsAction(notPassedLaborLawMenuButton, anotherMenuButton);
            displayStaffs(getAllNotPassedLaborLaw());
        });
        dismissedMenuButton.setOnAction(actionEvent -> {
            defaultCategoryMenuItemsAction(dismissedMenuButton, anotherMenuButton);
            displayStaffs(getAllMayBeDismissed());
        });

        anotherHbox.getChildren().add(anotherMenuButton);

        hBox.getChildren().addAll(divisionHbox, anotherHbox);
        scrollPaneContentVbox.getChildren().add(hBox);
    }

    private void createTableView() {
        tableView = new TableView();
        VBox.setVgrow(tableView, Priority.ALWAYS);

        scrollPaneContentVbox.getChildren().add(tableView);
        displayStaffs(Objects.requireNonNull(getAllStaffs()));
    }

    private void clearTableView(){
        tableView.getItems().clear();
        tableView.getColumns().clear();
    }

    private void displayStaffs(@NotNull List<StaffRecord> staffRecords){
        clearTableView();

        TableColumn<StaffRecord, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());

        TableColumn<StaffRecord, String> nameColumn = new TableColumn<>("Имя");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<StaffRecord, String> surnameColumn = new TableColumn<>("Фамилия");
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));

        TableColumn<StaffRecord, String> IDNPColumn = new TableColumn<>("IDNP");
        IDNPColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdnp()));

        TableColumn<StaffRecord, String> addressColumn = new TableColumn<>("Адрес");
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));

        TableColumn<StaffRecord, String> telephoneColumn = new TableColumn<>("Телефон");
        telephoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelephone()));

        TableColumn<StaffRecord, String> postColumn = new TableColumn<>("Должность");
        postColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPost()));

        TableColumn<StaffRecord, String> companyColumn = new TableColumn<>("Компания");
        companyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompany()));

        TableColumn<StaffRecord, String> divisionColumn = new TableColumn<>("Подразделение");
        divisionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDivision()));

        tableView.getColumns().addAll(idColumn, nameColumn, surnameColumn, IDNPColumn, addressColumn, telephoneColumn, postColumn, companyColumn, divisionColumn);
        tableView.getItems().addAll(staffRecords);
    }

    private void createButtonsHbox(){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15d);
        VBox.setMargin(hBox, new Insets(15d, 0d, 15d, 0d));

        AdditionalButton addDataButton = new AdditionalButton("Добавить данные", 200, 40, "#3F1560", Color.WHITE);

        hBox.getChildren().add(addDataButton);
        addNodeToTile(hBox);
    }

}

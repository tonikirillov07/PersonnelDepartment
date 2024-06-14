package com.ds.personneldepartment.pages;

import com.ds.personneldepartment.Constants;
import com.ds.personneldepartment.Main;
import com.ds.personneldepartment.additionalNodes.AdditionalButton;
import com.ds.personneldepartment.additionalNodes.AdditionalMenuButton;
import com.ds.personneldepartment.additionalNodes.AdditionalTextField;
import com.ds.personneldepartment.database.tables.Interviews;
import com.ds.personneldepartment.database.tables.JobApplications;
import com.ds.personneldepartment.database.tables.Staff;
import com.ds.personneldepartment.database.tables.Vacancies;
import com.ds.personneldepartment.records.*;
import com.ds.personneldepartment.utils.Utils;
import com.ds.personneldepartment.utils.actionsListeners.IOnAction;
import com.ds.personneldepartment.utils.dialogs.ErrorDialog;
import com.ds.personneldepartment.utils.settings.SettingsManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.ds.personneldepartment.utils.Utils.checkPhoneNumber;
import static com.ds.personneldepartment.utils.Utils.getEmptyFieldsFromArray;

public class AddDataPage extends Page{
    private VBox scrollViewContent;

    protected AddDataPage(Page prevoiusPage, VBox contentVbox, String title) {
        super(prevoiusPage, contentVbox, title);
    }

    @Override
    public void onOpen() {
        loadDefaultTileSettings();

        createScrollPane();
        createCategoryMenuButton();
        createBackButton();
    }

    private void createScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollViewContent = new VBox();
        scrollViewContent.setSpacing(15d);
        scrollViewContent.setPadding(new Insets(15d));
        scrollViewContent.setAlignment(Pos.TOP_CENTER);
        scrollPane.setContent(scrollViewContent);

        addNodeToTile(scrollPane);
    }

    private void createBackButton() {
        AdditionalButton backButton = new AdditionalButton("Назад", 200d, 40d, "#15604E", Color.WHITE);
        VBox.setMargin(backButton, new Insets(0d, 0d, 15d, 0d));
        backButton.setOnAction(actionEvent -> goToPreviousPage());

        scrollViewContent.getChildren().add(backButton);
    }

    private void createJobAppComponents(){
        scrollViewContent.getChildren().clear();

        AdditionalTextField additionalTextFieldApp = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Текст заявки",
                Utils.getImage("images/all_symbols.png"), false);
        scrollViewContent.getChildren().add(additionalTextFieldApp);

        createNextButton(() -> {
            List<AdditionalTextField> additionalTextFields = getEmptyFieldsFromArray(new AdditionalTextField[]{additionalTextFieldApp});
            additionalTextFields.forEach(AdditionalTextField::setError);

            if(!additionalTextFields.isEmpty())
                return;

            RecordsWriter.addJobApplication(new JobAppRecord(JobApplications.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), additionalTextFieldApp.getText()));
            goToPreviousPage();
        });
        createBackButton();
    }

    private void createInterviewsComponents(){
        scrollViewContent.getChildren().clear();

        AdditionalTextField additionalTextFieldName = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Имя намеченного на собеседование",
                Utils.getImage("images/user.png"), false);

        scrollViewContent.getChildren().add(additionalTextFieldName);
        createNextButton(() -> {
            List<AdditionalTextField> additionalTextFields = getEmptyFieldsFromArray(new AdditionalTextField[]{additionalTextFieldName});
            additionalTextFields.forEach(AdditionalTextField::setError);

            if(!additionalTextFields.isEmpty())
                return;

            RecordsWriter.addInterviews(new InterviewRecord(Interviews.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), additionalTextFieldName.getText()));
            goToPreviousPage();
        });
        createBackButton();
    }

    private void createVacanciesComponents(){
        scrollViewContent.getChildren().clear();

        AdditionalTextField additionalTextFieldName = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Название вакансии",
                Utils.getImage("images/all_symbols.png"), false);

        AdditionalTextField additionalTextFieldDivision = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Подразделение",
                Utils.getImage("images/division.png"), false);

        AdditionalTextField additionalTextFieldCountry = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Страна",
                Utils.getImage("images/country.png"), false);

        scrollViewContent.getChildren().addAll(additionalTextFieldName, additionalTextFieldDivision, additionalTextFieldCountry);
        createNextButton(() -> {
            List<AdditionalTextField> additionalTextFields = getEmptyFieldsFromArray(new AdditionalTextField[]{additionalTextFieldName, additionalTextFieldCountry, additionalTextFieldDivision});

            additionalTextFields.forEach(AdditionalTextField::setError);
            if(!additionalTextFields.isEmpty())
                return;

            if(!RecordsGetter.getAllDivisions().contains(additionalTextFieldDivision.getText())){
                ErrorDialog.show(new IllegalArgumentException("Такого подразделения не существует"));
                return;
            }

            RecordsWriter.addVacancies(new VacanciesRecord(Vacancies.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), additionalTextFieldName.getText(),
                    additionalTextFieldDivision.getText(), additionalTextFieldCountry.getText()));

            goToPreviousPage();
        });
        createBackButton();

    }

    private void createStaffComponents(){
        scrollViewContent.getChildren().clear();

        AdditionalTextField additionalTextFieldName = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Имя сотрудника",
                Utils.getImage("images/user.png"), false);

        AdditionalTextField additionalTextFieldNSurname = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Фамилия сотрудника",
                Utils.getImage("images/user.png"), false);

        AdditionalTextField additionalTextFieldIDNP = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "ИДНП",
                Utils.getImage("images/idnp.png"), false);

        AdditionalTextField additionalTextFieldAddress = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Адрес",
                Utils.getImage("images/address.png"), false);

        AdditionalTextField additionalTextFieldTelephone = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Телефон",
                Utils.getImage("images/telephone.png"), false);

        AdditionalTextField additionalTextFieldPost = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Должность",
                Utils.getImage("images/post.png"), false);

        AdditionalTextField additionalTextFieldCompany = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Компания",
                Utils.getImage("images/company.png"), false);

        AdditionalTextField additionalTextFieldDivision = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Подразделение",
                Utils.getImage("images/division.png"), false);

        CheckBox isPassedLaborLawCheckBox = createCheckBox("Прошел курс трудового права");
        CheckBox isMayBeDismissed = createCheckBox("Привлекаем к увольнению");

        scrollViewContent.getChildren().addAll(additionalTextFieldName, additionalTextFieldNSurname, additionalTextFieldIDNP, additionalTextFieldAddress, additionalTextFieldTelephone,
                additionalTextFieldPost, additionalTextFieldCompany, additionalTextFieldDivision, isPassedLaborLawCheckBox, isMayBeDismissed);

        createNextButton(() -> {
            List<AdditionalTextField> additionalTextFields = getEmptyFieldsFromArray(new AdditionalTextField[]{additionalTextFieldName, additionalTextFieldNSurname, additionalTextFieldIDNP, additionalTextFieldAddress, additionalTextFieldTelephone,
                    additionalTextFieldPost, additionalTextFieldCompany, additionalTextFieldDivision});

            additionalTextFields.forEach(AdditionalTextField::setError);
            if(!additionalTextFields.isEmpty())
                return;

            if(!checkPhoneNumber(additionalTextFieldTelephone.getText()))
                return;

            RecordsWriter.addStaff(new StaffRecord(Staff.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), additionalTextFieldName.getText(), additionalTextFieldNSurname.getText(),
                    additionalTextFieldIDNP.getText(), additionalTextFieldAddress.getText(), additionalTextFieldTelephone.getText(), additionalTextFieldPost.getText(), additionalTextFieldCompany.getText(),
                    additionalTextFieldDivision.getText(), isPassedLaborLawCheckBox.isSelected(), isMayBeDismissed.isSelected()));

            goToPreviousPage();
        });
        createBackButton();
    }

    private void createNextButton(IOnAction onAction){
        AdditionalButton nextButton = new AdditionalButton("Далее", 200d, 45d, "#26B592", Color.WHITE);
        nextButton.setOnAction(actionEvent -> onAction.onAction());

        scrollViewContent.getChildren().add(nextButton);
    }

    private @NotNull CheckBox createCheckBox(String text){
        CheckBox checkBox = new CheckBox(text);
        checkBox.setTextFill(Color.WHITE);
        checkBox.setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.FONT_BOLD_ITALIC_PATH), 16d));

        return checkBox;
    }

    private void createCategoryMenuButton() {
        AdditionalMenuButton menuButton = new AdditionalMenuButton("Категория");
        menuButton.setPrefSize(200d, 49d);
        VBox.setMargin(menuButton, new Insets(15d, 0d, 15d, 0d));

        MenuItem staffsMenuItem = new MenuItem("Сотрудники");
        MenuItem vacansiesMenuItem = new MenuItem("Вакансии");
        MenuItem jobAppMenuItem = new MenuItem("Заявки на прием");
        MenuItem interviewsMenuItem = new MenuItem("Лица, намеченные на собеседование");

        staffsMenuItem.setOnAction(actionEvent -> createStaffComponents());
        vacansiesMenuItem.setOnAction(actionEvent -> createVacanciesComponents());
        jobAppMenuItem.setOnAction(actionEvent -> createJobAppComponents());
        interviewsMenuItem.setOnAction(actionEvent -> createInterviewsComponents());

        menuButton.getItems().addAll(staffsMenuItem, vacansiesMenuItem, jobAppMenuItem, interviewsMenuItem);
        scrollViewContent.getChildren().add(menuButton);
    }
}

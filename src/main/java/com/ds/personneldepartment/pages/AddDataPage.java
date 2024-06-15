package com.ds.personneldepartment.pages;

import com.ds.personneldepartment.Constants;
import com.ds.personneldepartment.Main;
import com.ds.personneldepartment.additionalNodes.AdditionalButton;
import com.ds.personneldepartment.additionalNodes.AdditionalMenuButton;
import com.ds.personneldepartment.additionalNodes.AdditionalTextField;
import com.ds.personneldepartment.database.DatabaseService;
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

    private void createDeleteButton(long id, String table){
        AdditionalButton additionalButton = new AdditionalButton("Удалить", 200d, 40d, "#601515", Color.WHITE);
        VBox.setMargin(additionalButton, new Insets(0d, 0d, 15d, 0d));
        additionalButton.setOnAction(actionEvent -> {
            DatabaseService.deleteRecord(id, table, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
            goToPreviousPage();
        });

        scrollViewContent.getChildren().add(additionalButton);
    }

    private void createBackButton() {
        AdditionalButton backButton = new AdditionalButton("Назад", 200d, 40d, "#15604E", Color.WHITE);
        VBox.setMargin(backButton, new Insets(0d, 0d, 15d, 0d));
        backButton.setOnAction(actionEvent -> goToPreviousPage());

        scrollViewContent.getChildren().add(backButton);
    }

    public void createJobAppComponents(JobAppRecord jobAppRecord){
        scrollViewContent.getChildren().clear();

        AdditionalTextField additionalTextFieldApp = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Текст заявки",
                Utils.getImage("images/all_symbols.png"), false);
        scrollViewContent.getChildren().add(additionalTextFieldApp);

        if(jobAppRecord != null){
            additionalTextFieldApp.setText(jobAppRecord.getApp());
        }

        createNextButton(() -> {
            List<AdditionalTextField> additionalTextFields = getEmptyFieldsFromArray(new AdditionalTextField[]{additionalTextFieldApp});
            additionalTextFields.forEach(AdditionalTextField::setError);

            if(!additionalTextFields.isEmpty())
                return;

            if(jobAppRecord == null)
                RecordsWriter.addJobApplication(new JobAppRecord(JobApplications.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), additionalTextFieldApp.getText()));
            else
                DatabaseService.changeValue(JobApplications.APP_ROW, additionalTextFieldApp.getText(), jobAppRecord.getId(), JobApplications.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
            goToPreviousPage();
        });
        createBackButton();

        if(jobAppRecord != null){
            createDeleteButton(jobAppRecord.getId(), jobAppRecord.getTableName());
        }
    }

    public void createInterviewsComponents(InterviewRecord interviewRecord){
        scrollViewContent.getChildren().clear();

        AdditionalTextField additionalTextFieldName = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Имя намеченного на собеседование",
                Utils.getImage("images/user.png"), false);

        scrollViewContent.getChildren().add(additionalTextFieldName);

        if(interviewRecord != null){
            additionalTextFieldName.setText(interviewRecord.getName());
        }

        createNextButton(() -> {
            List<AdditionalTextField> additionalTextFields = getEmptyFieldsFromArray(new AdditionalTextField[]{additionalTextFieldName});
            additionalTextFields.forEach(AdditionalTextField::setError);

            if(!additionalTextFields.isEmpty())
                return;

            if(interviewRecord == null)
                RecordsWriter.addInterviews(new InterviewRecord(Interviews.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), additionalTextFieldName.getText()));
            else
                DatabaseService.changeValue(Interviews.NAME_ROW, additionalTextFieldName.getText(), interviewRecord.getId(), Interviews.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
            goToPreviousPage();
        });
        createBackButton();

        if(interviewRecord != null){
            createDeleteButton(interviewRecord.getId(), interviewRecord.getTableName());
        }
    }

    public void createVacanciesComponents(VacanciesRecord vacanciesRecord){
        scrollViewContent.getChildren().clear();

        AdditionalTextField additionalTextFieldName = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Название вакансии",
                Utils.getImage("images/all_symbols.png"), false);

        AdditionalTextField additionalTextFieldDivision = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Подразделение",
                Utils.getImage("images/division.png"), false);

        AdditionalTextField additionalTextFieldCountry = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "Страна",
                Utils.getImage("images/country.png"), false);

        scrollViewContent.getChildren().addAll(additionalTextFieldName, additionalTextFieldDivision, additionalTextFieldCountry);

        if(vacanciesRecord != null){
            additionalTextFieldName.setText(vacanciesRecord.getName());
            additionalTextFieldDivision.setText(vacanciesRecord.getDivision());
            additionalTextFieldCountry.setText(vacanciesRecord.getCountry());
        }

        createNextButton(() -> {
            List<AdditionalTextField> additionalTextFields = getEmptyFieldsFromArray(new AdditionalTextField[]{additionalTextFieldName, additionalTextFieldCountry, additionalTextFieldDivision});

            additionalTextFields.forEach(AdditionalTextField::setError);
            if(!additionalTextFields.isEmpty())
                return;

            if(!RecordsGetter.getAllDivisions().contains(additionalTextFieldDivision.getText())){
                ErrorDialog.show(new IllegalArgumentException("Такого подразделения не существует"));
                return;
            }

            if(vacanciesRecord == null)
                RecordsWriter.addVacancies(new VacanciesRecord(Vacancies.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), additionalTextFieldName.getText(),
                        additionalTextFieldDivision.getText(), additionalTextFieldCountry.getText()));
            else {
                DatabaseService.changeValue(Vacancies.NAME_ROW, additionalTextFieldName.getText(), vacanciesRecord.getId(), Vacancies.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                DatabaseService.changeValue(Vacancies.DIVISION_ROW, additionalTextFieldDivision.getText(), vacanciesRecord.getId(), Vacancies.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                DatabaseService.changeValue(Vacancies.COUNTRY_ROW, additionalTextFieldCountry.getText(), vacanciesRecord.getId(), Vacancies.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
            }

            goToPreviousPage();
        });
        createBackButton();

        if(vacanciesRecord != null){
            createDeleteButton(vacanciesRecord.getId(), vacanciesRecord.getTableName());
        }
    }

    public void createStaffComponents(StaffRecord staffRecord){
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

        if(staffRecord != null){
            additionalTextFieldName.setText(staffRecord.getName());
            additionalTextFieldNSurname.setText(staffRecord.getSurname());
            additionalTextFieldIDNP.setText(staffRecord.getIdnp());
            additionalTextFieldAddress.setText(staffRecord.getAddress());
            additionalTextFieldTelephone.setText(staffRecord.getTelephone());
            additionalTextFieldPost.setText(staffRecord.getPost());
            additionalTextFieldCompany.setText(staffRecord.getCompany());
            additionalTextFieldDivision.setText(staffRecord.getDivision());

            isPassedLaborLawCheckBox.setSelected(staffRecord.isPassedLaborLaw());
            isMayBeDismissed.setSelected(staffRecord.isMayBeDismissed());
        }

        createNextButton(() -> {
            List<AdditionalTextField> additionalTextFields = getEmptyFieldsFromArray(new AdditionalTextField[]{additionalTextFieldName, additionalTextFieldNSurname, additionalTextFieldIDNP, additionalTextFieldAddress, additionalTextFieldTelephone,
                    additionalTextFieldPost, additionalTextFieldCompany, additionalTextFieldDivision});

            additionalTextFields.forEach(AdditionalTextField::setError);
            if(!additionalTextFields.isEmpty())
                return;

            if(!checkPhoneNumber(additionalTextFieldTelephone.getText()))
                return;

            if(staffRecord == null)
                RecordsWriter.addStaff(new StaffRecord(Staff.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), additionalTextFieldName.getText(), additionalTextFieldNSurname.getText(),
                        additionalTextFieldIDNP.getText(), additionalTextFieldAddress.getText(), additionalTextFieldTelephone.getText(), additionalTextFieldPost.getText(), additionalTextFieldCompany.getText(),
                        additionalTextFieldDivision.getText(), isPassedLaborLawCheckBox.isSelected(), isMayBeDismissed.isSelected()));
            else{
                DatabaseService.changeValue(Staff.NAME_ROW, additionalTextFieldName.getText(), staffRecord.getId(), Staff.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                DatabaseService.changeValue(Staff.SURNAME_ROW, additionalTextFieldNSurname.getText(), staffRecord.getId(), Staff.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                DatabaseService.changeValue(Staff.IDNP_ROW, additionalTextFieldIDNP.getText(), staffRecord.getId(), Staff.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                DatabaseService.changeValue(Staff.ADDRESS_ROW, additionalTextFieldAddress.getText(), staffRecord.getId(), Staff.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                DatabaseService.changeValue(Staff.TELEPHONE_ROW, additionalTextFieldTelephone.getText(), staffRecord.getId(), Staff.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                DatabaseService.changeValue(Staff.POST_ROW, additionalTextFieldPost.getText(), staffRecord.getId(), Staff.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                DatabaseService.changeValue(Staff.COMPANY_ROW, additionalTextFieldCompany.getText(), staffRecord.getId(), Staff.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                DatabaseService.changeValue(Staff.DIVISION_ROW, additionalTextFieldDivision.getText(), staffRecord.getId(), Staff.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                DatabaseService.changeValue(Staff.IS_PASSED_LABOR_LAW_ROW, String.valueOf(isPassedLaborLawCheckBox.isSelected()), staffRecord.getId(), Staff.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                DatabaseService.changeValue(Staff.MAY_BE_DISMISSED_ROW, String.valueOf(isMayBeDismissed.isSelected()), staffRecord.getId(), Staff.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
            }

            goToPreviousPage();
        });
        createBackButton();

        if(staffRecord != null){
            createDeleteButton(staffRecord.getId(), staffRecord.getTableName());
        }
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

        staffsMenuItem.setOnAction(actionEvent -> createStaffComponents(null));
        vacansiesMenuItem.setOnAction(actionEvent -> createVacanciesComponents(null));
        jobAppMenuItem.setOnAction(actionEvent -> createJobAppComponents(null));
        interviewsMenuItem.setOnAction(actionEvent -> createInterviewsComponents(null));

        menuButton.getItems().addAll(staffsMenuItem, vacansiesMenuItem, jobAppMenuItem, interviewsMenuItem);
        scrollViewContent.getChildren().add(menuButton);
    }
}

module com.ds.personneldepartment {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.sql;


    opens com.ds.personneldepartment to javafx.fxml;
    exports com.ds.personneldepartment;
    exports com.ds.personneldepartment.utils;
    opens com.ds.personneldepartment.utils to javafx.fxml;
}
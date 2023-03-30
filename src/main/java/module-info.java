module com.example.kvisko {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.lang3;
    requires java.xml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.kvisko to javafx.fxml;
    exports com.example.kvisko;
    exports com.example.kvisko.database;
    opens com.example.kvisko.database to javafx.fxml;
    exports com.example.kvisko.timer;
    opens com.example.kvisko.timer to javafx.fxml;
    exports com.example.kvisko.controllers;
    opens com.example.kvisko.controllers to javafx.fxml;
    opens com.example.kvisko.quiz to javafx.fxml;
    exports com.example.kvisko.quiz;
}
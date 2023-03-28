module com.example.kvisko {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.lang3;
    requires java.xml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.kvisko to javafx.fxml;
    exports com.example.kvisko;
}
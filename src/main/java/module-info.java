module com.example.kvisko {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.lang3;


    opens com.example.kvisko to javafx.fxml;
    exports com.example.kvisko;
}
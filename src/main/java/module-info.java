module com.example.kvisko {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kvisko to javafx.fxml;
    exports com.example.kvisko;
}
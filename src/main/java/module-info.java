module com.example.clothes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.clothes to javafx.fxml;
    exports com.example.clothes;
}
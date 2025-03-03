module com.example.tryme {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tryme to javafx.fxml;
    exports com.example.tryme;
}
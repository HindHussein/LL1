module com.example.compilerfinalproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.compilerfinalproject to javafx.fxml;
    exports com.example.compilerfinalproject;
}
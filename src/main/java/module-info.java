module com.example.proyecto2_math {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.proyecto2_math to javafx.fxml;
    exports com.example.proyecto2_math;
}
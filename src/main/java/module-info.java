module com.example.proyecto2_math {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.main to javafx.fxml;
    exports com.main;
    exports com.Cliente;
    exports com.EstructurasDatos;
    opens com.Cliente to javafx.fxml;
}
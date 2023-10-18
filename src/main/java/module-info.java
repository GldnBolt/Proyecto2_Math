module com.example.proyecto2_math {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;


    opens com.main to javafx.fxml;
    exports com.main;
    exports com.Cliente;
    exports com.EstructurasDatos;
    opens com.Cliente to javafx.fxml;
}
module com.example.proyecto2_math {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;


    opens com.Servidor to javafx.fxml;
    opens com.Cliente to javafx.fxml;
    exports com.Servidor;
    exports com.Cliente;
    exports com.EstructurasDatos;

}
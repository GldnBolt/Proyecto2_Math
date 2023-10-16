package com.Cliente;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ClienteController {
    @FXML
    public Button botonConectar;
    @FXML
    public TextField puertoField;
    @FXML
    public TextField ipField;

    public void conectarCliente() throws IOException {
        if (!puertoField.getText().isEmpty() & !puertoField.getText().isEmpty()){
            MainCliente.cliente = new Cliente(ipField.getText(), Integer.parseInt(puertoField.getText()));
            MainCliente.abrirMainVentana();
        }
    }
}

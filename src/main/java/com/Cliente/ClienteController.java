package com.Cliente;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ClienteController {
    @FXML
    public Button botonConectar;
    @FXML
    public TextField puertoField;
    @FXML
    public TextField ipField;

    public void conectarCliente(){
        if (!puertoField.getText().isEmpty() & !puertoField.getText().isEmpty()){
            Cliente cliente = new Cliente(ipField.getText(), Integer.parseInt(puertoField.getText()));
        }
    }
}

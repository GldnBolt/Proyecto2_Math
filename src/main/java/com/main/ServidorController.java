package com.main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ServidorController {
    @FXML
    public Button botonIniciar;
    @FXML
    private TextField puertoField;

    public void iniciarServidor() throws IOException {
        if (!puertoField.getText().isEmpty()) {
            MainServidor.servidor = new Servidor(Integer.parseInt(puertoField.getText()));
        }
    }
}

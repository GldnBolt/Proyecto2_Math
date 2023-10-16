package com.main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ServidorController {
    @FXML
    public Button botonIniciar;
    @FXML
    public Label conexionLabel;
    @FXML
    private TextField puertoField;

    public void iniciarServidor() throws IOException {
        if (!puertoField.getText().isEmpty()) {
            try {
                MainServidor.servidor = new Servidor(Integer.parseInt(puertoField.getText()));
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
            puertoField.setEditable(false);
            conexionLabel.setTextFill(Color.GREEN);
        }
    }
}

package com.Servidor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * Clase que inicia la interfaz y crea el servidor
 */
public class ServidorController {
    @FXML
    public Button botonIniciar;
    @FXML
    public Label conexionLabel;
    @FXML
    private TextField puertoField;

    /**
     * Método para iniciar el servidor
     */
    public void iniciarServidor() {
        if (!puertoField.getText().isEmpty() & puertoField.getText().matches("\\d+")) {
            try {
                MainServidor.servidor = new Servidor(Integer.parseInt(puertoField.getText()));
                puertoField.setEditable(false);
                conexionLabel.setText("Esperando conexion de clientes...");
                conexionLabel.setTextFill(Color.GREEN);

            } catch (NumberFormatException e) {
                System.out.println("Error: El puerto ingresado no es un numero valido");
            }
        } else {
            conexionLabel.setText("Puerto invalido");
            conexionLabel.setTextFill(Color.RED);
        }
    }
}

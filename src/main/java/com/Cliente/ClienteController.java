package com.Cliente;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * Clase encargada de controlar la interfaz para actualizar el cliente con la información
 */
public class ClienteController {
    @FXML
    public Button botonConectar;
    @FXML
    public TextField puertoField;
    @FXML
    public TextField ipField;
    @FXML
    public Label ipLabel;
    @FXML
    public Label puertoLabel;
    @FXML
    public TextField usuarioField;

    /**
     * Función que valida y utiliza la información de los text fields para actualizar el cliente
     */
    public void conectarCliente() throws IOException {
        if (!puertoField.getText().isEmpty() & !puertoField.getText().isEmpty() & !usuarioField.getText().isEmpty()){
            try {
                MainCliente.cliente = new Cliente(ipField.getText(), Integer.parseInt(puertoField.getText()), usuarioField.getText());
                new Thread(() -> MainCliente.cliente.recibirMensajes()).start();
                MainCliente.abrirMainVentana();
                ipLabel.setText("");
                puertoLabel.setText("");
                ipField.setEditable(false);
                puertoField.setEditable(false);
                usuarioField.setEditable(false);

            } catch(Exception e) {
                e.printStackTrace();
            }

        } else {
            ipLabel.setText("IP invalida");
            ipLabel.setTextFill(Color.RED);
            puertoLabel.setText("Puerto invalido");
            puertoLabel.setTextFill(Color.RED);
        }
    }
}

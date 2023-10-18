package com.Cliente;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;

/**
 * Clase que controla la interfaz que actualiza la información del cliente
 */
public class ClienteController {
    @FXML
    public Button botonConectar;
    @FXML
    public TextField puertoField;
    @FXML
    public TextField ipField;

    /**
     * Función que utiliza la información de los text fields para actualizar el cliente y abrir la ventana principal
     * @throws IOException Excepción en caso de error al abrir la ventana principal de la aplicación
     */
    public void conectarCliente() throws IOException {
        if (!puertoField.getText().isEmpty() & !puertoField.getText().isEmpty()){
            MainCliente.cliente = new Cliente(ipField.getText(), Integer.parseInt(puertoField.getText()), "1");
            new Thread(() -> {
                MainCliente.cliente.recibirMensajes();
            });
            MainCliente.abrirMainVentana();
        }
    }
}

package com.Cliente;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Clase que controla la interfaz principal de la aplicación
 */
public class MainController {
    @FXML
    public ImageView imagenView;
    @FXML
    public TextField expresionField;
    @FXML
    public TextArea resultadoArea;
    @FXML
    public Button botonEnviar;
    @FXML
    public Button botonFoto;

    /**
     * Función que envía la expresión al servidor por medio del cliente
     */
    public void enviarExpresion(){
        if (!expresionField.getText().isEmpty()){
            MainCliente.cliente.enviarExpresion(expresionField.getText());
            expresionField.clear();
        }
    }

    public void escribirResultado(String resultado){
        resultadoArea.setText(resultado);
    }
}

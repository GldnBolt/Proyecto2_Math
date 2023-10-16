package com.Cliente;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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

    public void enviarExpresion(){
        if (!expresionField.getText().isEmpty()){
            MainCliente.cliente.enviarExpresion(expresionField.getText());
            expresionField.clear();
        }
    }
}

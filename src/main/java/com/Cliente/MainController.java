package com.Cliente;

import com.EstructurasDatos.CapturaImagen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.io.File;
import java.io.IOException;

/**
 * Clase MainController: Clase controladora de la interfaz gráfica.
 * @version 7.3, 26/10/2023
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
    @FXML
    public Button botonHistorial;

    /**
     * Método para enviar la expresión al servidor.
     */
    public void enviarExpresion(){
        if (!expresionField.getText().isEmpty()){
            MainCliente.cliente.enviarExpresion(expresionField.getText());
            expresionField.clear();
        }
    }

    /**
     * Método para escribir el resultado de la expresión en el área de texto.
     * @param resultado Resultado de la expresión.
     */
    public void escribirResultado(String resultado){
        resultadoArea.appendText(resultado + "\n");
    }

    /**
     * Método para abrir la ventana de historial.
     * @throws IOException Excepción de entrada y salida.
     */
    public void abrirHistorial() throws IOException {
        MainCliente.abrirVentanaHistorial();
        MainCliente.historialController.leerCSV();
    }

    /**
     * Método para tomar una foto y procesarla.
     */
    public void tomarFoto(){
        CapturaImagen.inicarTesseract();
        VideoCapture camara = new VideoCapture(0);

        if (!camara.isOpened()) {
            System.err.println("No se puede acceder a la cámara");
        }

        Mat frame = new Mat();
        camara.read(frame);
        String texto = CapturaImagen.procesarImagen(frame);
        camara.release();
        frame.release();
        Image image = new Image(new File("temporal.jpg").toURI().toString());
        imagenView.setImage(image);
        expresionField.setText(texto);
    }
}

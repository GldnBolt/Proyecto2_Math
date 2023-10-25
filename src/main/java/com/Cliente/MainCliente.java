package com.Cliente;

import com.Servidor.MainServidor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Clase que inicia la interfaz y crea el cliente, también abre la ventana principal de la aplicación
 */
public class MainCliente extends Application {
    public static Cliente cliente;
    ClienteController clienteController = new ClienteController();
    static MainController mainController = new MainController();

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainServidor.class.getResource("VentanaCliente.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        clienteController= fxmlLoader.getController();

        primaryStage.setTitle("Cliente");
        primaryStage.setScene(scene);
        primaryStage.resizableProperty().setValue(false);
        primaryStage.show();
    }

    public static void abrirMainVentana() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainServidor.class.getResource("MainVentana.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        mainController = fxmlLoader.getController();

        stage.setTitle("MainArbol Ventana");
        stage.setScene(scene);
        stage.resizableProperty().setValue(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

/**
 * Clase que inicia el cliente, cuenta con las funciones para enviar y recibir información del servidor
 */
class Cliente {
    private String nombreCliente;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Cliente(String ip, int puerto, String nombre) {
        try {
            nombreCliente = nombre;
            socket = new Socket(ip, puerto);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(nombre);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarExpresion(String expression) {
        out.println(this.nombreCliente + ">>" + expression);
    }

    public void recibirMensajes() {
        while (true) {
            try {
                String mensaje = in.readLine();

                if (mensaje != null) {
                    System.out.println("Servidor dice: " + mensaje);
                    MainCliente.mainController.escribirResultado(mensaje);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

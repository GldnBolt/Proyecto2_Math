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
        primaryStage.show();
    }

    public static void abrirMainVentana() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainServidor.class.getResource("MainVentana.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        mainController = fxmlLoader.getController();

        stage.setTitle("MainArbol Ventana");
        stage.setScene(scene);
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
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Cliente(String ip, int puerto, String nombre) { // Añadir un parámetro para el nombre del cliente
        try {
            socket = new Socket(ip, puerto);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(nombre); // Enviar el nombre del cliente al servidor
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarExpresion(String expression) {
        out.println(expression);
    }

    // Añadir este método para recibir y mostrar los mensajes del servidor
    public void recibirMensajes() {
        //new Thread(() -> {
            try {
                String message = in.readLine();
                while ((message) != null) {
                    System.out.println("Servidor dice: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
       // });
    }
}
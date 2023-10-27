package com.Cliente;

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
 * Clase principal que contiene el método main para ejecutar el programa.
 * @version 7.3, 26/10/2023
 */
public class MainCliente extends Application {
    public static Cliente cliente;
    ClienteController clienteController = new ClienteController();
    static MainController mainController = new MainController();
    static HistorialController historialController = new HistorialController();

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainCliente.class.getResource("VentanaCliente.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        clienteController= fxmlLoader.getController();

        primaryStage.setTitle("Cliente");
        primaryStage.setScene(scene);
        primaryStage.resizableProperty().setValue(false);
        primaryStage.show();
    }

    /**
     * Función que abre la ventana principal
     */
    public static void abrirMainVentana() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainCliente.class.getResource("MainVentana.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        mainController = fxmlLoader.getController();

        stage.setTitle("Math");
        stage.setScene(scene);
        stage.resizableProperty().setValue(false);
        stage.show();
    }

    /**
     * Función que abre la ventana del historial
     */
    public static void abrirVentanaHistorial() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainCliente.class.getResource("VentanaHistorial.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        historialController = fxmlLoader.getController();

        stage.setTitle("Historial");
        stage.setScene(scene);
        stage.resizableProperty().setValue(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

/**
 * Clase del cliente, inicia los sockets y cuenta con la funcion de enviar información al servidor
 */
class Cliente {
    private String nombreCliente;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Constructor de la clase Cliente
     * @param ip IP del servidor
     * @param puerto Puerto del servidor
     * @param nombre Nombre del cliente
     */
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

    /**
     * Función que envía la expresión al servidor
     * @param expression
     */
    public void enviarExpresion(String expression) {
        out.println(this.nombreCliente + ">>" + expression);
    }

    public void recibirMensajes() {
        while (true) {
            try {
                String mensaje = in.readLine();

                if (mensaje != null) {
                    System.out.println("Resultado de expresion: " + mensaje);
                    MainCliente.mainController.escribirResultado(mensaje);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

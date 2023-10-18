package com.Servidor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que inicia la interfaz y crea el servidor
 */
public class MainServidor extends Application {
    ServidorController servidorController = new ServidorController();
    public static Servidor servidor;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainServidor.class.getResource("VentanaServidor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        servidorController= fxmlLoader.getController();

        primaryStage.setTitle("Servidor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

/**
 * Clase del servidor, inicia los sockets y cuenta con la funcion de enviar información a los clientes
 */
class Servidor {
    private static Map<String, ManejoClientes> clientes; // Mapa de clientes conectados al servidor

    public Servidor(int puerto) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(puerto)) {
                System.out.println("Servidor en ejecución en el puerto " + puerto);
                clientes = new HashMap<>(); // Inicializar el mapa de clientes
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Cliente conectado: " + clientSocket);
                    ManejoClientes mc = new ManejoClientes(clientSocket); // Crear un objeto ManejoClientes para el nuevo cliente
                    mc.start(); // Iniciar el hilo del cliente
                    clientes.put(mc.getNombre(), mc); // Añadir el cliente al mapa con su nombre como clave
                    System.out.println("Cliente añadido al mapa");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    static void enviarTodos(String message) {
        for (ManejoClientes mc : clientes.values()) {
            mc.enviarMensajes(message);
        }
    }

    static void enviarUno(String nombre, String message) {
        // Buscar el cliente con el nombre dado en el mapa
        ManejoClientes mc = clientes.get(nombre);
        if (mc != null) {
            // Enviar el mensaje al cliente encontrado
            mc.enviarMensajes(message);
        } else {
            // Mostrar un mensaje de error si no se encuentra el cliente
            System.out.println("No se encontró el cliente con el nombre " + nombre);
        }
    }
}

/**
 * Clase que se encarga de aceptar la conexion con los clientes, también se encarga de procesar la información que recibe de estos
 */
class ManejoClientes extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String nombre; // Nombre del cliente

    public ManejoClientes(Socket socket) {
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Manejo de clientes exitoso");

            nombre = in.readLine();
            System.out.println("Nombre del cliente: " + nombre);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                // Procesa el mensaje del cliente y envía la respuesta
                System.out.println("Cliente dice: " + message);
                procesarMensaje(message); // Llamar al método que procesa el mensaje según el destinatario
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void enviarMensajes(String message) {
        out.println(message);
    }

    public String getNombre() {
        return nombre;
    }

    public void procesarMensaje(String message) {
        // Comprobar si el mensaje tiene el formato de enviar a un destinatario específico
        if (message.contains(">>")) {
            // Extraer el nombre del destinatario y el contenido del mensaje
            String[] partes = message.split(">>", 2);
            String destinatario = partes[0].trim();
            String contenido = partes[1].trim();
            // Enviar el mensaje al destinatario usando el método del servidor
            Servidor.enviarUno(destinatario, nombre + " dice: " + contenido);
        } else {
            // Si no tiene destinatario específico, enviar el mensaje a todos los clientes usando el método del servidor
            Servidor.enviarTodos(nombre + " dice: " + message.toUpperCase());
        }
    }
}

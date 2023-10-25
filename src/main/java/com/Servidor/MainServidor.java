package com.Servidor;

import com.EstructurasDatos.MainArbol;
import com.EstructurasDatos.Nodo;
import com.opencsv.CSVWriter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
        primaryStage.resizableProperty().setValue(false);
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
    private static Map<String, ManejoClientes> clientesMapa; // Mapa de clientes conectados al servidor

    public Servidor(int puerto) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(puerto)) {
                System.out.println("Servidor en ejecución en el puerto " + puerto);
                clientesMapa = new HashMap<>();

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Cliente conectado: " + clientSocket);
                    ManejoClientes manejoClientes = new ManejoClientes(clientSocket); // Crear un objeto ManejoClientes para el nuevo cliente
                    manejoClientes.start(); // Iniciar el nuevo hilo del cliente
                    clientesMapa.put(manejoClientes.getNombreCliente(), manejoClientes); // Añadir el cliente al mapa con su nombre como clave
                    System.out.println("Cliente añadido al mapa");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

     public static void enviarTodos(String message) {
        for (ManejoClientes manejoClientes : clientesMapa.values()) {
            manejoClientes.enviarMensajes(message);
        }
    }

    public static void enviarUno(String nombre, String message){
        ManejoClientes manejoClientes = clientesMapa.get(nombre);
        if (manejoClientes != null) {
            manejoClientes.enviarMensajes(message);

        } else {
            System.out.println("No se encontró el cliente: " + nombre);
        }
    }

    public static void escribirCSV(String mensaje, String persona, String fecha, String resultado) throws IOException {
        CSVWriter csvWriter = new CSVWriter(new FileWriter("Historial.csv", true));

        String[] datosNuevos = {persona, mensaje, resultado, fecha};
        csvWriter.writeNext(datosNuevos);
        csvWriter.close();
    }

    public class convertidorPostfijo {
        public static String convertir(String expression) {
            StringBuilder output = new StringBuilder();
            Stack<Character> operators = new Stack<>();

            for (char c : expression.toCharArray()) {
                if (Character.isDigit(c)) {
                    output.append(c);
                } else if (c == '(') {
                    operators.push(c);
                } else if (c == ')') {
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        output.append(operators.pop());
                    }
                    operators.pop();
                } else if (isOperator(c)) {
                    while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
                        output.append(operators.pop());
                    }
                    operators.push(c);
                }
            }

            while (!operators.isEmpty()) {
                output.append(operators.pop());
            }

            return output.toString();
        }

        private static boolean isOperator(char c) {
            return c == '+' || c == '-' || c == '*' || c == '/';
        }

        private static int precedence(char operator) {
            switch (operator) {
                case '+':
                case '-':
                    return 1;
                case '*':
                case '/':
                    return 2;
                default:
                    return 0;
            }
        }
    }

}

/**
 * Clase que se encarga de aceptar la conexion con los clientes, también se encarga de procesar la información que recibe de estos
 */
class ManejoClientes extends Thread {
    private Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;
    private String nombreCliente;

    public ManejoClientes(Socket socket) {
        this.socket = socket;
        try {
            salida = new PrintWriter(socket.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Manejo de clientes exitoso");

            nombreCliente = entrada.readLine();
            System.out.println("Nombre del cliente conectado: " + nombreCliente);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String message;
            while ((message = entrada.readLine()) != null) {
                System.out.println("Cliente dice: " + message);
                procesarMensaje(message);
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
        salida.println(message);
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void procesarMensaje(String message) throws IOException {
        LocalDateTime ahora = LocalDate.now().atStartOfDay();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ahora.format(formato);

        if (message.contains(">>")) {
            // Se extrae el nombre del destinatario antes del >> y el contenido del mensaje después del >>
            String[] partes = message.split(">>", 2);
            String destinatario = partes[0].trim();
            String contenido = partes[1].trim();
            String expresionPostfija = Servidor.convertidorPostfijo.convertir(contenido);
            String[] tokens = expresionPostfija.split(" ");
            Nodo raiz = MainArbol.construirArbol(tokens);
            int resultado = MainArbol.evaluarArbol(raiz);

            Servidor.escribirCSV(contenido, destinatario, String.valueOf(ahora), String.valueOf(resultado));
            Servidor.enviarUno(destinatario, nombreCliente + " dice: " + resultado);
        } else {
            Servidor.enviarTodos(nombreCliente + " dice: " + message);
            Servidor.escribirCSV(message, "Desconocido", String.valueOf(ahora), "resultado");
        }
    }
}



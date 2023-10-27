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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Clase principal que contiene el método main para ejecutar el programa.
 * @version 7.3, 26/10/2023
 */
public class MainServidor extends Application {
    ServidorController servidorController = new ServidorController();
    static Servidor servidor;

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
 * Clase que se encarga de aceptar la conexion con los clientes, también se encarga de procesar la información que recibe de estos
 */
class Servidor {
    private static Map<String, ManejoClientes> clientesMapa; // Mapa de clientes conectados al servidor

    /**
     * Constructor de la clase Servidor
     * @param puerto Puerto en el que se va a ejecutar el servidor
     */
    public Servidor(int puerto) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(puerto)) {
                System.out.println("Servidor en ejecución en el puerto " + puerto);
                clientesMapa = new HashMap<>();

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    ManejoClientes manejoClientes = new ManejoClientes(clientSocket);
                    manejoClientes.start();
                    clientesMapa.put(manejoClientes.getNombreCliente(), manejoClientes);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Método para enviar un mensaje a un cliente específico
     * @param nombre Nombre del cliente a quien se le quiere enviar el mensaje
     * @param message Mensaje a enviar
     */
    public static void enviarUno(String nombre, String message){
        ManejoClientes manejoClientes = clientesMapa.get(nombre);
        if (manejoClientes != null) {
            manejoClientes.enviarMensajes(message);

        } else {
            System.out.println("No se encontró el cliente: " + nombre);
        }
    }

    /**
     * Método para escribir en un archivo CSV
     * @param mensaje Mensaje que se va a escribir en el archivo
     * @param persona Nombre de la persona que envio el mensaje
     * @param fecha Fecha en la que se envio el mensaje
     * @param resultado Resultado de la operacion aritmetica
     * @throws IOException
     */
    public static void escribirCSV(String mensaje, String persona, String fecha, String resultado) throws IOException {
        CSVWriter csvWriter = new CSVWriter(new FileWriter("Historial.csv", true));

        String[] datosNuevos = {persona, mensaje, resultado, fecha};
        csvWriter.writeNext(datosNuevos);
        csvWriter.close();
    }

    /**
     * Método para convertir una expresión infija a postfija
     * @param expresion Expresión infija
     * @return Expresión postfija
     */
    public static String convertirPostfijo(String expresion) {
        StringBuilder salida = new StringBuilder();
        Stack<String> operadores = new Stack<>();
        StringBuilder numero = new StringBuilder();

        for (int i = 0; i < expresion.length(); i++) {// Se recorre la expresión infija caracter por caracter
            char c = expresion.charAt(i);// Se obtiene el caracter en la posición i
            if (Character.isDigit(c)) {
                numero.append(c);
            } else {
                if (numero.length() > 0) {// Si el número tiene más de un dígito, se agrega a la salida
                    salida.append(numero).append(' ');
                    numero.setLength(0);
                }
                if (c == '(') {// Si el caracter es un paréntesis izquierdo, se agrega a la pila
                    operadores.push(String.valueOf(c));
                } else if (c == ')') {// Si el caracter es un paréntesis derecho, se sacan los operadores de la pila hasta encontrar un paréntesis izquierdo
                    while (!operadores.isEmpty() && !operadores.peek().equals("(")) {
                        salida.append(operadores.pop()).append(' ');
                    }
                    operadores.pop();
                } else if (esOperador(String.valueOf(c))) {// Si el caracter es un operador, se sacan los operadores de la pila hasta encontrar un operador de menor precedencia
                    String operador = String.valueOf(c);
                    if (c == '*' && i + 1 < expresion.length() && expresion.charAt(i + 1) == '*') {// Si el operador es **, se agrega a la pila
                        operador = "**";
                        i++;
                    }
                    while (!operadores.isEmpty() && ordenOperadores(operador) <= ordenOperadores(operadores.peek())) {// Se sacan los operadores de la pila hasta encontrar un operador de menor precedencia
                        salida.append(operadores.pop()).append(' ');
                    }
                    operadores.push(operador);
                }
            }
        }

        if (numero.length() > 0) {// Si el número tiene más de un dígito, se agrega a la salida
            salida.append(numero).append(' ');
        }

        while (!operadores.isEmpty()) {// Se sacan los operadores de la pila hasta que esta quede vacía
            salida.append(operadores.pop()).append(' ');
        }

        return salida.toString();
    }

    /**
     * Método para saber si un string es un operador
     * @param s String a evaluar
     * @return True si es un operador, false si no lo es
     */
    private static boolean esOperador(String s) {
        return !s.isEmpty() && (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("%") || s.equals("**") || s.equals("!") || s.equals("(+)") || s.equals("^"));
    }

    /**
     * Método para saber el orden de precedencia de los operadores
     * @param operador Operador a evaluar
     * @return Orden de precedencia del operador
     */
    private static int ordenOperadores(String operador) {
        switch (operador) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
            case "%":
                return 2;
            case "**":
                return 3;
            case "!":
                return 4;
            case "(+)":
                return 5;
            case "^":
                return 6;
            default:
                return 0;
        }
    }
}

/**
 * Clase que se encarga de manejar la conexión con los clientes
 */
class ManejoClientes extends Thread {
    private final Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;
    private String nombreCliente;

    /**
     * Constructor de la clase ManejoClientes
     * @param socket Socket del cliente
     */
    public ManejoClientes(Socket socket) {
        this.socket = socket;
        try {
            salida = new PrintWriter(socket.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

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

    /**
     * Método para enviar un mensaje al cliente
     * @param message Mensaje a enviar
     */
    public void enviarMensajes(String message) {
        salida.println(message);
    }

    /**
     * Método para obtener el nombre del cliente
     * @return Nombre del cliente
     */
    public String getNombreCliente() {
        return nombreCliente;
    }

    /**
     * Método para procesar el mensaje que recibe el servidor
     * @param message Mensaje a procesar
     * @throws IOException
     */
    public void procesarMensaje(String message) throws IOException {
        MainArbol mainArbol = new MainArbol();
        LocalDateTime ahora = LocalDateTime.now().withHour(0);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println(ahora.format(formato));

        // Se extrae el nombre del destinatario antes del >> y el contenido del mensaje después del >>
        if (message.contains(">>")) {
            String[] partes = message.split(">>", 2);
            String destinatario = partes[0].trim();
            String contenido = partes[1].trim();

            String expresionPostfija = Servidor.convertirPostfijo(contenido);
            String expresionCompleta = MainArbol.convertirExpresionesLogicas(expresionPostfija);
            String[] tokens = expresionCompleta.split(" ");
            Nodo raiz = MainArbol.construirArbol(tokens);
            int resultado = MainArbol.evaluarArbol(raiz);

            Servidor.escribirCSV(contenido, destinatario, String.valueOf(ahora.format(formato)), String.valueOf(resultado));
            Servidor.enviarUno(destinatario, contenido + " = " + resultado);
        }
    }
}



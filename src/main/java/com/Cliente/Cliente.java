package com.Cliente;
import java.io.*;
import java.net.Socket;

public class Cliente {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Cliente() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarExpresion(String expression) {
        out.println(expression);
    }

    public String recibirResultado() {
        try {
            String result = in.readLine();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}


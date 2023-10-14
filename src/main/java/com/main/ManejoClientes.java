package com.main;

import java.io.*;
import java.net.*;

public class ManejoClientes implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ManejoClientes(Socket socket) {
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
                out.println("Servidor dice: " + message.toUpperCase());
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

    public void sendMessage(String message) {
        // Envía un mensaje al cliente
        out.println(message);
    }
}


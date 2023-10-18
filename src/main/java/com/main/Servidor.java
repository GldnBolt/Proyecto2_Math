package com.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
    private static final int POOL_SIZE = 3;
    private static ExecutorService pool;

    public Servidor(int puerto) {
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor en ejecución en el puerto " + puerto);
            pool = Executors.newFixedThreadPool(POOL_SIZE);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket);
                pool.execute(new ManejoClientes(clientSocket));
                System.out.println("Pool exitoso");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void enviarTodos(String message) {
        for (Runnable r : pool.shutdownNow()) {
            ManejoClientes client = (ManejoClientes) r;
            client.enviarMensajes(message);
        }
    }
}

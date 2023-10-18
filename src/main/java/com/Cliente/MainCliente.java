package com.Cliente;

import com.main.MainServidor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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

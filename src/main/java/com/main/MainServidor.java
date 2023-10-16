package com.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainServidor extends Application {
    public static Servidor servidor;
    ServidorController servidorController = new ServidorController();

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


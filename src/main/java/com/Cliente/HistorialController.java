package com.Cliente;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.FileReader;
import java.io.IOException;

public class HistorialController {
    @FXML
    public TableColumn<Historial, String> columnaUsuario;
    @FXML
    public TableColumn<Historial, String> columnaExpresion;
    @FXML
    public TableColumn<Historial, String> columnaResultado;
    @FXML
    public TableColumn<Historial, String> columnaFecha;
    @FXML
    public TableView<Historial> tabla;

    public void leerCSV() {
        try {
            CSVReader reader = new CSVReader(new FileReader("Historial.csv"));
            ObservableList<Historial> data = FXCollections.observableArrayList();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] es un array de valores de una fila
                data.add(new Historial(nextLine[0], nextLine[1], nextLine[2], nextLine[3]));
            }
            tabla.setItems(data);
            columnaUsuario.setCellValueFactory(new PropertyValueFactory<Historial, String>("usuario"));
            columnaExpresion.setCellValueFactory(new PropertyValueFactory<Historial, String>("expresion"));
            columnaResultado.setCellValueFactory(new PropertyValueFactory<Historial, String>("resultado"));
            columnaFecha.setCellValueFactory(new PropertyValueFactory<Historial, String>("fecha"));

        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}


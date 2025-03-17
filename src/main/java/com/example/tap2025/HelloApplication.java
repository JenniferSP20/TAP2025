package com.example.tap2025;

import com.example.tap2025.componentes.Hilo;
import com.example.tap2025.modelos.Conexion;
import com.example.tap2025.vistas.Calculadora;
import com.example.tap2025.vistas.Celayork;
import com.example.tap2025.vistas.ListaClientes;
import com.example.tap2025.vistas.Rompecabezas;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private VBox vBox;
    private MenuBar mnbPrincipal;
    private Menu menCompetencia1, menCompetencia2;
    private MenuItem mitCalculadora, mitRestaurante, mitRompecabeza, mitHilo;
    private Scene escena;

    void CrearUI() {

        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(actionEvent -> new Calculadora());

        mitRompecabeza = new MenuItem("Rompecabezas");
        mitRompecabeza.setOnAction(actionEvent -> new Rompecabezas());

        mitRestaurante = new MenuItem("Restaurante");
        mitRestaurante.setOnAction(actionEvent -> new ListaClientes());

        mitHilo = new MenuItem("Celayork");
        mitHilo.setOnAction(actionEvent -> new Celayork());

        menCompetencia1 = new Menu("Competencia 1");
        menCompetencia1.getItems().addAll(mitCalculadora, mitRestaurante, mitRompecabeza);

        menCompetencia2 = new Menu("Competencia 2");
        menCompetencia2.getItems().addAll(mitHilo);

        mnbPrincipal = new MenuBar(menCompetencia1, menCompetencia2);
        vBox = new VBox(mnbPrincipal);
        escena = new Scene(vBox);
        String css = getClass().getResource("/Styles/main.Css").toExternalForm();
        escena.getStylesheets().add(css);

    }

    @Override
    public void start(Stage stage) {

        Conexion.createConnection();
        CrearUI();
        stage.setTitle("Hola Mundo de Eventos");
        stage.setScene(escena);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
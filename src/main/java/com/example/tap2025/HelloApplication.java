package com.example.tap2025;

import com.example.tap2025.vistas.Calculadora;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private VBox vBox;
    private MenuBar mnbPrincipal;
    private Menu menCompetencia1, menCompetencia2;
    private MenuItem mitCalculadora;
    private Scene escena;

    void CrearUI(){
        mitCalculadora=new MenuItem("Calculadora");
        mitCalculadora.setOnAction(event -> new Calculadora());
        menCompetencia1=new Menu("Competencia 1");
        menCompetencia1.getItems().addAll(mitCalculadora);
        mnbPrincipal=new MenuBar();
        mnbPrincipal.getMenus().addAll(menCompetencia1);
        vBox=new VBox(mnbPrincipal);
        escena=new Scene(vBox);
        escena.getStylesheets().add(getClass().getResource("/Styles/main.css").toString());

    }

    @Override
    public void start(Stage stage) throws IOException {
        CrearUI(); //Instancia el vertical box
        stage.setTitle("Hola Mundo de Eventos :)");
        stage.setScene(escena);
        stage.show();
        stage.setMaximized(true);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
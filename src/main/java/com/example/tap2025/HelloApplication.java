package com.example.tap2025;

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
//Pantalla de inicio
    private VBox vBox;
    private MenuBar mnbPrincipal;
    private Menu menCompetencia1, menCompetencia2;
    private MenuItem mitCalculadora;
    private Scene escena;

    void CrearUI(){
        mitCalculadora=new MenuItem("Calculadora");
        menCompetencia1=new Menu("Competencia1");
        menCompetencia1.getItems().add(mitCalculadora);
        mnbPrincipal=new MenuBar();
        mnbPrincipal.getMenus().add(menCompetencia1);

    }

    @Override
    public void start(Stage stage) throws IOException {

        vBox= new VBox();
        stage.setTitle("Hola Mundo de Eventos :)");
        stage.setScene(new Scene vBox);
        stage.show();
        stage.setMaximized(true);
    }

    public static void main(String[] args) {

    }
}
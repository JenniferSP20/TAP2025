package com.example.tap2025;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SisOp extends Application {
    private Scene escena;


    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Proyecto de Sistemas Operativos :)");
        stage.setScene(escena);
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
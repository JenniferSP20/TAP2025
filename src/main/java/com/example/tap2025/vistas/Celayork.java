package com.example.tap2025.vistas;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.awt.*;

public class Celayork extends Stage {
    private VBox vbox;
    private GridPane gdpCalles;
    private Button btnIniciar;
    private Label[] LblRutas;
    private ProgressBar[] pgbRutas;

    public Celayork() {
        this.setTitle("Calles de Celaya");
        this.setScene(escena);
        this.show();

    }
}

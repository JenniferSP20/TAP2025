package com.example.tap2025.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.*;

public class Rompecabezas extends Stage {
    private static final String IMAGEN_PATH = "/Img/2.jpg";
    private int tamano = 3; // Tamaño inicial
    private List<Pieza> piezas = new ArrayList<>();
    private GridPane gridPane = new GridPane();
    private int emptyRow, emptyCol;
    private Label labelTiempo = new Label("Tiempo: 0s");
    private int tiempo = 0;
    private Timeline timeline;
    private Image imagen;

    public Rompecabezas() {
        Button btn3x3 = new Button("3x3");
        Button btn4x4 = new Button("4x4");
        Button btn5x5 = new Button("5x5");
        btn3x3.setOnAction(e -> cambiarTamano(3));
        btn4x4.setOnAction(e -> cambiarTamano(4));
        btn5x5.setOnAction(e -> cambiarTamano(5));
        HBox botones = new HBox(10, btn3x3, btn4x4, btn5x5);

        iniciarJuego();
        VBox root = new VBox(labelTiempo, botones, gridPane);
        this.setScene(new Scene(root, 450, 450));
        this.setTitle("Rompecabezas");
        this.show();
    }

    private void iniciarJuego() {
        cargarImagen();
        mezclarPiezas();
        iniciarTemporizador();
    }

    private void iniciarTemporizador() {
        if (timeline != null) {
            timeline.stop();
        }
        tiempo = 0;
        labelTiempo.setText("Tiempo: 0s");
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            tiempo++;
            labelTiempo.setText("Tiempo: " + tiempo + "s");
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void cargarImagen() {
        piezas.clear();
        imagen = new Image(getClass().getResourceAsStream(IMAGEN_PATH));
        double anchoPieza = imagen.getWidth() / tamano;
        double altoPieza = imagen.getHeight() / tamano;

        for (int fila = 0; fila < tamano; fila++) {
            for (int col = 0; col < tamano; col++) {
                if (fila == tamano - 1 && col == tamano - 1) {
                    piezas.add(null);
                    emptyRow = fila;
                    emptyCol = col;
                    continue;
                }
                ImageView piezaView = new ImageView(imagen);
                piezaView.setViewport(new javafx.geometry.Rectangle2D(col * anchoPieza, fila * altoPieza, anchoPieza, altoPieza));
                piezaView.setFitWidth(400 / tamano);
                piezaView.setFitHeight(400 / tamano);
                piezaView.setOnMouseClicked(this::moverPieza);
                piezas.add(new Pieza(piezaView, fila, col));
            }
        }
        actualizarGrid();
    }

    private void mezclarPiezas() {
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            List<int[]> movimientosPosibles = new ArrayList<>();
            if (emptyRow > 0) movimientosPosibles.add(new int[]{emptyRow - 1, emptyCol});
            if (emptyRow < tamano - 1) movimientosPosibles.add(new int[]{emptyRow + 1, emptyCol});
            if (emptyCol > 0) movimientosPosibles.add(new int[]{emptyRow, emptyCol - 1});
            if (emptyCol < tamano - 1) movimientosPosibles.add(new int[]{emptyRow, emptyCol + 1});

            int[] movimiento = movimientosPosibles.get(rand.nextInt(movimientosPosibles.size()));
            int indexPieza = movimiento[0] * tamano + movimiento[1];
            int indexVacio = emptyRow * tamano + emptyCol;

            Collections.swap(piezas, indexPieza, indexVacio);
            emptyRow = movimiento[0];
            emptyCol = movimiento[1];
        }
        actualizarGrid();
    }

    private void actualizarGrid() {
        gridPane.getChildren().clear();
        int index = 0;
        for (int fila = 0; fila < tamano; fila++) {
            for (int col = 0; col < tamano; col++) {
                Pieza pieza = piezas.get(index++);
                if (pieza != null) {
                    gridPane.add(pieza.imageView, col, fila);
                }
            }
        }
    }

    private void guardarTiempo() {
        try (FileWriter writer = new FileWriter("tiempos.txt", true)) {
            writer.write("Tamaño " + tamano + "x" + tamano + " - " + tiempo + "s\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean esSolucion() {
        for (int i = 0; i < piezas.size(); i++) {
            Pieza pieza = piezas.get(i);
            if (pieza == null) continue;
            int filaEsperada = i / tamano;
            int colEsperada = i % tamano;
            if (pieza.filaOriginal != filaEsperada || pieza.colOriginal != colEsperada) {
                return false;
            }
        }
        return true;
    }

    private void moverPieza(MouseEvent event) {
        ImageView piezaSeleccionada = (ImageView) event.getSource();
        int filaPieza = GridPane.getRowIndex(piezaSeleccionada);
        int colPieza = GridPane.getColumnIndex(piezaSeleccionada);

        if ((Math.abs(filaPieza - emptyRow) == 1 && colPieza == emptyCol) ||
                (Math.abs(colPieza - emptyCol) == 1 && filaPieza == emptyRow)) {
            int indexPieza = filaPieza * tamano + colPieza;
            int indexVacio = emptyRow * tamano + emptyCol;

            Collections.swap(piezas, indexPieza, indexVacio);
            emptyRow = filaPieza;
            emptyCol = colPieza;
            actualizarGrid();

            if (esSolucion()) {
                guardarTiempo();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("¡Felicidades!");
                alert.setHeaderText(null);
                alert.setContentText("¡Has completado el rompecabezas en " + tiempo + " segundos!");
                alert.showAndWait();
            }
        }
    }

    private void cambiarTamano(int nuevoTamano) {
        this.tamano = nuevoTamano;
        iniciarJuego();
    }

    private static class Pieza {
        ImageView imageView;
        int filaOriginal, colOriginal;

        Pieza(ImageView imageView, int filaOriginal, int colOriginal) {
            this.imageView = imageView;
            this.filaOriginal = filaOriginal;
            this.colOriginal = colOriginal;
        }
    }
}

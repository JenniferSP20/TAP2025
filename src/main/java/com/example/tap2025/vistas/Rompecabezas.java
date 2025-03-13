package com.example.tap2025.vistas;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Rompecabezas extends Stage {
    private static final String IMAGEN_PATH = "/Img/2.jpg";
    private static final int TAMANO = 3; // Tamaño del rompecabezas 3x3
    private List<Pieza> piezas = new ArrayList<>();
    private GridPane gridPane = new GridPane();
    private int emptyRow = TAMANO - 1;
    private int emptyCol = TAMANO - 1;
    private Label labelTiempo = new Label("Tiempo: 0s");
    private Label labelFelicidades = new Label();
    private int tiempo = 0;
    private Timeline timeline;
    private Image imagen;

    public Rompecabezas() {
        cargarImagen();
        mezclarPiezas();
        iniciarTemporizador();

        VBox root = new VBox(labelTiempo, gridPane, labelFelicidades);
        this.setScene(new Scene(root, 400, 400));
        this.setTitle("Rompecabezas");
        this.show();
    }

    private void cargarImagen() {
        imagen = new Image(getClass().getResourceAsStream(IMAGEN_PATH));
        double anchoPieza = imagen.getWidth() / TAMANO;
        double altoPieza = imagen.getHeight() / TAMANO;

        for (int fila = 0; fila < TAMANO; fila++) {
            for (int col = 0; col < TAMANO; col++) {
                if (fila == TAMANO - 1 && col == TAMANO - 1) {
                    piezas.add(null); // Espacio vacío
                    continue;
                }
                ImageView piezaView = new ImageView(imagen);
                piezaView.setViewport(new javafx.geometry.Rectangle2D(col * anchoPieza, fila * altoPieza, anchoPieza, altoPieza));
                piezaView.setFitWidth(400 / TAMANO);
                piezaView.setFitHeight(400 / TAMANO);
                piezaView.setOnMouseClicked(this::moverPieza);
                piezas.add(new Pieza(piezaView, fila, col));
            }
        }
    }

    private void mezclarPiezas() {
        Random rand = new Random();

        for (int i = 0; i < 100; i++) {
            List<int[]> movimientosPosibles = new ArrayList<>();
            if (emptyRow > 0) movimientosPosibles.add(new int[]{emptyRow - 1, emptyCol});
            if (emptyRow < TAMANO - 1) movimientosPosibles.add(new int[]{emptyRow + 1, emptyCol});
            if (emptyCol > 0) movimientosPosibles.add(new int[]{emptyRow, emptyCol - 1});
            if (emptyCol < TAMANO - 1) movimientosPosibles.add(new int[]{emptyRow, emptyCol + 1});

            int[] movimiento = movimientosPosibles.get(rand.nextInt(movimientosPosibles.size()));
            int nuevaFila = movimiento[0];
            int nuevaCol = movimiento[1];

            int indexPieza = nuevaFila * TAMANO + nuevaCol;
            int indexVacio = emptyRow * TAMANO + emptyCol;

            Collections.swap(piezas, indexPieza, indexVacio);

            emptyRow = nuevaFila;
            emptyCol = nuevaCol;
        }
        actualizarGrid();
    }

    private void actualizarGrid() {
        gridPane.getChildren().clear();
        int index = 0;
        for (int fila = 0; fila < TAMANO; fila++) {
            for (int col = 0; col < TAMANO; col++) {
                Pieza pieza = piezas.get(index++);
                if (pieza != null) {
                    gridPane.add(pieza.imageView, col, fila);
                }
            }
        }
    }

    private void moverPieza(MouseEvent event) {
        ImageView piezaSeleccionada = (ImageView) event.getSource();
        int filaPieza = GridPane.getRowIndex(piezaSeleccionada);
        int colPieza = GridPane.getColumnIndex(piezaSeleccionada);

        if ((Math.abs(filaPieza - emptyRow) == 1 && colPieza == emptyCol) ||
                (Math.abs(colPieza - emptyCol) == 1 && filaPieza == emptyRow)) {

            int indexPieza = filaPieza * TAMANO + colPieza;
            int indexVacio = emptyRow * TAMANO + emptyCol;

            Collections.swap(piezas, indexPieza, indexVacio);
            emptyRow = filaPieza;
            emptyCol = colPieza;
            actualizarGrid();

            if (esSolucion()) {
                timeline.stop();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("¡Felicidades!");
                alert.setHeaderText(null);
                alert.setContentText("¡Has completado el rompecabezas en " + tiempo + " segundos!");
                alert.showAndWait();
            }
        }
    }

    private boolean esSolucion() {
        for (int i = 0; i < piezas.size(); i++) {
            Pieza pieza = piezas.get(i);
            if (pieza == null) continue;
            int filaEsperada = i / TAMANO;
            int colEsperada = i % TAMANO;
            if (pieza.filaOriginal != filaEsperada || pieza.colOriginal != colEsperada) {
                return false;
            }
        }
        return true;
    }

    private void iniciarTemporizador() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            tiempo++;
            labelTiempo.setText("Tiempo: " + tiempo + "s");
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
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

package com.example.tap2025.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage {
    private Scene escena;
    private TextField txtDisplay;
    private VBox vbox;
    private GridPane gdpTeclado;
    private Button[][] arBtnTeclado;
    private Button btnClear;
    private String operador = "";
    private double num1 = 0;
    private boolean nuevaOperacion = false;
    private boolean errorMostrado = false;
    String strTeclas[] = {"7", "8", "9", "*", "4", "5", "6", "/", "1", "2", "3", "+", "=", "0", ".", "-"};

    public void CrearUI() {
        CrearKeyboard();
        txtDisplay = new TextField("0");
        txtDisplay.setEditable(false);
        txtDisplay.setAlignment(Pos.BASELINE_RIGHT);
        btnClear = new Button("C");
        btnClear.setPrefSize(50, 50);
        btnClear.setOnAction(e -> limpiarDisplay());
        vbox = new VBox(txtDisplay, btnClear, gdpTeclado);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        escena = new Scene(vbox, 250, 350);
        escena.getStylesheets().add(getClass().getResource("/Styles/Calcu.css").toString());
    }

    public void CrearKeyboard() {
        arBtnTeclado = new Button[4][4];
        gdpTeclado = new GridPane();
        gdpTeclado.setHgap(5);
        gdpTeclado.setVgap(5);
        int pos = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arBtnTeclado[i][j] = new Button(strTeclas[pos]);
                arBtnTeclado[i][j].setId("fontButton");
                int finalPos = pos;
                arBtnTeclado[i][j].setOnAction(e -> EventoTeclado(strTeclas[finalPos]));
                arBtnTeclado[i][j].setPrefSize(50, 50);
                gdpTeclado.add(arBtnTeclado[i][j], j, i);
                pos++;
            }
        }
    }

    private void EventoTeclado(String strTecla) {
        if (errorMostrado) {
            txtDisplay.setText("0");
            errorMostrado = false;
        }
        if (strTecla.matches("[0-9.]")) {
            if (nuevaOperacion) {
                txtDisplay.setText(strTecla);
                nuevaOperacion = false;
            } else {
                txtDisplay.setText(txtDisplay.getText().equals("0") ? strTecla : txtDisplay.getText() + strTecla);
            }
        } else if (strTecla.equals("-")) {
            if (txtDisplay.getText().equals("0") || nuevaOperacion) {
                txtDisplay.setText("-");
                nuevaOperacion = false;
            } else if (!txtDisplay.getText().contains("-")) {
                operador = "-";
                num1 = Double.parseDouble(txtDisplay.getText());
                txtDisplay.setText("0");
            }
        } else if (strTecla.matches("[+*/]")) {
            operador = strTecla;
            num1 = Double.parseDouble(txtDisplay.getText());
            txtDisplay.setText("0");
        } else if (strTecla.equals("=")) {
            double num2 = Double.parseDouble(txtDisplay.getText());
            double resultado = realizarOperacion(num1, num2, operador);
            if (Double.isNaN(resultado)) {
                txtDisplay.setText("Error");
                errorMostrado = true;
            } else {
                txtDisplay.setText(String.valueOf(resultado));
                nuevaOperacion = true;
            }
        }
    }

    private void limpiarDisplay() {
        txtDisplay.setText("0");
        operador = "";
        num1 = 0;
        nuevaOperacion = false;
        errorMostrado = false;
    }

    private double realizarOperacion(double num1, double num2, String operador) {
        switch (operador) {
            case "+": return num1 + num2;
            case "-": return num1 - num2;
            case "*": return num1 * num2;
            case "/": return num2 == 0 ? Double.NaN : num1 / num2;
            default: return Double.NaN;
        }
    }

    public Calculadora() {
        CrearUI();
        this.setScene(escena);
        this.setTitle("Calculadora");
        this.show();
    }
}

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
    //Generar un metodo
    private Scene escena;
    private TextField txtDisplay;
    private VBox vbox;
    private GridPane gdpTeclado;
    private Button[][] arBtnTeclado;
    String strTeclas[]= {"7","8","9","*","4","5","6","/","1","2","3","+","=","0",".","-"};

    public void CrearUI(){
        CrearKeyboard();
        txtDisplay = new TextField("0");
        txtDisplay.setEditable(false);
        txtDisplay.setAlignment(Pos.BASELINE_RIGHT);
        vbox = new VBox(txtDisplay, gdpTeclado);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        escena = new Scene(vbox, 200, 200);
    }

public void CrearKeyboard(){
   arBtnTeclado = new Button[4][4];
   gdpTeclado = new GridPane();
   gdpTeclado.setHgap(5);
   gdpTeclado.setVgap(5);
   int pos = 0;
   for (int i = 0; i < 4; i++) {
       for (int j = 0; j < 4; j++) {
           arBtnTeclado[i][j] = new Button(strTeclas[pos]);
           //Funcion de los botones
           int finalPos = pos;
           arBtnTeclado[i][j].setOnAction(e -> EventoTeclado(strTeclas[finalPos]));
           arBtnTeclado[i][j].setPrefSize(50,50);
           gdpTeclado.add(arBtnTeclado[i][j],j,i);
           pos++;
       }
   }

}

    private void EventoTeclado(String strTecla) {

        txtDisplay.appendText(strTecla);
    }

    public Calculadora() {
        CrearUI();
        this.setScene(escena);
       this.setTitle("Calculadora");
       this.show();
    }

}

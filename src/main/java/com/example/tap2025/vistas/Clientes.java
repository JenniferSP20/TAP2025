package com.example.tap2025.vistas;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Clientes extends Stage {
    private Button btnGuardar;
    private TextField txtNomte, txtDireccion, txtTelCte, txtEmail;
    private VBox Vbox;
    private Scene escena;

    public Clientes() {
        this.setTitle("Registrar Clientes");
        this.setScene(escena);
        this.show();

    }
    private void CrearUI() {
        txtNomte = new TextField();
        txtDireccion = new TextField();
        txtTelCte = new TextField();
        txtEmail = new TextField();
        btnGuardar = new Button("Guardar");
        Vbox=new VBox(txtNomte, txtDireccion, txtTelCte, txtEmail);
        escena = new Scene(Vbox, 120, 150);
    }


}


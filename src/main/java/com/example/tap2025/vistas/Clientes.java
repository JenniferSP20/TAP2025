package com.example.tap2025.vistas;
import com.example.tap2025.modelos.ClientesDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Clientes extends Stage {
    private Button btnGuardar;
    private TextField txtNomte, txtDireccion, txtTelCte, txtEmail;
    private VBox Vbox;
    private Scene escena;
    private ClientesDAO objC;
    private TableView<ClientesDAO> tbvClientes;

    public Clientes() {
        objC = new ClientesDAO();
        CrearUI();
        this.setTitle("Registrar Cliente");
        this.setScene(escena);
        this.show();

    }
    private void CrearUI() {
        txtNomte = new TextField();
        txtDireccion = new TextField();
        txtTelCte = new TextField();
        txtEmail = new TextField();
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> {
            objC.setNomCte(txtNomte.getText());
            objC.setDireccion(txtDireccion.getText());
            objC.setTelCte(txtTelCte.getText());
            objC.setEmailCte(txtEmail.getText());
            objC.INSERT();
            tbvClientes.setItems(objC.SELECT());
            tbvClientes.refresh();
            this.close();
        });
        Vbox=new VBox(txtNomte, txtDireccion, txtTelCte, txtEmail, btnGuardar);
        escena = new Scene(Vbox, 120, 150);
    }


}


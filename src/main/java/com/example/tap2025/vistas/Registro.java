package com.example.tap2025.vistas;

import com.example.tap2025.modelos.UsuariosDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Registro {

    public void mostrarVentanaRegistro() {
        Stage ventana = new Stage();
        ventana.setTitle("Registro de Usuario");

        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Usuario");
        txtUsuario.getStyleClass().add("text-field");

        PasswordField txtContrasena = new PasswordField();
        txtContrasena.setPromptText("Contraseña");
        txtContrasena.getStyleClass().add("text-field");

        Button btnRegistrar = new Button("Registrar");
        btnRegistrar.getStyleClass().add("button");
        btnRegistrar.setOnAction(e -> {
            UsuariosDAO usuario = new UsuariosDAO();
            usuario.setUsername(txtUsuario.getText());
            usuario.setPassword(txtContrasena.getText());

            if (usuario.insertar()) {
                mostrarAlerta("Registro exitoso", Alert.AlertType.INFORMATION);
                txtUsuario.clear();
                txtContrasena.clear();
            } else {
                mostrarAlerta("Error al registrar", Alert.AlertType.ERROR);
            }
        });

        VBox layoutFormulario = new VBox(12, txtUsuario, txtContrasena, btnRegistrar);
        layoutFormulario.setId("panelRegistro");
        layoutFormulario.setAlignment(Pos.CENTER);
        layoutFormulario.setPadding(new Insets(20));

        VBox contenedor = new VBox(layoutFormulario);
        contenedor.setAlignment(Pos.CENTER);
        contenedor.setId("contenedorRegistro");

        Scene escena = new Scene(contenedor, 320, 280);
        escena.getStylesheets().add(getClass().getResource("/Styles/teclado.css").toExternalForm());

        ventana.setScene(escena);
        ventana.setResizable(false);
        ventana.show();
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Registro");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

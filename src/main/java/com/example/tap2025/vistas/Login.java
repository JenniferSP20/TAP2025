package com.example.tap2025.vistas;

import com.example.tap2025.modelos.UsuariosDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login {

    public void mostrarVentanaLogin() {
        Stage ventana = new Stage();
        ventana.setTitle("Iniciar Sesión");

        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Usuario");
        txtUsuario.getStyleClass().add("text-field");

        PasswordField txtContrasena = new PasswordField();
        txtContrasena.setPromptText("Contraseña");
        txtContrasena.getStyleClass().add("text-field");

        Button btnLogin = new Button("Iniciar Sesión");
        btnLogin.getStyleClass().add("button");
        btnLogin.setOnAction(e -> {
            String usuario = txtUsuario.getText();
            String contrasena = txtContrasena.getText();

            UsuariosDAO dao = new UsuariosDAO();
            dao.setUsername(usuario);
            dao.setPassword(contrasena);

            if (dao.verificarLogin()) {
                mostrarAlerta("¡Inicio de sesión exitoso!", Alert.AlertType.INFORMATION);
                ventana.close();

                Estadisticas estadisticas = new Estadisticas();
                estadisticas.show();
            } else {
                mostrarAlerta("Credenciales incorrectas", Alert.AlertType.ERROR);
            }
        });

        VBox layoutFormulario = new VBox(10, txtUsuario, txtContrasena, btnLogin);
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
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}


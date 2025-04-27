package com.example.tap2025.vistas;

import com.example.tap2025.modelos.ClientesDAO;
import com.example.tap2025.modelos.ReservacionDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ReservacionApp extends Stage {

    private int mesaSeleccionada = -1;
    private ComboBox<ClientesDAO> comboClientes;
    private ComboBox<String> comboHora;
    private ComboBox<Integer> comboNumPersonas;
    private TextField campoActivo;

    public ReservacionApp() {
        initModality(Modality.APPLICATION_MODAL);

        BorderPane root = new BorderPane();

        VBox panelReservacion = new VBox(10);
        panelReservacion.setPadding(new Insets(10));
        panelReservacion.setAlignment(Pos.CENTER);
        panelReservacion.setId("panelReservacion");

        Label lblReservacion = new Label("RESERVACIÓN");
        lblReservacion.setFont(Font.font("Arial", 18));

        VBox panelMesasReservacion = crearPanelMesas();
        panelMesasReservacion.setId("panelMesasReservacion");

        VBox panelClientesReservacion = new VBox(10);
        panelClientesReservacion.setAlignment(Pos.CENTER);
        panelClientesReservacion.setId("panelClientesReservacion");

        Label lblClienteReservacion = new Label("Cliente:");
        comboClientes = new ComboBox<>();
        comboClientes.setItems(FXCollections.observableArrayList(new ClientesDAO().SELECT()));
        comboClientes.setPromptText("Selecciona un cliente");

        Button btnRegistrarCliente = new Button("Registrar Cliente");
        btnRegistrarCliente.setOnAction(e -> {
            Stage ventanaTeclado = new Stage();
            ventanaTeclado.setTitle("Registrar Cliente");

            VBox panelTeclado = crearTecladoConLetras();
            Scene escenaTeclado = new Scene(panelTeclado, 500, 500);
            escenaTeclado.getStylesheets().add(getClass().getResource("/Styles/teclado.css").toExternalForm());
            ventanaTeclado.setScene(escenaTeclado);
            ventanaTeclado.show();
        });

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Fecha");

        comboHora = new ComboBox<>();
        comboHora.setItems(FXCollections.observableArrayList(
                "08:00", "09:00", "10:00", "11:00", "12:00",
                "13:00", "14:00", "15:00", "16:00", "17:00",
                "18:00", "19:00", "20:00", "21:00", "22:00"
        ));
        comboHora.setPromptText("Selecciona una hora");

        comboNumPersonas = new ComboBox<>();
        comboNumPersonas.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11));
        comboNumPersonas.setPromptText("Selecciona número de personas");

        Button btnReservar = new Button("Reservar");
        btnReservar.setOnAction(e -> {
            if (mesaSeleccionada == -1) {
                mostrarAlerta(Alert.AlertType.WARNING, "Mesa no seleccionada", "Por favor, selecciona una mesa para la reservación.");
                return;
            }

            if (comboClientes.getValue() == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Cliente no seleccionado", "Por favor, selecciona un cliente para la reservación.");
                return;
            }

            if (datePicker.getValue() == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Fecha no seleccionada", "Por favor, selecciona una fecha para la reservación.");
                return;
            }

            if (comboHora.getValue() == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Hora no seleccionada", "Por favor, selecciona una hora para la reservación.");
                return;
            }

            if (comboNumPersonas.getValue() == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Número de personas no seleccionado", "Por favor, selecciona el número de personas para la reservación.");
                return;
            }

            ReservacionDAO reservacion = new ReservacionDAO();
            reservacion.setIdCte(comboClientes.getValue().getIdCte());
            reservacion.setIdMesa(mesaSeleccionada);
            reservacion.setFecha(datePicker.getValue().toString());
            reservacion.setHora(comboHora.getValue());
            reservacion.setNumPersonas(comboNumPersonas.getValue());
            reservacion.INSERT();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Reservación Exitosa", "La reservación ha sido guardada exitosamente.");
            this.close();
        });

        panelClientesReservacion.getChildren().addAll(lblClienteReservacion, comboClientes, btnRegistrarCliente, datePicker, comboHora, comboNumPersonas, btnReservar);

        panelReservacion.getChildren().addAll(lblReservacion, panelMesasReservacion, panelClientesReservacion);

        root.setCenter(panelReservacion);

        Scene escena = new Scene(root, 800, 650);
        escena.getStylesheets().add(getClass().getResource("/Styles/reservacion.css").toExternalForm());
        this.setTitle("Reservación");
        this.setScene(escena);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
    private VBox crearTecladoConLetras() {
        VBox panelTeclado = new VBox(10);
        panelTeclado.setPadding(new Insets(10));
        panelTeclado.setAlignment(Pos.CENTER);
        panelTeclado.setId("panelTeclado");

        TextField txtNomCte = new TextField();
        txtNomCte.setPromptText("Nombre");
        txtNomCte.setEditable(false);
        txtNomCte.setOnMouseClicked(e -> campoActivo = txtNomCte);

        TextField txtTelCte = new TextField();
        txtTelCte.setPromptText("Teléfono");
        txtTelCte.setEditable(false);
        txtTelCte.setOnMouseClicked(e -> campoActivo = txtTelCte);

        TextField txtDireccion = new TextField();
        txtDireccion.setPromptText("Dirección");
        txtDireccion.setEditable(false);
        txtDireccion.setOnMouseClicked(e -> campoActivo = txtDireccion);

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        txtEmail.setEditable(false);
        txtEmail.setOnMouseClicked(e -> campoActivo = txtEmail);

        GridPane teclado = new GridPane();
        teclado.setHgap(5);
        teclado.setVgap(5);
        teclado.setAlignment(Pos.CENTER);

        String[] fila1 = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        String[] fila2 = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"};
        String[] fila3 = {"A", "S", "D", "F", "G", "H", "J", "K", "L"};
        String[] fila4 = {"Z", "X", "C", "V", "B", "N", "M"};
        String[] fila5 = {".", "@", "Espacio"};


        agregarFilaTeclado(teclado, fila1, 0);
        agregarFilaTeclado(teclado, fila2, 1);
        agregarFilaTeclado(teclado, fila3, 2);
        agregarFilaTeclado(teclado, fila4, 3);
        agregarFilaTeclado(teclado, fila5, 4);

        Button btnBorrar = new Button("Borrar");
        btnBorrar.setOnAction(e -> {
            if (campoActivo != null && !campoActivo.getText().isEmpty()) {
                campoActivo.setText(campoActivo.getText().substring(0, campoActivo.getText().length() - 1));
            }
        });

        Button btnGuardar = new Button("Guardar");
        btnGuardar.setPrefSize(70, 60);
        btnBorrar.setPrefSize(60, 60);

        btnGuardar.setOnAction(e -> {
            String nombre = txtNomCte.getText();
            String telefono = txtTelCte.getText();
            String direccion = txtDireccion.getText();
            String email = txtEmail.getText();

            if (!nombre.isEmpty() && !telefono.isEmpty() && !direccion.isEmpty() && !email.isEmpty()) {
                ClientesDAO cliente = new ClientesDAO();
                cliente.setNomCte(nombre);
                cliente.setTelCte(telefono);
                cliente.setDireccion(direccion);
                cliente.setEmailCte(email);
                cliente.INSERT();

                mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente Registrado", "El cliente ha sido registrado exitosamente.");
                this.close();

                comboClientes.setItems(FXCollections.observableArrayList(new ClientesDAO().SELECT()));
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor, completa toda la información.");
            }
        });

        panelTeclado.getChildren().addAll(txtNomCte, txtTelCte, txtDireccion, txtEmail, teclado, btnBorrar, btnGuardar);
        return panelTeclado;
    }

    private void agregarFilaTeclado(GridPane teclado, String[] fila, int filaIndex) {
        int col = 0;
        for (String tecla : fila) {
            Button btn = new Button(tecla);
            btn.setPrefSize(50, 50);
            btn.setOnAction(e -> {
                if (campoActivo != null) {
                    if (tecla.equals("Espacio")) {
                        campoActivo.setText(campoActivo.getText() + " ");
                    } else {
                        campoActivo.setText(campoActivo.getText() + tecla);
                    }
                }
            });
            teclado.add(btn, col, filaIndex);
            col++;
        }
    }
    private VBox crearPanelMesas() {
        VBox panelMesas = new VBox(10);
        panelMesas.setPadding(new Insets(10));
        panelMesas.setAlignment(Pos.CENTER);

        Label lbl = new Label("MESAS");
        lbl.setFont(Font.font("Arial", 16));
        panelMesas.getChildren().add(lbl);

        int columnas = 5;
        HBox filaActual = new HBox(5);
        filaActual.setAlignment(Pos.CENTER);
        int count = 0;

        for (int i = 1; i <= 20; i++) {
            Button btnMesa = new Button(String.valueOf(i));
            btnMesa.setPrefSize(40, 40);
            btnMesa.setOnAction(e -> {
                mesaSeleccionada = Integer.parseInt(btnMesa.getText());
                mostrarAlerta(Alert.AlertType.INFORMATION, "Mesa Seleccionada", "Mesa " + mesaSeleccionada + " seleccionada para reservación.");
            });

            filaActual.getChildren().add(btnMesa);
            count++;

            if (count % columnas == 0) {
                panelMesas.getChildren().add(filaActual);
                filaActual = new HBox(5);
                filaActual.setAlignment(Pos.CENTER);
            }
        }

        if (!filaActual.getChildren().isEmpty()) {
            panelMesas.getChildren().add(filaActual);
        }

        return panelMesas;
    }
}
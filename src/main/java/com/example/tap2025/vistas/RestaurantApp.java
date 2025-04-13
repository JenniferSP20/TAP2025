package com.example.tap2025.vistas;

import com.example.tap2025.modelos.Conexion;
import com.example.tap2025.modelos.MesaDAO;
import com.example.tap2025.modelos.OrdenDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RestaurantApp extends Stage {

    private ListView<String> listaProductos;
    private ListView<String> listaOrden;
    private ComboBox<Integer> comboCantidad;
    private Label lblTotal;
    private double total = 0.0;
    private int mesaSeleccionada = -1;

    public RestaurantApp() {
        Conexion.createConnection(); // Asegura que se cree la conexión

        BorderPane root = new BorderPane();
        root.setLeft(crearBarraLateral());
        root.setTop(crearPanelMesas());
        root.setCenter(crearPanelProductos());
        root.setRight(crearPanelOrden());

        Scene escena = new Scene(root, 1200, 770);
        this.setTitle("Sistema Restaurante");
        this.setScene(escena);
        this.show();
    }

    private VBox crearBarraLateral() {
        VBox barra = new VBox(10);
        barra.setPadding(new Insets(10));
        barra.setAlignment(Pos.TOP_CENTER);
        barra.setStyle("-fx-background-color: #ECECEC;");

        String[] secciones = {"MESAS", "BARRA", "COCINA", "TELÉFONO", "CAJA", "ADMINISTRACIÓN"};

        for (String sec : secciones) {
            Button btn = crearBotonMenu(sec);
            barra.getChildren().add(btn);
        }

        barra.getChildren().add(new Separator());

        Label lblCategorias = new Label("CATEGORÍAS");
        lblCategorias.setFont(Font.font("Arial", 16));
        barra.getChildren().add(lblCategorias);

        String[] categorias = {"BEBIDAS", "DESAYUNOS", "BOCADILLOS", "GUARNICIONES",
                "CAFÉS", "POSTRES", "COMIDAS", "SNACKS / TAPAS", "MENÚS"};

        for (String cat : categorias) {
            Button btnCat = crearBotonCategoria(cat);
            btnCat.setOnAction(e -> cargarProductos(cat));
            barra.getChildren().add(btnCat);
        }

        return barra;
    }

    private GridPane crearPanelMesas() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER_LEFT);

        Label lbl = new Label("Mesas");
        lbl.setFont(Font.font("Arial", 16));
        grid.add(lbl, 0, 0, 5, 1);

        for (int i = 1; i <= 25; i++) {
            Button btnMesa = new Button(String.valueOf(i));
            btnMesa.setPrefSize(40, 40);
            int numeroMesa = i;
            btnMesa.setOnAction(e -> {
                mesaSeleccionada = numeroMesa;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mesa Seleccionada");
                alert.setHeaderText(null);
                alert.setContentText("Mesa " + mesaSeleccionada + " seleccionada.");
                alert.showAndWait();
            });
            grid.add(btnMesa, (i - 1) % 5, 1 + (i - 1) / 5);
        }

        return grid;
    }

    private VBox crearPanelProductos() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setAlignment(Pos.TOP_CENTER);

        Label lbl = new Label("Productos disponibles:");
        lbl.setFont(Font.font("Arial", 16));

        listaProductos = new ListView<>();
        listaProductos.setPrefSize(300, 300);

        comboCantidad = new ComboBox<>(FXCollections.observableArrayList(1, 2, 3, 4, 5));
        comboCantidad.setValue(1);

        Button btnAgregar = new Button("Agregar");
        btnAgregar.setOnAction(e -> {
            String item = listaProductos.getSelectionModel().getSelectedItem();
            if (item != null) {
                int cantidad = comboCantidad.getValue();
                listaOrden.getItems().add(cantidad + " x " + item);
                total += cantidad * 50;
                actualizarTotal();
            }
        });

        panel.getChildren().addAll(lbl, listaProductos, new Label("Cantidad:"), comboCantidad, btnAgregar);
        return panel;
    }

    private VBox crearPanelOrden() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setAlignment(Pos.TOP_CENTER);

        Label lbl = new Label("Tu orden:");
        lbl.setFont(Font.font("Arial", 16));

        listaOrden = new ListView<>();
        listaOrden.setPrefSize(250, 300);

        lblTotal = new Label("Total: $0.00");
        lblTotal.setFont(Font.font("Arial", 14));

        Button btnFinalizar = new Button("Finalizar Orden");
        btnFinalizar.setOnAction(e -> finalizarOrden());

        panel.getChildren().addAll(lbl, listaOrden, lblTotal, btnFinalizar);
        return panel;
    }

    private void finalizarOrden() {
        if (mesaSeleccionada == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Mesa no seleccionada");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecciona una mesa antes de finalizar la orden.");
            alert.showAndWait();
            return;
        }

        StringBuilder detalles = new StringBuilder();
        for (String item : listaOrden.getItems()) {
            detalles.append(item).append("\n");
        }

        OrdenDAO orden = new OrdenDAO();
        orden.setDetallesOrden(detalles.toString());
        orden.setIdMesa(mesaSeleccionada);
        orden.setIdEmpleado(1);
        orden.setIdCliente(1);
        orden.setFecha(java.time.LocalDateTime.now().toString());
        orden.setTotal(total);
        orden.INSERT();

        MesaDAO mesa = new MesaDAO();
        mesa.setIdMesa(mesaSeleccionada);
        mesa.setOcupado(true);
        mesa.UPDATE_OCUPADO();

        listaOrden.getItems().clear();
        total = 0;
        actualizarTotal();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Orden Finalizada");
        alert.setHeaderText(null);
        alert.setContentText("¡Tu orden ha sido guardada exitosamente!");
        alert.showAndWait();
    }

    private void cargarProductos(String categoria) {
        ObservableList<String> productos = FXCollections.observableArrayList();

        switch (categoria) {
            case "BEBIDAS" -> productos.addAll("Agua", "Jugo", "Refresco");
            case "DESAYUNOS" -> productos.addAll("Huevos", "Molletes", "Hot Cakes");
            case "BOCADILLOS" -> productos.addAll("Sándwich", "Torta", "Baguette");
            case "GUARNICIONES" -> productos.addAll("Papas", "Arroz", "Verduras");
            case "CAFÉS" -> productos.addAll("Café Americano", "Café con leche");
            case "POSTRES" -> productos.addAll("Pastel", "Flan", "Gelatina");
            case "COMIDAS" -> productos.addAll("Carne Asada", "Pechuga", "Pasta");
            case "SNACKS / TAPAS" -> productos.addAll("Nachos", "Quesadillas", "Tostadas");
            case "MENÚS" -> productos.addAll("Menú Ejecutivo", "Menú Infantil");
        }

        listaProductos.setItems(productos);
    }

    private void actualizarTotal() {
        lblTotal.setText(String.format("Total: $%.2f", total));
    }

    private Button crearBoton(String texto) {
        Button btn = new Button(texto);
        btn.setFont(Font.font("Arial", 14));
        btn.setPrefWidth(150);
        return btn;
    }

    private Button crearBotonCategoria(String texto) {
        Button btn = new Button(texto);
        btn.setFont(Font.font("Arial", 13));
        btn.setPrefWidth(160);
        return btn;
    }

    private Button crearBotonMenu(String texto) {
        Button btn = new Button(texto);
        btn.setFont(Font.font("Arial", 13));
        btn.setPrefWidth(160);
        return btn;
    }
}

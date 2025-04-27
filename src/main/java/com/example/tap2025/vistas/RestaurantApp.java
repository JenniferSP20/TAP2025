package com.example.tap2025.vistas;

import com.example.tap2025.modelos.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RestaurantApp extends Stage {

    private ListView<String> listaOrden;
    private ListView<HBox> listaProductos;
    private ComboBox<Integer> comboCantidad;
    private ComboBox<EmpleadoDAO> comboEmpleados;
    private ComboBox<ClientesDAO> comboClientes;
    private Label lblTotal;
    private double total = 0.0;
    private int mesaSeleccionada = -1;
    private Map<String, Double> mapaPrecios = new HashMap<>();

    public RestaurantApp() {
        Conexion.createConnection();

        BorderPane root = new BorderPane();

        HBox contenedorLateral = new HBox(30);
        contenedorLateral.setAlignment(Pos.TOP_LEFT);
        contenedorLateral.setPadding(new Insets(40, 10, 10, 10));
        contenedorLateral.getChildren().addAll(crearBarraLateral());
        root.setLeft(contenedorLateral);

        VBox contenedorCentralVertical = new VBox(30);
        contenedorCentralVertical.setPadding(new Insets(40));
        contenedorCentralVertical.setAlignment(Pos.TOP_LEFT);

        HBox panelSuperior = new HBox(50);
        panelSuperior.setAlignment(Pos.TOP_LEFT);
        panelSuperior.getChildren().addAll(crearPanelMesas(), crearPanelSuperior());

        HBox panelInferior = new HBox(50);
        panelInferior.setAlignment(Pos.TOP_LEFT);
        panelInferior.getChildren().addAll(crearPanelProductos(), crearPanelOrden());

        contenedorCentralVertical.getChildren().addAll(panelSuperior, panelInferior);

        root.setCenter(contenedorCentralVertical);

        Scene escena = new Scene(root, 1200, 770);
        this.setTitle("Sistema Restaurante");
        escena.getStylesheets().add(getClass().getResource("/Styles/restaurant.css").toExternalForm());
        this.setScene(escena);
        this.show();
    }

    private TextField campoActivo;

    private VBox crearTecladoConLetras() {
        VBox panelTeclado = new VBox(10);
        panelTeclado.setPadding(new Insets(10));
        panelTeclado.setId("panelTeclado");
        panelTeclado.setAlignment(Pos.TOP_CENTER);

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
                txtNomCte.clear();
                txtTelCte.clear();
                txtDireccion.clear();
                txtEmail.clear();

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

    private Button crearBotonRegistrarClientes() {
        Button btnTeclado = new Button("REGISTRAR CLIENTES");
        btnTeclado.setFont(Font.font("Arial", 14));

        btnTeclado.setOnAction(e -> {
            Stage ventanaTeclado = new Stage();
            ventanaTeclado.setTitle("Teclado Virtual");

            VBox panelTeclado = crearTecladoConLetras();
            Scene escenaTeclado = new Scene(panelTeclado, 500, 500);
            escenaTeclado.getStylesheets().add(getClass().getResource("/Styles/teclado.css").toExternalForm());
            ventanaTeclado.setScene(escenaTeclado);

            ventanaTeclado.show();
        });

        return btnTeclado;
    }

    private Button crearBotonReservacion() {
        Button btnReservacion = new Button("RESERVACIÓN");
        btnReservacion.setFont(Font.font("Arial", 14));

        btnReservacion.setOnAction(e -> {
            ReservacionApp reservacionApp = new ReservacionApp();
            reservacionApp.initOwner(this);
            reservacionApp.showAndWait();
        });

        return btnReservacion;
    }

    private VBox crearBarraLateral() {
        VBox barra = new VBox(10);
        barra.setPadding(new Insets(15));
        barra.setAlignment(Pos.TOP_CENTER);
        barra.setPrefWidth(180);

        barra.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(barra, Priority.ALWAYS);

        Label lblControl = new Label("CONTROL");
        lblControl.setFont(Font.font("Arial", 16));
        barra.getChildren().add(lblControl);

        String[] secciones = {"ADMINISTRACIÓN"};
        for (String sec : secciones) {
            Button btn = crearBotonMenu(sec);
            btn.setMaxWidth(Double.MAX_VALUE);

            if (sec.equals("ADMINISTRACIÓN")) {
                btn.setOnAction(e -> {
                    new Login().mostrarVentanaLogin();
                });
            }

            barra.getChildren().add(btn);
        }

        Button btnReservacion = crearBotonReservacion();
        btnReservacion.setMaxWidth(Double.MAX_VALUE);
        barra.getChildren().add(btnReservacion);

        Button btnRegistrarClientes = crearBotonRegistrarClientes();
        btnRegistrarClientes.setMaxWidth(Double.MAX_VALUE);
        barra.getChildren().add(btnRegistrarClientes);

        Separator separador = new Separator();
        barra.getChildren().add(separador);

        Label lblCategorias = new Label("CATEGORÍAS");
        lblCategorias.setFont(Font.font("Arial", 16));
        barra.getChildren().add(lblCategorias);

        GridPane gridCategorias = new GridPane();
        gridCategorias.setHgap(10);
        gridCategorias.setVgap(10);
        gridCategorias.setAlignment(Pos.CENTER);

        gridCategorias.add(crearBotonCategoria("BEBIDAS", "/Img/Categorias/Bebidas.png"), 0, 0);
        gridCategorias.add(crearBotonCategoria("DESAYUNOS", "/Img/Categorias/Desayunos.png"), 1, 0);
        gridCategorias.add(crearBotonCategoria("BOCADILLOS", "/Img/Categorias/Bocadillos.png"), 0, 1);
        gridCategorias.add(crearBotonCategoria("GUARNICIONES", "/Img/Categorias/Guarniciones.png"), 1, 1);
        gridCategorias.add(crearBotonCategoria("CAFÉS", "/Img/Categorias/Cafes.png"), 0, 2);
        gridCategorias.add(crearBotonCategoria("POSTRES", "/Img/Categorias/Postres.png"), 1, 2);
        gridCategorias.add(crearBotonCategoria("COMIDAS", "/Img/Categorias/Comidas.png"), 0, 3);
        gridCategorias.add(crearBotonCategoria("SNACKS", "/Img/Categorias/Snacks.png"), 1, 3);
        gridCategorias.add(crearBotonCategoria("MENÚS", "/Img/Categorias/Menus.png"), 0, 4);

        barra.getChildren().add(gridCategorias);

        return barra;
    }

    private Button crearBotonCategoria(String nombreCategoria, String imagePath) {
        Button btn = new Button();
        btn.setPrefWidth(90);

        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(95);
            imageView.setFitHeight(70);

            btn.setGraphic(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar la imagen: " + imagePath);
        }

        btn.setOnAction(e -> cargarProductos(nombreCategoria));

        return btn;
    }

    private VBox crearPanelProductos() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setAlignment(Pos.TOP_CENTER);

        Label lbl = new Label("PRODUCTOS DISPONIBLES:");
        lbl.setFont(Font.font("Arial", 16));

        listaProductos = new ListView<>();
        listaProductos.setPrefSize(500, 300);

        comboCantidad = new ComboBox<>(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        comboCantidad.setValue(1);

        Button btnAgregar = new Button("AGREGAR");
        btnAgregar.setOnAction(e -> {
            HBox selectedItem = listaProductos.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Label nombreLabel = (Label) selectedItem.getChildren().get(1);
                String item = nombreLabel.getText();
                int cantidad = comboCantidad.getValue();
                Double precio = mapaPrecios.get(item.split(" - \\$")[0]);
                if (precio != null) {
                    listaOrden.getItems().add(cantidad + " x " + item);
                    total += cantidad * precio;
                    actualizarTotal();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "Precio no encontrado para el producto seleccionado.");
                }
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Selección de Producto", "Por favor, selecciona un producto antes de agregarlo a la orden.");
            }
        });

        panel.getChildren().addAll(lbl, listaProductos, new Label("CANTIDAD:"), comboCantidad, btnAgregar);
        return panel;
    }

    private VBox crearPanelOrden() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setAlignment(Pos.TOP_CENTER);

        Label lbl = new Label("TU ORDEN:");
        lbl.setFont(Font.font("Arial", 16));

        listaOrden = new ListView<>();
        listaOrden.setPrefSize(280, 280);

        lblTotal = new Label("TOTAL: $0.00");
        lblTotal.setFont(Font.font("Arial", 14));

        Button btnFinalizar = new Button("FINALIZAR ORDEN");
        btnFinalizar.setOnAction(e -> finalizarOrden());

        panel.getChildren().addAll(lbl, listaOrden, lblTotal, btnFinalizar);
        return panel;
    }

    private void finalizarOrden() {
        if (mesaSeleccionada == -1) {
            mostrarAlerta(Alert.AlertType.WARNING, "Mesa no seleccionada", "Por favor, selecciona una mesa antes de finalizar la orden.");
            return;
        }

        if (comboEmpleados.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Empleado no seleccionado", "Por favor, selecciona un empleado antes de finalizar la orden.");
            return;
        }

        if (comboClientes.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Cliente no seleccionado", "Por favor, selecciona un cliente antes de finalizar la orden.");
            return;
        }

        if (listaOrden.getItems().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Orden vacía", "No puedes finalizar una orden vacía.");
            return;
        }

        StringBuilder detalles = new StringBuilder();
        for (String item : listaOrden.getItems()) {
            detalles.append(item).append("\n");
        }

        OrdenDAO orden = new OrdenDAO();
        orden.setDetallesOrden(detalles.toString());
        orden.setidMesa(mesaSeleccionada);
        orden.setId_Empleado(comboEmpleados.getValue().getId_Empleado());
        orden.setidCte(comboClientes.getValue().getIdCte());

        String fechaActual = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        orden.setFecha(fechaActual);
        orden.setTotal(total);

        orden.INSERT();

        int idOrden = orden.getLastInsertedId();
        System.out.println("ID de la orden insertada: " + idOrden);

        if (idOrden == 0) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo obtener el ID de la orden insertada.");
            return;
        }

        for (String item : listaOrden.getItems()) {
            String[] partes = item.split(" x ");
            int cantidad = Integer.parseInt(partes[0]);
            String nombreProducto = partes[1].split(" - \\$")[0];

            System.out.println("Buscando producto: " + nombreProducto);

            int idProducto = obtenerIdProductoPorNombre(nombreProducto);

            if (idProducto != -1) {
                OrdenDetalleDAO ordenDetalle = new OrdenDetalleDAO();
                ordenDetalle.setId_Orden(idOrden);
                ordenDetalle.setId_Producto(idProducto);
                ordenDetalle.setCantidad(cantidad);
                ordenDetalle.INSERT();
                System.out.println("Insertando en orden_detalle: Id_Orden=" + idOrden + ", Id_Producto=" + idProducto + ", Cantidad=" + cantidad);
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Producto no encontrado: " + nombreProducto);
            }
        }

        MesaDAO mesa = new MesaDAO();
        mesa.setIdMesa(mesaSeleccionada);
        mesa.setOcupado(true);
        mesa.UPDATE_OCUPADO();

        listaOrden.getItems().clear();
        total = 0;
        actualizarTotal();

        mostrarAlerta(Alert.AlertType.INFORMATION, "Orden Finalizada", "¡Tu orden ha sido guardada exitosamente!");
    }


    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
    private int obtenerIdProductoPorNombre(String nombreProducto) {
        int idProducto = -1;
        String query = "SELECT Id_Producto FROM producto WHERE nombreProducto = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setString(1, nombreProducto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idProducto = rs.getInt("Id_Producto");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idProducto;
    }

    private VBox crearPanelSuperior() {
        VBox panelSuperior = new VBox(20);
        panelSuperior.setPadding(new Insets(10));
        panelSuperior.setAlignment(Pos.TOP_LEFT);

        VBox panelEmpleados = new VBox(5);
        Label lblEmpleado = new Label("MESEROS:");
        comboEmpleados = new ComboBox<>();
        comboEmpleados.setItems(FXCollections.observableArrayList(new EmpleadoDAO().SELECT()));
        comboEmpleados.setPromptText("Meseros");
        panelEmpleados.setId("panelMesero");
        panelEmpleados.setStyle("-fx-background-color: white; -fx-background-radius: 12;");
        panelEmpleados.getChildren().addAll(lblEmpleado, comboEmpleados);

        VBox panelClientes = new VBox(5);
        Label lblCliente = new Label("CLIENTES:");
        comboClientes = new ComboBox<>();
        comboClientes.setItems(FXCollections.observableArrayList(new ClientesDAO().SELECT()));
        comboClientes.setPromptText("Clientes");
        panelClientes.getChildren().addAll(lblCliente, comboClientes);
        panelClientes.setId("panelCliente");
        panelClientes.setStyle("-fx-background-color: white; -fx-background-radius: 12;");

        panelSuperior.getChildren().addAll(panelEmpleados, panelClientes);
        return panelSuperior;
    }

    private VBox crearPanelMesas() {
        VBox panelMesas = new VBox(10);
        panelMesas.setPadding(new Insets(10));
        panelMesas.setAlignment(Pos.TOP_LEFT);

        Label lbl = new Label("MESAS");
        lbl.setFont(Font.font("Arial", 16));
        panelMesas.getChildren().add(lbl);

        MesaDAO mesaDao = new MesaDAO();
        ObservableList<MesaDAO> listaMesas = mesaDao.SELECT();

        Map<Integer, Button> mapaBotonesMesas = new HashMap<>();
        Map<Integer, Integer> capacidadMesas = new HashMap<>();
        int columnas = 5;
        HBox filaActual = new HBox(5);
        int count = 0;

        for (MesaDAO mesa : listaMesas) {
            Button btnMesa = new Button(String.valueOf(mesa.getidMesa()));
            btnMesa.setPrefSize(40, 40);

            btnMesa.setOnAction(e -> {
                mesaSeleccionada = mesa.getidMesa();
            });

            mapaBotonesMesas.put(mesa.getidMesa(), btnMesa);
            capacidadMesas.put(mesa.getidMesa(), mesa.getCapacidad());
            filaActual.getChildren().add(btnMesa);
            count++;

            if (count % columnas == 0) {
                panelMesas.getChildren().add(filaActual);
                filaActual = new HBox(5);
            }
        }

        if (!filaActual.getChildren().isEmpty()) {
            panelMesas.getChildren().add(filaActual);
        }

        HBox acciones = new HBox(10);
        acciones.setAlignment(Pos.CENTER_LEFT);

        Button btnConfirmar = new Button("✅");
        Button btnCancelar = new Button("❌");
        Button btnAyuda = new Button("¿?");

        Label lblCapacidad = new Label();
        lblCapacidad.setFont(Font.font("Arial", 12));
        panelMesas.getChildren().add(lblCapacidad);

        btnConfirmar.setOnAction(e -> {
            if (mesaSeleccionada != -1) {
                Button btn = mapaBotonesMesas.get(mesaSeleccionada);
                if (btn != null) {
                    btn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                }
            }
        });

        btnCancelar.setOnAction(e -> {
            if (mesaSeleccionada != -1) {
                Button btn = mapaBotonesMesas.get(mesaSeleccionada);
                if (btn != null) {
                    btn.setStyle("");
                }
                mesaSeleccionada = -1;
                lblCapacidad.setText("");
            }
        });

        btnAyuda.setOnAction(e -> {
            if (mesaSeleccionada != -1) {
                Integer capacidad = capacidadMesas.get(mesaSeleccionada);
                if (capacidad != null) {
                    lblCapacidad.setText("La capacidad de la mesa seleccionada es: " + capacidad);
                } else {
                    lblCapacidad.setText("No se encontró la capacidad de la mesa seleccionada.");
                }
            }
        });

        acciones.getChildren().addAll(btnConfirmar, btnCancelar, btnAyuda);
        panelMesas.getChildren().add(acciones);

        return panelMesas;
    }

    private void cargarProductos(String nombreCategoria) {
        ProductoDAO productoDAO = new ProductoDAO();
        mapaPrecios.clear();
        ObservableList<HBox> productos = FXCollections.observableArrayList();

        int idCategoria = obtenerIdCategoriaPorNombre(nombreCategoria);
        if (idCategoria == -1) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Categoría no encontrada: " + nombreCategoria);
            return;
        }

        ObservableList<ProductoDAO> lista = productoDAO.obtenerProductosPorIdCategoria(idCategoria);

        for (ProductoDAO producto : lista) {
            HBox productoBox = new HBox(10);
            productoBox.setAlignment(Pos.CENTER_LEFT);

            if (producto.getImagenProducto() != null && !producto.getImagenProducto().isEmpty()) {
                try {
                    Image image = new Image(getClass().getResourceAsStream("/Img/Productos/" + producto.getImagenProducto()));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    productoBox.getChildren().add(imageView);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error al cargar la imagen del producto: " + producto.getImagenProducto());
                }
            }

            Label nombreLabel = new Label(producto.getNombreProducto() + " - $" + producto.getPrecio());
            productoBox.getChildren().add(nombreLabel);

            productos.add(productoBox);
            mapaPrecios.put(producto.getNombreProducto(), producto.getPrecio());
        }

        if (productos.isEmpty()) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "No hay productos", "No hay productos disponibles en esta categoría.");
        } else {
            listaProductos.setItems(productos);
        }
    }

    private int obtenerIdCategoriaPorNombre(String nombreCategoria) {
        int idCategoria = -1;
        String query = "SELECT Id_Categoria FROM categoria WHERE nombreCategoria = '" + nombreCategoria + "'";
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                idCategoria = rs.getInt("Id_Categoria");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idCategoria;
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

    private Button crearBotonMenu(String texto) {
        Button btn = new Button(texto);
        btn.setFont(Font.font("Arial", 13));
        btn.setPrefWidth(160);
        return btn;
    }
}

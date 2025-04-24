package com.example.tap2025.vistas;

import com.example.tap2025.componentes.ButtonCell3;
import com.example.tap2025.modelos.ProductoDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaProducto extends Stage {

    private ToolBar tlbMenu;
    private TableView<ProductoDAO> tbvProductos;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaProducto() {
        CrearUI();
        this.setTitle("Listado de Productos");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvProductos = new TableView<>();
        btnAgregar = new Button();
        btnAgregar.setOnAction(event -> new Producto(tbvProductos, null));
        ImageView imv = new ImageView(getClass().getResource("/Img/3.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);
        tlbMenu = new ToolBar(btnAgregar);
        CreateTable();
        vBox = new VBox(tlbMenu, tbvProductos);
        escena = new Scene(vBox, 800, 600);
    }

    private void CreateTable() {
        ProductoDAO objP = new ProductoDAO();

        TableColumn<ProductoDAO, String> tbcNombreProducto = new TableColumn<>("Producto");
        tbcNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));

        TableColumn<ProductoDAO, Double> tbcprecio = new TableColumn<>("Precio");
        tbcprecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<ProductoDAO, Integer> tbcCategoria = new TableColumn<>("Categoría");
        tbcCategoria.setCellValueFactory(new PropertyValueFactory<>("Id_Categoria"));

        TableColumn<ProductoDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<ProductoDAO, String>, TableCell<ProductoDAO, String>>() {
            @Override
            public TableCell<ProductoDAO, String> call(TableColumn<ProductoDAO, String> param) {
                return new ButtonCell3("Editar");
            }
        });

        TableColumn<ProductoDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<ProductoDAO, String>, TableCell<ProductoDAO, String>>() {
            @Override
            public TableCell<ProductoDAO, String> call(TableColumn<ProductoDAO, String> param) {
                return new ButtonCell3("Eliminar");
            }
        });

        tbvProductos.getColumns().addAll(tbcNombreProducto, tbcprecio, tbcCategoria, tbcEditar, tbcEliminar);
        tbvProductos.setItems(objP.SELECT());
    }
}


package com.example.tap2025.vistas;

import com.example.tap2025.componentes.ButtonCell5;
import com.example.tap2025.modelos.OrdenDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaOrden extends Stage {

    private ToolBar tlbMenu;
    private TableView<OrdenDAO> tbvOrdenes;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaOrden() {
        CrearUI();
        this.setTitle("Listado de Órdenes");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvOrdenes = new TableView<>();
        btnAgregar = new Button();
        btnAgregar.setOnAction(event -> new Orden(tbvOrdenes, null));
        ImageView imv = new ImageView(getClass().getResource("/Img/3.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);
        tlbMenu = new ToolBar(btnAgregar);
        CreateTable();
        vBox = new VBox(tlbMenu, tbvOrdenes);
        escena = new Scene(vBox, 900, 600);
    }

    private void CreateTable() {
        OrdenDAO objO = new OrdenDAO();

        TableColumn<OrdenDAO, String> tbcDetalles = new TableColumn<>("Detalles");
        tbcDetalles.setCellValueFactory(new PropertyValueFactory<>("detallesOrden"));

        TableColumn<OrdenDAO, Integer> tbcMesa = new TableColumn<>("Mesa");
        tbcMesa.setCellValueFactory(new PropertyValueFactory<>("idMesa"));

        TableColumn<OrdenDAO, Integer> tbcEmpleado = new TableColumn<>("Empleado");
        tbcEmpleado.setCellValueFactory(new PropertyValueFactory<>("Id_Empleado"));

        TableColumn<OrdenDAO, Integer> tbcCliente = new TableColumn<>("Cliente");
        tbcCliente.setCellValueFactory(new PropertyValueFactory<>("idCte"));

        TableColumn<OrdenDAO, String> tbcFecha = new TableColumn<>("Fecha");
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        TableColumn<OrdenDAO, Double> tbcTotal = new TableColumn<>("Total");
        tbcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        // Opcional: columnas para editar y eliminar si tienes ButtonCell como en Clientes
        TableColumn<OrdenDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<OrdenDAO, String>, TableCell<OrdenDAO, String>>() {
            @Override
            public TableCell<OrdenDAO, String> call(TableColumn<OrdenDAO, String> param) {
                return new ButtonCell5("Editar");
            }
        });

        TableColumn<OrdenDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<OrdenDAO, String>, TableCell<OrdenDAO, String>>() {
            @Override
            public TableCell<OrdenDAO, String> call(TableColumn<OrdenDAO, String> param) {
                return new ButtonCell5("Eliminar");
            }
        });

        tbvOrdenes.getColumns().addAll( tbcDetalles, tbcMesa, tbcEmpleado, tbcCliente, tbcFecha, tbcTotal, tbcEditar, tbcEliminar);
        tbvOrdenes.setItems(objO.SELECT());
    }
}

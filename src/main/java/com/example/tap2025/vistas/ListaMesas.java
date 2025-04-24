package com.example.tap2025.vistas;

import com.example.tap2025.componentes.ButtonCell4;
import com.example.tap2025.modelos.MesaDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaMesas extends Stage {

    private ToolBar tlbMenu;
    private TableView<MesaDAO> tbvMesas;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaMesas() {
        CrearUI();
        this.setTitle("Listado de Mesas :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvMesas = new TableView<>();
        btnAgregar = new Button();
        btnAgregar.setOnAction(event -> new Mesa(tbvMesas, null));
        ImageView imv = new ImageView(getClass().getResource("/Img/3.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);
        tlbMenu = new ToolBar(btnAgregar);
        CreateTable();
        vBox = new VBox(tlbMenu, tbvMesas);
        escena = new Scene(vBox, 800, 600);
    }

    private void CreateTable() {
        MesaDAO objM = new MesaDAO();

        TableColumn<MesaDAO, Integer> tbcCapacidad = new TableColumn<>("Capacidad");
        tbcCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));

        TableColumn<MesaDAO, Boolean> tbcOcupado = new TableColumn<>("Ocupado");
        tbcOcupado.setCellValueFactory(new PropertyValueFactory<>("ocupado"));

        TableColumn<MesaDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<MesaDAO, String>, TableCell<MesaDAO, String>>() {
            @Override
            public TableCell<MesaDAO, String> call(TableColumn<MesaDAO, String> param) {
                return new ButtonCell4("Editar");
            }
        });

        TableColumn<MesaDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<MesaDAO, String>, TableCell<MesaDAO, String>>() {
            @Override
            public TableCell<MesaDAO, String> call(TableColumn<MesaDAO, String> param) {
                return new ButtonCell4("Eliminar");
            }
        });

        tbvMesas.getColumns().addAll(tbcCapacidad, tbcOcupado, tbcEditar, tbcEliminar);
        tbvMesas.setItems(objM.SELECT());
    }
}

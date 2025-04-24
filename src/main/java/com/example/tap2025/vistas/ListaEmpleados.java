package com.example.tap2025.vistas;

import com.example.tap2025.componentes.ButtonCell2;
import com.example.tap2025.modelos.EmpleadoDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaEmpleados extends Stage {

    private ToolBar tlbMenu;
    private TableView<EmpleadoDAO> tbvEmpleados;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaEmpleados() {
        CrearUI();
        this.setTitle("Listado de Empleados :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvEmpleados = new TableView<>();
        btnAgregar = new Button();
        btnAgregar.setOnAction(event -> new Empleado(tbvEmpleados, null)); // Cambiar Cliente a Empleado
        ImageView imv = new ImageView(getClass().getResource("/Img/3.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);
        tlbMenu = new ToolBar(btnAgregar);
        CreateTable();
        vBox = new VBox(tlbMenu, tbvEmpleados); // Cambiar tbvClientes a tbvEmpleados
        escena = new Scene(vBox, 800, 600);
    }

    private void CreateTable() {
        EmpleadoDAO objE = new EmpleadoDAO();

        TableColumn<EmpleadoDAO, String> tbcNombre = new TableColumn<>("Nombre");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<EmpleadoDAO, String> tbcApellido1 = new TableColumn<>("Apellido 1");
        tbcApellido1.setCellValueFactory(new PropertyValueFactory<>("apellido1"));

        TableColumn<EmpleadoDAO, String> tbcApellido2 = new TableColumn<>("Apellido 2");
        tbcApellido2.setCellValueFactory(new PropertyValueFactory<>("apellido2"));

        TableColumn<EmpleadoDAO, String> tbcCURP = new TableColumn<>("CURP");
        tbcCURP.setCellValueFactory(new PropertyValueFactory<>("CURP"));

        TableColumn<EmpleadoDAO, String> tbcRFC = new TableColumn<>("RFC");
        tbcRFC.setCellValueFactory(new PropertyValueFactory<>("RFC"));

        TableColumn<EmpleadoDAO, String> tbcNSS = new TableColumn<>("NSS");
        tbcNSS.setCellValueFactory(new PropertyValueFactory<>("NSS"));

        TableColumn<EmpleadoDAO, String> tbcSueldo = new TableColumn<>("Sueldo");
        tbcSueldo.setCellValueFactory(new PropertyValueFactory<>("sueldo"));

        TableColumn<EmpleadoDAO, String> tbcPuesto = new TableColumn<>("Puesto");
        tbcPuesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));

        TableColumn<EmpleadoDAO, String> tbcNoContacto = new TableColumn<>("Contacto");
        tbcNoContacto.setCellValueFactory(new PropertyValueFactory<>("noContacto"));

        TableColumn<EmpleadoDAO, String> tbcHorario = new TableColumn<>("Horario");
        tbcHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));

        TableColumn<EmpleadoDAO, String> tbcFechaIngreso = new TableColumn<>("Fecha Ingreso");
        tbcFechaIngreso.setCellValueFactory(new PropertyValueFactory<>("fechaIngreso"));

        TableColumn<EmpleadoDAO,String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<EmpleadoDAO, String>, TableCell<EmpleadoDAO, String>>() {
            @Override
            public TableCell<EmpleadoDAO, String> call(TableColumn<EmpleadoDAO, String> param) {
                return new ButtonCell2("Editar");
            }
        });
        TableColumn<EmpleadoDAO,String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<EmpleadoDAO, String>, TableCell<EmpleadoDAO, String>>() {
            @Override
            public TableCell<EmpleadoDAO, String> call(TableColumn<EmpleadoDAO, String> param) {
                return new ButtonCell2("Eliminar");
            }
        });


        tbvEmpleados.getColumns().addAll(tbcNombre, tbcApellido1, tbcApellido2, tbcCURP, tbcRFC, tbcNSS, tbcSueldo, tbcPuesto, tbcNoContacto, tbcHorario, tbcFechaIngreso, tbcEditar, tbcEliminar);
        tbvEmpleados.setItems(objE.SELECT());
    }
}


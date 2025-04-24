
package com.example.tap2025.vistas;

import com.example.tap2025.modelos.OrdenDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Orden extends Stage {

    private Button btnGuardar;
    private TextField txtDetalles, txtMesa, txtEmpleado, txtFecha, txtTotal;
    private VBox vBox;
    private Scene escena;
    private OrdenDAO objO;
    private TableView<OrdenDAO> tbvOrdenes;

    public Orden(TableView<OrdenDAO> tbvOrd, OrdenDAO obj) {
        this.tbvOrdenes = tbvOrd;
        CrearUI();
        if (obj == null) {
            objO = new OrdenDAO();
        } else {
            objO = obj;
            txtDetalles.setText(objO.getDetallesOrden());
            txtMesa.setText(String.valueOf(objO.getIdMesa()));
            txtEmpleado.setText(String.valueOf(objO.getId_Empleado()));
            txtFecha.setText(objO.getFecha());
            txtTotal.setText(String.valueOf(objO.getTotal()));
        }
        this.setTitle("Registrar Orden");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtDetalles = new TextField();
        txtMesa = new TextField();
        txtEmpleado = new TextField();
        txtFecha = new TextField();
        txtTotal = new TextField();
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> {
            objO.setDetallesOrden(txtDetalles.getText());
            objO.setIdMesa(Integer.parseInt(txtMesa.getText()));
            objO.setIdEmpleado(Integer.parseInt(txtEmpleado.getText()));
            objO.setFecha(txtFecha.getText());
            objO.setTotal(Double.parseDouble(txtTotal.getText()));
            objO.INSERT();
            tbvOrdenes.setItems(objO.SELECT());
            tbvOrdenes.refresh();
            this.close();
        });
        vBox = new VBox(txtDetalles, txtMesa, txtEmpleado, txtFecha, txtTotal, btnGuardar);
        escena = new Scene(vBox, 200, 200);
    }
}


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
    private TextField txtDetalles, txtMesa,txtCliente , txtEmpleado, txtFecha, txtTotal;
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
            txtMesa.setText(String.valueOf(objO.getidMesa()));
            txtEmpleado.setText(String.valueOf(objO.getId_Empleado()));
            txtCliente.setText(String.valueOf(objO.getidCte()));
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
        txtCliente = new TextField();
        txtFecha = new TextField();
        txtTotal = new TextField();
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> {
            objO.setDetallesOrden(txtDetalles.getText());
            objO.setidMesa(Integer.parseInt(txtMesa.getText()));
            objO.setId_Empleado(Integer.parseInt(txtEmpleado.getText()));
            objO.setidCte(Integer.parseInt(txtCliente.getText()));
            objO.setFecha(txtFecha.getText());
            objO.setTotal(Double.parseDouble(txtTotal.getText()));
            if( objO.getId_Orden() > 0 )
                objO.UPDATE();
            else
                objO.INSERT();
            tbvOrdenes.setItems(objO.SELECT());
            tbvOrdenes.refresh();
            this.close();
        });
        vBox = new VBox(txtDetalles, txtMesa, txtEmpleado, txtCliente, txtFecha, txtTotal, btnGuardar);
        escena = new Scene(vBox, 200, 200);
    }
}

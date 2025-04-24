package com.example.tap2025.vistas;

import com.example.tap2025.modelos.EmpleadoDAO; // Cambiar de ClientesDAO a EmpleadosDAO
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Empleado extends Stage {

    private Button btnGuardar;
    private TextField txtNombre, txtApellido1, txtApellido2, txtCURP, txtRFC, txtNSS, txtSueldo, txtPuesto, txtNoContacto, txtHorario, txtFechaIngreso;
    private VBox vBox;
    private Scene escena;
    private EmpleadoDAO objE;
    private TableView<EmpleadoDAO> tbvEmpleados;

    public Empleado(TableView<EmpleadoDAO> tbvEmp, EmpleadoDAO obj) {
        this.tbvEmpleados = tbvEmp;
        CrearUI();
        if (obj == null) {
            objE = new EmpleadoDAO();
        } else {
            objE = obj;
            txtNombre.setText(objE.getNombre());
            txtApellido1.setText(objE.getApellido1());
            txtApellido2.setText(objE.getApellido2());
            txtCURP.setText(objE.getCURP());
            txtRFC.setText(objE.getRFC());
            txtNSS.setText(objE.getNSS());
            txtSueldo.setText(objE.getSueldo());
            txtPuesto.setText(objE.getPuesto());
            txtNoContacto.setText(objE.getNoContacto());
            txtHorario.setText(objE.getHorario());
            txtFechaIngreso.setText(objE.getFechaIngreso().toString());
        }
        this.setTitle("Registrar Empleado");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtNombre = new TextField();
        txtApellido1 = new TextField();
        txtApellido2 = new TextField();
        txtCURP = new TextField();
        txtRFC = new TextField();
        txtNSS = new TextField();
        txtSueldo = new TextField();
        txtPuesto = new TextField();
        txtNoContacto = new TextField();
        txtHorario = new TextField();
        txtFechaIngreso = new TextField();

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> {
            objE.setNombre(txtNombre.getText());
            objE.setApellido1(txtApellido1.getText());
            objE.setApellido2(txtApellido2.getText());
            objE.setCURP(txtCURP.getText());
            objE.setRFC(txtRFC.getText());
            objE.setNSS(txtNSS.getText());
            objE.setSueldo(Double.parseDouble(txtSueldo.getText()));
            objE.setPuesto(txtPuesto.getText());
            objE.setNoContacto(txtNoContacto.getText());
            objE.setHorario(txtHorario.getText());
            objE.setFechaIngreso(txtFechaIngreso.getText());

            if (objE.getId_Empleado() > 0) {
                objE.UPDATE();
            } else {
                objE.INSERT();
            }
            tbvEmpleados.setItems(objE.SELECT());
            tbvEmpleados.refresh();
            this.close();
        });

        vBox = new VBox(txtNombre, txtApellido1, txtApellido2, txtCURP, txtRFC, txtNSS, txtSueldo, txtPuesto, txtNoContacto, txtHorario, txtFechaIngreso, btnGuardar);
        escena = new Scene(vBox, 300, 400);
    }
}

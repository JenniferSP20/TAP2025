package com.example.tap2025.vistas;

import com.example.tap2025.modelos.MesaDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Mesa extends Stage {

    private Button btnGuardar;
    private TextField txtIdMesa, txtCapacidad;
    private CheckBox chkOcupado;
    private VBox vBox;
    private Scene escena;
    private MesaDAO objM;
    private TableView<MesaDAO> tbvMesas;

    public Mesa(TableView<MesaDAO> tbvMesas, MesaDAO obj) {
        this.tbvMesas = tbvMesas;
        CrearUI();
        if (obj == null) {
            objM = new MesaDAO();
        } else {
            objM = obj;
            txtIdMesa.setText(String.valueOf(objM.getIdMesa()));
            txtCapacidad.setText(String.valueOf(objM.getCapacidad()));
            chkOcupado.setSelected(objM.isOcupado());
        }
        this.setTitle("Registrar Mesa");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtIdMesa = new TextField();
        txtIdMesa.setPromptText("ID Mesa");
        txtCapacidad = new TextField();
        txtCapacidad.setPromptText("Capacidad");
        chkOcupado = new CheckBox("Ocupado");
        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            objM.setIdMesa(Integer.parseInt(txtIdMesa.getText()));
            objM.setCapacidad(Integer.parseInt(txtCapacidad.getText()));
            objM.setOcupado(chkOcupado.isSelected());

            if (objM.getIdMesa() > 0) {
                objM.UPDATE_OCUPADO();
            } else {
                objM.INSERT();
            }

            tbvMesas.setItems(objM.SELECT());
            tbvMesas.refresh();
            this.close();
        });

        vBox = new VBox(txtIdMesa, txtCapacidad, chkOcupado, btnGuardar);
        escena = new Scene(vBox, 200, 200);
    }
}

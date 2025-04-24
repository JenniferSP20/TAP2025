package com.example.tap2025.componentes;

import com.example.tap2025.modelos.ProductoDAO;
import com.example.tap2025.vistas.Producto;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;


import java.util.Optional;

public class ButtonCell3 extends TableCell<ProductoDAO, String> {

    private Button btnCelda;
    private String strLabelBtn;
    public ButtonCell3(String label){

        strLabelBtn = label;
        btnCelda = new Button(strLabelBtn);
        btnCelda.setOnAction(event -> {
           ProductoDAO objP = this.getTableView().getItems().get(this.getIndex());
            if( strLabelBtn.equals("Editar")){
                new Producto(this.getTableView(),objP);
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mensaje del Sistema :)");
                alert.setContentText("¿Deseas eliminar el registro seleccionado?");
                Optional<ButtonType> opcion = alert.showAndWait();
                if( opcion.get() == ButtonType.OK ){
                    objP.DELETE();
                }
            }
            this.getTableView().setItems(objP.SELECT());
            this.getTableView().refresh();
        });
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if( !empty )
            this.setGraphic(btnCelda);
    }
}
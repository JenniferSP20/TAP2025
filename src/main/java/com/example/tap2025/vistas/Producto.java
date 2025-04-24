package com.example.tap2025.vistas;

import com.example.tap2025.modelos.ProductoDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Producto extends Stage {

    private Button btnGuardar;
    private TextField txtNombreProducto, txtPrecio, txtCategoria;
    private VBox vBox;
    private Scene escena;
    private ProductoDAO objP;
    private TableView<ProductoDAO> tbvProductos;

    public Producto(TableView<ProductoDAO> tbvProd, ProductoDAO obj) {
        this.tbvProductos = tbvProd;
        CrearUI();
        if (obj == null) {
            objP = new ProductoDAO();
        } else {
            objP = obj;
            txtNombreProducto.setText(objP.getNombreProducto());
            txtPrecio.setText(String.valueOf(objP.getPrecio()));
            txtCategoria.setText(String.valueOf(objP.getId_Categoria()));
        }
        this.setTitle("Registrar Producto");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtNombreProducto = new TextField();
        txtPrecio = new TextField();
        txtCategoria = new TextField();
        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            objP.setNombreProducto(txtNombreProducto.getText());
            objP.setPrecio(Double.parseDouble(txtPrecio.getText()));
            objP.setId_Categoria(Integer.parseInt(txtCategoria.getText()));

            if (objP.getId_Producto() > 0) {
                objP.UPDATE();
            } else {
                objP.INSERT();
            }

            tbvProductos.setItems(objP.SELECT());
            tbvProductos.refresh();
            this.close();
        });

        vBox = new VBox(txtNombreProducto, txtPrecio, txtCategoria, btnGuardar);
        escena = new Scene(vBox, 200, 250);
    }
}

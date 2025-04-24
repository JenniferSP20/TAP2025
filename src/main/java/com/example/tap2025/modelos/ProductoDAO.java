package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class ProductoDAO {

    private int Id_Producto;
    private String nombreProducto;
    private double precio;
    private int Id_Categoria;
    private String imagenProducto; // Ruta de la imagen asociada al producto

    public int getId_Producto() {
        return Id_Producto;
    }

    public void setId_Producto(int Id_Producto) {
        this.Id_Producto = Id_Producto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getId_Categoria() {
        return Id_Categoria;
    }

    public void setId_Categoria(int Id_Categoria) {
        this.Id_Categoria = Id_Categoria;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public void INSERT() {
        String query = "INSERT INTO producto (nombreProducto, precio, Id_Categoria, imagenProducto) " +
                "VALUES ('" + nombreProducto + "', " + precio + ", " + Id_Categoria + ", '" + imagenProducto + "')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UPDATE() {
        String query = "UPDATE producto SET nombreProducto = '" + nombreProducto + "', precio = " + precio +
                ", Id_Categoria = " + Id_Categoria + ", imagenProducto = '" + imagenProducto + "' WHERE Id_Producto = " + Id_Producto;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM producto WHERE Id_Producto = " + Id_Producto;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ProductoDAO> SELECT() {
        String query = "SELECT * FROM producto";
        ObservableList<ProductoDAO> listaP = FXCollections.observableArrayList();
        ProductoDAO objP;
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                objP = new ProductoDAO();
                objP.setId_Producto(res.getInt("Id_Producto"));
                objP.setNombreProducto(res.getString("nombreProducto"));
                objP.setPrecio(res.getDouble("precio"));
                objP.setId_Categoria(res.getInt("Id_Categoria"));
                objP.setImagenProducto(res.getString("imagenProducto"));
                listaP.add(objP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaP;
    }

    public ObservableList<ProductoDAO> obtenerProductosPorIdCategoria(int idCategoria) {
        ObservableList<ProductoDAO> listaP = FXCollections.observableArrayList();
        String query = "SELECT * FROM producto WHERE Id_Categoria = " + idCategoria;
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                ProductoDAO objP = new ProductoDAO();
                objP.setId_Producto(res.getInt("Id_Producto"));
                objP.setNombreProducto(res.getString("nombreProducto"));
                objP.setPrecio(res.getDouble("precio"));
                objP.setId_Categoria(res.getInt("Id_Categoria"));
                objP.setImagenProducto(res.getString("imagenProducto"));
                listaP.add(objP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaP;
    }
}


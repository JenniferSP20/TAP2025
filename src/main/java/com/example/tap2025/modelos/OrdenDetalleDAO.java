package com.example.tap2025.modelos;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrdenDetalleDAO {

    private int Id_Orden;
    private int Id_Producto;
    private int cantidad;

    public OrdenDetalleDAO() {}

    public OrdenDetalleDAO(int Id_Orden, int Id_Producto, int cantidad) {
        this.Id_Orden = Id_Orden;
        this.Id_Producto = Id_Producto;
        this.cantidad = cantidad;
    }

    public int getId_Orden() {
        return Id_Orden;
    }

    public void setId_Orden(int Id_Orden) {
        this.Id_Orden = Id_Orden;
    }

    public int getId_Producto() {
        return Id_Producto;
    }

    public void setId_Producto(int Id_Producto) {
        this.Id_Producto = Id_Producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void INSERT() {
        String query = "INSERT INTO orden_detalle (Id_Orden, Id_Producto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, Id_Orden);
            stmt.setInt(2, Id_Producto);
            stmt.setInt(3, cantidad);
            stmt.executeUpdate();
            System.out.println("Datos insertados en orden_detalle: Id_Orden=" + Id_Orden + ", Id_Producto=" + Id_Producto + ", Cantidad=" + cantidad);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al insertar en orden_detalle: " + e.getMessage());
        }
    }
}

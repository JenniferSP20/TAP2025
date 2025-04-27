package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InsumoDAO {
    private int Id_Insumo;
    private String nombreInsumo;
    private String unidadMedida;
    private double cantidad;

    public int getId_Insumo() {
        return Id_Insumo;
    }

    public void setId_Insumo(int Id_Insumo) {
        this.Id_Insumo = Id_Insumo;
    }

    public String getNombreInsumo() {
        return nombreInsumo;
    }

    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public List<InsumoDAO> obtenerInsumosPorIdProducto(int Id_Producto) {
        List<InsumoDAO> insumos = new ArrayList<>();
        String query = "SELECT i.Id_Insumo, i.nombreInsumo, pi.cantidad, i.unidadMedida " +
                "FROM insumo i " +
                "JOIN producto_insumo pi ON i.Id_Insumo = pi.Id_Insumo " +
                "WHERE pi.Id_Producto = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, Id_Producto);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                InsumoDAO insumo = new InsumoDAO();
                insumo.setId_Insumo(rs.getInt("Id_Insumo"));
                insumo.setNombreInsumo(rs.getString("nombreInsumo"));
                insumo.setUnidadMedida(rs.getString("unidadMedida"));
                insumos.add(insumo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insumos;
    }

    public void INSERT(int Id_Producto) {
        String query = "INSERT INTO producto_insumo (Id_Producto, Id_Insumo, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, Id_Producto);
            stmt.setInt(2, this.Id_Insumo);
            stmt.setDouble(3, this.cantidad);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

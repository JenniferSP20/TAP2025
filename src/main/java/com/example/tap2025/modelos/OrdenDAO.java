package com.example.tap2025.modelos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class OrdenDAO {

    private int Id_Orden;
    private String detallesOrden;
    private int idMesa;
    private int Id_Empleado;
    private int idCte;
    private String fecha;
    private double total;

    public int getId_Orden() {
        return Id_Orden;
    }

    public void setId_Orden(int Id_Orden) {
        this.Id_Orden = Id_Orden;
    }

    public String getDetallesOrden() {
        return detallesOrden;
    }

    public void setDetallesOrden(String detallesOrden) {
        this.detallesOrden = detallesOrden;
    }

    public int getidMesa() {
        return idMesa;
    }

    public void setidMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getId_Empleado() {
        return Id_Empleado;
    }

    public void setId_Empleado(int Id_Empleado) {
        this.Id_Empleado = Id_Empleado;
    }

    public int getidCte() {
        return idCte;
    }

    public void setidCte(int idCte) {
        this.idCte = idCte;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    public void UPDATE() {
        String query = "UPDATE orden SET detallesOrden = ?, idMesa = ?, Id_Empleado = ?, idCte = ?, fecha = ?, total = ? WHERE Id_Orden = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setString(1, detallesOrden);
            stmt.setInt(2, idMesa);
            stmt.setInt(3, Id_Empleado);
            stmt.setInt(4, idCte);
            stmt.setString(5, fecha);
            stmt.setDouble(6, total);
            stmt.setInt(7, Id_Orden);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DELETE(){
        String query = "DELETE FROM orden WHERE Id_Orden = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, Id_Orden);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void INSERT() {
        String query = "INSERT INTO orden(detallesOrden, idMesa, Id_Empleado, idCte, fecha, total) VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, detallesOrden);
            stmt.setInt(2, idMesa);
            stmt.setInt(3, Id_Empleado);
            stmt.setInt(4, idCte);
            stmt.setString(5, fecha);
            stmt.setDouble(6, total);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.Id_Orden = generatedKeys.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getLastInsertedId() {
        return this.Id_Orden;
    }
}
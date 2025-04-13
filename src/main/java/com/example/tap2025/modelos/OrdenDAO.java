package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class OrdenDAO {

    private int idOrden;
    private String detallesOrden;
    private int idMesa;
    private int idEmpleado;
    private int idCliente;
    private String fecha;
    private double total;

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public String getDetallesOrden() {
        return detallesOrden;
    }

    public void setDetallesOrden(String detallesOrden) {
        this.detallesOrden = detallesOrden;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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

    public void INSERT() {
        String query = "INSERT INTO orden(detallesOrden, Id_Mesa, Id_Empleado, Id_Cliente, fecha, total) " +
                "VALUES('" + detallesOrden + "'," + idMesa + "," + idEmpleado + "," + idCliente + ",'" + fecha + "'," + total + ")";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<OrdenDAO> SELECT() {
        String query = "SELECT * FROM orden";
        ObservableList<OrdenDAO> lista = FXCollections.observableArrayList();
        OrdenDAO obj;
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                obj = new OrdenDAO();
                obj.setIdOrden(res.getInt("Id_Orden"));
                obj.setDetallesOrden(res.getString("detallesOrden"));
                obj.setIdMesa(res.getInt("Id_Mesa"));
                obj.setIdEmpleado(res.getInt("Id_Empleado"));
                obj.setIdCliente(res.getInt("Id_Cliente"));
                obj.setFecha(res.getString("fecha"));
                obj.setTotal(res.getDouble("total"));
                lista.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}

package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getId_Empleado() {
        return Id_Empleado;
    }

    public void setIdEmpleado(int Id_Empleado) {
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
    public void UPDATE(){
        String query = "UPDATE orden SET detallesOrden = '"+detallesOrden+"'," +
                "idMesa = '"+idMesa+"',Id_Empleado = '"+Id_Empleado+"'," +
                "idCte = '"+idCte+"',"+"fecha = '"+fecha+"', "+"total = '"+total+"', WHERE Id_Orden = "+Id_Orden;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void DELETE(){
        String query = "DELETE FROM orden WHERE Id_Orden = "+Id_Orden;
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void INSERT() {
        String query = "INSERT INTO orden(detallesOrden, Id_Mesa, Id_Empleado, idCte, fecha, total) " +
                "VALUES('" + detallesOrden + "'," + idMesa + "," + Id_Empleado + "," + idCte + ",'" + fecha + "'," + total + ")";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<OrdenDAO> SELECT() {
        String query = "SELECT * FROM orden";
        ObservableList<OrdenDAO> listaO = FXCollections.observableArrayList();
        OrdenDAO objO;
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                objO = new OrdenDAO();
                objO.setId_Orden(res.getInt("Id_Orden"));
                objO.setDetallesOrden(res.getString("detallesOrden"));
                objO.setIdMesa(res.getInt("Id_Mesa"));
                objO.setIdEmpleado(res.getInt("Id_Empleado"));
                objO.setidCte(res.getInt("idCte"));
                objO.setFecha(res.getString("fecha"));
                objO.setTotal(res.getDouble("total"));
                listaO.add(objO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaO;
    }
}

package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class ReservacionDAO {

    private int idReservacion;
    private int idCte;
    private int idMesa;
    private String fecha;
    private String hora;
    private int numPersonas;

    public int getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(int idReservacion) {
        this.idReservacion = idReservacion;
    }

    public int getIdCte() {
        return idCte;
    }

    public void setIdCte(int idCte) {
        this.idCte = idCte;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getNumPersonas() {
        return numPersonas;
    }

    public void setNumPersonas(int numPersonas) {
        this.numPersonas = numPersonas;
    }

    public void INSERT() {
        String query = "INSERT INTO reservacion(idCte, idMesa, fecha, hora, numPersonas) " +
                "VALUES(" + idCte + ", " + idMesa + ", '" + fecha + "', '" + hora + "', " + numPersonas + ")";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UPDATE() {
        String query = "UPDATE reservacion SET idCte = " + idCte + ", idMesa = " + idMesa + ", fecha = '" + fecha + "', hora = '" + hora + "', numPersonas = " + numPersonas + " WHERE idReservacion = " + idReservacion;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM reservacion WHERE idReservacion = " + idReservacion;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ReservacionDAO> SELECT() {
        String query = "SELECT * FROM reservacion";
        ObservableList<ReservacionDAO> listaR = FXCollections.observableArrayList();
        ReservacionDAO objR;
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                objR = new ReservacionDAO();
                objR.setIdReservacion(res.getInt("idReservacion"));
                objR.setIdCte(res.getInt("idCte"));
                objR.setIdMesa(res.getInt("idMesa"));
                objR.setFecha(res.getString("fecha"));
                objR.setHora(res.getString("hora"));
                objR.setNumPersonas(res.getInt("numPersonas"));
                listaR.add(objR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaR;
    }

}


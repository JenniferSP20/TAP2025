package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MesaDAO {
    private int idMesa;
    private int capacidad;
    private boolean ocupado;

    public int getidMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
    public void UPDATE(){
        String query = "UPDATE mesa SET capacidad = '"+capacidad+"'," +
                "ocupado = '"+ocupado+"' WHERE idMesa = "+idMesa;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void UPDATE_OCUPADO() {
        String query = "UPDATE mesa SET ocupado = ? WHERE idMesa = ?";
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setBoolean(1, ocupado);
            stmt.setInt(2, idMesa);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void DELETE(){
        String query = "DELETE FROM mesa WHERE idMesa = "+idMesa;
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void INSERT(){
        String query = "INSERT INTO mesa (idMesa, capacidad, ocupado) " +
                "values('"+idMesa+"','"+capacidad+"','"+ocupado+"')";
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<MesaDAO> SELECT() {
        ObservableList<MesaDAO> listaM = FXCollections.observableArrayList();
        String query = "SELECT * FROM mesa";
        MesaDAO objM;
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                objM = new MesaDAO();
                objM.setIdMesa(res.getInt("idMesa"));
                objM.setCapacidad(Integer.parseInt(res.getString("capacidad")));
                objM.setOcupado(Boolean.parseBoolean(res.getString("ocupado")));
                listaM.add(objM);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaM;
    }

}

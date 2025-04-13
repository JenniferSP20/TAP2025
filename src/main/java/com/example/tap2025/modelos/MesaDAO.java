package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MesaDAO {
    private int idMesa;
    private boolean ocupado;

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }


    public void UPDATE_OCUPADO() {
        String query = "UPDATE mesa SET ocupado = ? WHERE Id_Mesa = ?";
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setBoolean(1, ocupado);
            stmt.setInt(2, idMesa);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ObservableList<MesaDAO> SELECT() {
        ObservableList<MesaDAO> lista = FXCollections.observableArrayList();
        String query = "SELECT * FROM mesa";
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MesaDAO m = new MesaDAO();
                m.setIdMesa(rs.getInt("idMesa"));
                m.setOcupado(rs.getBoolean("ocupado"));
                lista.add(m);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}


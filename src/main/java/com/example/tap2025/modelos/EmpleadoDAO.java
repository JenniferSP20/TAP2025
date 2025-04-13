package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class EmpleadoDAO {

    private int idEmpleado;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String curp;
    private String rfc;
    private String nss;
    private double sueldo;
    private String puesto;
    private String noContacto;
    private String horario;
    private String fechaIngreso;

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getNoContacto() {
        return noContacto;
    }

    public void setNoContacto(String noContacto) {
        this.noContacto = noContacto;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public ObservableList<EmpleadoDAO> SELECT() {
        String query = "SELECT * FROM empleados";
        ObservableList<EmpleadoDAO> lista = FXCollections.observableArrayList();
        EmpleadoDAO obj;
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                obj = new EmpleadoDAO();
                obj.setIdEmpleado(res.getInt("Id_Empleado"));
                obj.setNombre(res.getString("nombre"));
                obj.setApellido1(res.getString("apellido1"));
                obj.setApellido2(res.getString("apellido2"));
                obj.setCurp(res.getString("CURP"));
                obj.setRfc(res.getString("RFC"));
                obj.setNss(res.getString("NSS"));
                obj.setSueldo(res.getDouble("sueldo"));
                obj.setPuesto(res.getString("puesto"));
                obj.setNoContacto(res.getString("noContacto"));
                obj.setHorario(res.getString("horario"));
                obj.setFechaIngreso(res.getString("fechaIngreso"));
                lista.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}

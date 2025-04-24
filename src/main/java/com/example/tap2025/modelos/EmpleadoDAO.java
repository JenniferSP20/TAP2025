package com.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class EmpleadoDAO {

    private int Id_Empleado;
    private String Nombre;
    private String Apellido1;
    private String Apellido2;
    private String CURP;
    private String RFC;
    private String NSS;
    private double Sueldo;
    private String Puesto;
    private String NoContacto;
    private String Horario;
    private String FechaIngreso;

    public int getId_Empleado() {
        return Id_Empleado;
    }

    public void setId_Empleado(int Id_Empleado) {
        this.Id_Empleado = Id_Empleado;
    }

    // Método adicional para compatibilidad con otras clases
    public int getIdEmpleado() {
        return Id_Empleado;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido1() {
        return Apellido1;
    }

    public void setApellido1(String Apellido1) {
        this.Apellido1 = Apellido1;
    }

    public String getApellido2() {
        return Apellido2;
    }

    public void setApellido2(String Apellido2) {
        this.Apellido2 = Apellido2;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getNSS() {
        return NSS;
    }

    public void setNSS(String NSS) {
        this.NSS = NSS;
    }

    public String getSueldo() {
        return String.valueOf(Sueldo);
    }

    public void setSueldo(double Sueldo) {
        this.Sueldo = Sueldo;
    }

    public String getPuesto() {
        return Puesto;
    }

    public void setPuesto(String Puesto) {
        this.Puesto = Puesto;
    }

    public String getNoContacto() {
        return NoContacto;
    }

    public void setNoContacto(String NoContacto) {
        this.NoContacto = NoContacto;
    }

    public String getHorario() {
        return Horario;
    }

    public void setHorario(String Horario) {
        this.Horario = Horario;
    }

    public String getFechaIngreso() {
        return FechaIngreso;
    }

    public void setFechaIngreso(String FechaIngreso) {
        this.FechaIngreso = FechaIngreso;
    }

    public void INSERT() {
        String query = "INSERT INTO empleados(nombre, apellido1, apellido2, CURP, RFC, NSS, sueldo, puesto, noContacto, horario, fechaIngreso) " +
                "VALUES('" + Nombre + "', '" + Apellido1 + "', '" + Apellido2 + "', '" + CURP + "', '" + RFC + "', '" + NSS + "', " + Sueldo + ", '" + Puesto + "', '" + NoContacto + "', '" + Horario + "', '" + FechaIngreso + "')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UPDATE() {
        String query = "UPDATE empleados SET nombre = '" + Nombre + "', apellido1 = '" + Apellido1 + "', apellido2 = '" + Apellido2 + "', CURP = '" + CURP + "', RFC = '" + RFC + "', NSS = '" + NSS + "', sueldo = " + Sueldo + ", puesto = '" + Puesto + "', noContacto = '" + NoContacto + "', horario = '" + Horario + "', fechaIngreso = '" + FechaIngreso + "' WHERE Id_Empleado = " + Id_Empleado;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM empleados WHERE Id_Empleado = " + Id_Empleado;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<EmpleadoDAO> SELECT() {
        String query = "SELECT * FROM empleados";
        ObservableList<EmpleadoDAO> listaE = FXCollections.observableArrayList();
        EmpleadoDAO objE;
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                objE = new EmpleadoDAO();
                objE.setId_Empleado(res.getInt("Id_Empleado"));
                objE.setNombre(res.getString("nombre"));
                objE.setApellido1(res.getString("apellido1"));
                objE.setApellido2(res.getString("apellido2"));
                objE.setCURP(res.getString("CURP"));
                objE.setRFC(res.getString("RFC"));
                objE.setNSS(res.getString("NSS"));
                objE.setSueldo(res.getDouble("sueldo"));
                objE.setPuesto(res.getString("puesto"));
                objE.setNoContacto(res.getString("noContacto"));
                objE.setHorario(res.getString("horario"));
                objE.setFechaIngreso(res.getString("fechaIngreso"));
                listaE.add(objE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaE;
    }

    @Override
    public String toString() {
        return Nombre + " " + Apellido1;
    }
}

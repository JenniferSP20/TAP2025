package com.example.tap2025.modelos;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    static private String DB = "restaurante";
    static private String USER = "admin";
    static private String PWD = "1234567890";
    static private String HOST = "localhost";
    static private String PORT = "3306";

    public static Connection connection;

    public static void createConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://"+HOST+":"+PORT+"/"+DB,USER,PWD);
            System.out.println("Conexión establecida :)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
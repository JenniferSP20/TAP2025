package com.example.tap2025.modelos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                createConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            createConnection();
        }
        return connection;
    }
}
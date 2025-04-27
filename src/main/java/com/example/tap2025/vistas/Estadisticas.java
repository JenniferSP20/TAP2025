package com.example.tap2025.vistas;

import com.example.tap2025.modelos.Conexion;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.Statement;

public class Estadisticas extends Stage {

    public Estadisticas() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #fdfdd9;");

        root.getChildren().addAll(
                crearGraficaProductosMasVendidos(),
                crearGraficaVentasPorDia(),
                crearGraficaEmpleadosConMasVentas()
        );

        Scene scene = new Scene(root, 900, 800);
        this.setScene(scene);
        this.setTitle("Estadísticas");
    }

    private BarChart<String, Number> crearGraficaProductosMasVendidos() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Productos");
        xAxis.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Cantidad Vendida");
        yAxis.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        configurarBarChart(barChart, "Productos más vendidos");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Cantidad Vendida");

        try {
            String query = "SELECT p.nombreProducto, SUM(od.cantidad) as totalVendido " +
                    "FROM orden_detalle od " +
                    "JOIN producto p ON od.Id_Producto = p.Id_Producto " +
                    "GROUP BY p.nombreProducto " +
                    "ORDER BY totalVendido DESC " +
                    "LIMIT 10";
            Statement stmt = Conexion.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String producto = rs.getString("nombreProducto");
                int cantidad = rs.getInt("totalVendido");
                series.getData().add(new XYChart.Data<>(producto, cantidad));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        barChart.getData().add(series);
        return barChart;
    }

    private BarChart<String, Number> crearGraficaVentasPorDia() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Día");
        xAxis.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Ventas ($)");
        yAxis.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        configurarBarChart(barChart, "Ventas por día");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ventas");

        try {
            String query = "SELECT DATE(fecha) as dia, SUM(total) as totalVentas " +
                    "FROM orden " +
                    "GROUP BY dia " +
                    "ORDER BY dia DESC " +
                    "LIMIT 30";
            Statement stmt = Conexion.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String dia = rs.getString("dia");
                double totalVentas = rs.getDouble("totalVentas");
                series.getData().add(new XYChart.Data<>(dia, totalVentas));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        barChart.getData().add(series);
        return barChart;
    }

    private BarChart<String, Number> crearGraficaEmpleadosConMasVentas() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Empleados");
        xAxis.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Ventas Realizadas");
        yAxis.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        configurarBarChart(barChart, "Empleados con más ventas realizadas");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ventas Realizadas");

        try {
            String query = "SELECT CONCAT(e.nombre, ' ', e.apellido1, ' ', e.apellido2) as nombreEmpleado, COUNT(o.Id_Orden) as totalVentas " +
                    "FROM orden o " +
                    "JOIN empleados e ON o.Id_Empleado = e.Id_Empleado " +
                    "GROUP BY e.Id_Empleado " +
                    "ORDER BY totalVentas DESC " +
                    "LIMIT 10";
            Statement stmt = Conexion.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String nombreEmpleado = rs.getString("nombreEmpleado");
                int totalVentas = rs.getInt("totalVentas");
                series.getData().add(new XYChart.Data<>(nombreEmpleado, totalVentas));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        barChart.getData().add(series);
        return barChart;
    }

    private void configurarBarChart(BarChart<String, Number> barChart, String titulo) {
        barChart.setTitle(titulo);
        barChart.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 16px;");
        barChart.setLegendVisible(false);
        barChart.setCategoryGap(10);
        barChart.setBarGap(5);
        barChart.setAnimated(false);
        barChart.setVerticalGridLinesVisible(false);
        barChart.setHorizontalGridLinesVisible(false);
        barChart.setAlternativeColumnFillVisible(false);
        barChart.setAlternativeRowFillVisible(false);
        barChart.setHorizontalZeroLineVisible(false);
        barChart.setVerticalZeroLineVisible(false);
        barChart.setStyle("-fx-background-color: white; -fx-border-color: transparent;");
    }
}

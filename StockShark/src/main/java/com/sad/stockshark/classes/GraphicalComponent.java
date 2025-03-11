package com.sad.stockshark.classes;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import java.util.List;

public class GraphicalComponent implements iStockDisplay, iStockGraph {

    private LineChart<String, Number> stockChart;

    public GraphicalComponent(LineChart<String, Number> stockChart) {
        this.stockChart = stockChart;
    }

    @Override
    public void displayStockInfo() {
        System.out.println("Displaying stock information...");
        // Future implementation: Fetch stock details and update UI
    }

    @Override
    public void generateGraph() {
        System.out.println("Generating stock comparison graph...");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Example Stock");
        series.getData().add(new XYChart.Data<>("Jan", 100));
        series.getData().add(new XYChart.Data<>("Feb", 120));

        stockChart.getData().clear();
        stockChart.getData().add(series);
    }

    // Additional method to update the graph dynamically
    public void updateGraph(List<XYChart.Series<String, Number>> seriesList) {
        stockChart.getData().clear();
        stockChart.getData().addAll(seriesList);
    }
}

package com.sad.stockshark.Controllers;

import com.sad.stockshark.classes.iStockComparison;
import com.sad.stockshark.classes.iStockGraph;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.List;

public class StockComparisonController implements iStockComparison {

    @FXML private LineChart<String, Number> stockChart;
    @FXML private TextField stock1Field;
    @FXML private TextField stock2Field;
    @FXML private Button compareButton;

    private iStockGraph stockGraph;

    // Constructor Dependency Injection
    public StockComparisonController(iStockGraph stockGraph) {
        this.stockGraph = stockGraph;
    }

    @Override
    public void compareStocks(String stock1, String stock2) {
        System.out.println("Comparing stocks: " + stock1 + " vs " + stock2);

        stockGraph.generateGraph(); // Ensure stockGraph is used

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName(stock1);
        series1.getData().add(new XYChart.Data<>("Jan", 100));
        series1.getData().add(new XYChart.Data<>("Feb", 120));

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName(stock2);
        series2.getData().add(new XYChart.Data<>("Jan", 90));
        series2.getData().add(new XYChart.Data<>("Feb", 110));

        List<XYChart.Series<String, Number>> seriesList = new ArrayList<>();
        seriesList.add(series1);
        seriesList.add(series2);

        stockChart.getData().clear();
        stockChart.getData().addAll(seriesList); // Fixes Type Safety Warning
    }

    @FXML
    public void handleCompareButton() {
        compareStocks(stock1Field.getText(), stock2Field.getText());
    }
}

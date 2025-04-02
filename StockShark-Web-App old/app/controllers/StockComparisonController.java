package com.sad.stockshark.Controllers;

import com.sad.stockshark.classes.GraphicalComponent;
import com.sad.stockshark.classes.iStockComparison;
import com.sad.stockshark.classes.iStockGraph;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.List;

/**
 * StockComparisonController acts as the **Stock Comparison Graphs/Dashboard**. 
 * 
 * - **Provides (○) `iStockGraph`** → Implements `iStockGraph` for generating stock graphs.
 * - **Used by (⊂) `GraphicalComponent`** → GraphicalComponent requires `iStockGraph` from here.
 */
public class StockComparisonController implements iStockComparison, iStockGraph {  // ○ Provides `iStockGraph`

    @FXML private LineChart<String, Number> stockChart;
    @FXML private TextField stock1Field;
    @FXML private TextField stock2Field;
    @FXML private Button compareButton;

    private GraphicalComponent graphicalComponent;  // Uses the UI component

    @FXML
    public void initialize() {
        // Injects itself (`this`) as the required `iStockGraph` for GraphicalComponent
        this.graphicalComponent = new GraphicalComponent(stockChart, this);  
    }

    @Override
    public void compareStocks(String stock1, String stock2) {
        System.out.println("Comparing stocks: " + stock1 + " vs " + stock2);

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

        graphicalComponent.displayGraph(seriesList); // Calls `displayGraph()`
    }

    @FXML
    public void handleCompareButton() {
        compareStocks(stock1Field.getText(), stock2Field.getText());
    }

    /**
     * **PROVIDED (○) FUNCTIONALITY: `iStockGraph`**
     * Implements `generateGraph()` that GraphicalComponent will call.
     */
    @Override
    public void generateGraph(List<XYChart.Series<String, Number>> seriesList) {
        if (stockChart != null) {
            stockChart.getData().clear();
            stockChart.getData().addAll(seriesList);
        } else {
            System.err.println("Stock chart is not initialized!");
        }
    }
}

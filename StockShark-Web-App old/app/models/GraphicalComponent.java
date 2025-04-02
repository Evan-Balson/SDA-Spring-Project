package com.sad.stockshark.classes;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import java.util.List;

/**
 * GraphicalComponent represents the graphical display of stock information.
 * 
 * - **Provides (○) `iStockDisplay`** → Implements `iStockDisplay` to display stock info.
 * - **Requires (⊂) `iStockGraph`** → Needs `iStockGraph` to generate graphs.
 */
public class GraphicalComponent implements iStockDisplay {  // ○ Provides `iStockDisplay`

    private LineChart<String, Number> stockChart; // UI Component for displaying graphs
    private iStockGraph stockGraph;  // ⊂ Requires `iStockGraph`

    /**
     * Constructor: Injects required dependencies.
     * - `LineChart` is required to render graphs. 
     * - `iStockGraph` is required for graph generation.
     */
    public GraphicalComponent(LineChart<String, Number> stockChart, iStockGraph stockGraph) {
        this.stockChart = stockChart;
        this.stockGraph = stockGraph;  // ⊂ Injects required `iStockGraph`
    }

    /**
     * **PROVIDED (○) FUNCTIONALITY: `iStockDisplay`**
     * Implements the method to display stock information.
     */
    @Override
    public void displayStockInfo() {
        System.out.println("Displaying stock information...");
        // Future implementation: Fetch stock details and update UI
    }

    /**
     * **USES (⊂) FUNCTIONALITY FROM `iStockGraph`**
     * Calls `generateGraph()` from the required dependency.
     */
    public void displayGraph(List<XYChart.Series<String, Number>> seriesList) {
        System.out.println("Requesting graph from Stock Comparison Graphs/Dashboard...");
        stockGraph.generateGraph(seriesList);  // ⊂ Calls the required `iStockGraph`
    }
}

package com.sad.stockshark.classes;

import javafx.scene.chart.XYChart;
import java.util.List;

public interface iStockGraph {
    void generateGraph(List<XYChart.Series<String, Number>> seriesList);
}
 
package com.stockshark.stockshark.models.Stock_Analysis;

import java.util.List;

public interface iStockDisplay {
    public void GenerateAreaChart(List<String> stockData);
    public void GenerateAreaChartByDateRange(List<String> stockSymbols,String date1, String date2);

}

package com.stockshark.stockshark.models.Data_Handling;

import org.json.JSONObject;

import java.util.List;

public interface iStockData {
    List<StockData> formatData_from_AlphaVantage(JSONObject jsonData);

}
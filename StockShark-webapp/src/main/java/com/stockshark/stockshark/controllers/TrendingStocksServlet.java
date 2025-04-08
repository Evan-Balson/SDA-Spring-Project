package com.stockshark.stockshark.controllers;

import com.stockshark.stockshark.models.Data_Handling.StockData;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/TrendingStocks")
public class TrendingStocksServlet extends HttpServlet {

    private StockDataService service;

    @Override
    public void init() throws ServletException {
        this.service = new StockDataService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        List<StockData> trendingStocks = service.getTrendingStocks(); // You'll need to implement this method

        if (trendingStocks != null && !trendingStocks.isEmpty()) {
            JSONArray jsonArray = new JSONArray();
            for (StockData stock : trendingStocks) {
                JSONObject stockJson = new JSONObject();
                stockJson.put("symbol", stock.getSymbol()); // Assuming your StockData has a getSymbol()
                stockJson.put("name", "Stock Name Here"); // You might need to fetch the name too
                stockJson.put("price", stock.getClose()); // Assuming getClose() gives the current price
                // You'll need to calculate and set "change" and "change%" here
                stockJson.put("change", "+X.XX");
                stockJson.put("changePercent", "+Y.YY%");
                stockJson.put("volume", stock.getVolume());
                // Add other fields as needed
                jsonArray.put(stockJson);
            }
            out.print(jsonArray);
        } else {
            out.print("[]"); // Send an empty array if no trending stocks found
        }

        out.flush();
        out.close();
    }
}
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

        List<StockData> trendingStocks = service.getTrendingStocks();
        System.out.println("Trending Stocks: " + trendingStocks);

        if (trendingStocks != null && !trendingStocks.isEmpty()) {
            JSONArray jsonArray = new JSONArray();
            for (StockData stock : trendingStocks) {
                JSONObject stockJson = new JSONObject();
                stockJson.put("symbol", stock.getSymbol());
                stockJson.put("price", stock.getClose()); 
                stockJson.put("change", stock.getChange());
                stockJson.put("changePercent", stock.getChangePercent());
                stockJson.put("volume", stock.getVolume());
                stockJson.put("averageVolume", stock.getAvgVolume3M());
                stockJson.put("marketCap", stock.getMarketCap());
                stockJson.put("peRatio", stock.getPeRatio());
                stockJson.put("fiftyTwoWeekChange", stock.getFiftyTwoWeekChange());
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
package com.stockshark.stockshark.controllers;

import com.stockshark.stockshark.models.Data_Handling.StockData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/Markets")
public class StockDataServlet extends HttpServlet {

    private StockDataService stockService;

    @Override
    public void init() throws ServletException {
        stockService = new StockDataService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<StockData> trendingStocks = stockService.getTopTrendingStocks();
        System.out.println(trendingStocks);
        request.setAttribute("trendingStocks", trendingStocks);
        request.getRequestDispatcher("markets.jsp").forward(request, response);
    }
}

package com.stockshark.stockshark.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/StockData")
public class StockDataServlet extends HttpServlet {

    private StockDataService service;

    @Override
    public void init() throws ServletException {
        // Initialize the StockDataService (you may want to get it from context or a DI framework)
        this.service = new StockDataService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();


        String symbol = request.getParameter("symbol");
        if (symbol == null || symbol.isEmpty()) {
            out.println("{\"error\": \"Please provide a stock symbol.\"}");
            return;
        }
        boolean success = service.updateStockData(symbol);

        if (success) {
            out.println("{\"success\": \"Data fetched and inserted successfully.\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"Failed to fetch and process data.\"}");
        }

        out.flush();
        out.close();
    }
}

package com.stockshark.stockshark.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@WebServlet("/StockData")
public class StockDataServlet extends HttpServlet {
    private static final String API_KEY = "JGCLFOUSE8PP5S1P"; // Replace with your key

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String symbol = request.getParameter("symbol");
        if (symbol == null || symbol.isEmpty()) {
            out.println("{\"error\": \"Please provide a stock symbol.\"}");
            return;
        }

        // Construct the URL based on the Alpha Vantage parameter details
        String url = "https://www.alphavantage.co/query?" +
                "function=TIME_SERIES_DAILY&symbol=" + symbol +
                "&outputsize=compact&datatype=json&apikey=" + API_KEY;

        // Make the API call
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            out.println(httpResponse.body()); // Return the JSON data to the client
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"Failed to fetch data: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
            out.close();
        }
    }
}


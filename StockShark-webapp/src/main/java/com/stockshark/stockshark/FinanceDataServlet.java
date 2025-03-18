package com.stockshark.stockshark;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/FinanceData")
public class FinanceDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String symbol = request.getParameter("symbol"); // Get stock symbol from request
        if (symbol == null || symbol.isEmpty()) {
            symbol = "AAPL"; // Default to Apple if no symbol provided
        }

        try {
            Stock stock = YahooFinance.get(symbol);
            if (stock != null) {
                out.printf("{\"symbol\": \"%s\", \"price\": %s, \"prevClose\": %s}",
                        stock.getSymbol(), stock.getQuote().getPrice(), stock.getQuote().getPreviousClose());
            } else {
                out.printf("{\"error\": \"No data found for symbol: %s\"}", symbol);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"Failed to fetch data: " + e.getMessage() + "\"}");
            e.printStackTrace(); // This will print more details about the error in your server logs
        } finally {
            out.flush();
            out.close();
        }
    }
}


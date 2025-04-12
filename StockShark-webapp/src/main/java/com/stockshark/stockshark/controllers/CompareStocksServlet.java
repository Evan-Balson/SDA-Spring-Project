package com.stockshark.stockshark.controllers;

import com.stockshark.config.DatabaseConfig;
import com.stockshark.stockshark.models.CompoundCoomponents.Data_Handling_Component;
import com.stockshark.stockshark.models.CompoundCoomponents.User_Management_Compound;
import com.stockshark.stockshark.models.Data_Handling.Database;
import com.stockshark.stockshark.models.Data_Handling.StockData;
import com.stockshark.stockshark.models.User_Management.UserService;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/CompareStocks")
public class CompareStocksServlet extends HttpServlet {

    private User_Management_Compound user_management = new User_Management_Compound();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("compareStocks.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String symbolA = request.getParameter("symbolA");
        String symbolB = request.getParameter("symbolB");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        // Checkboxes (if checked, the parameter will be non-null)
        boolean showSymbolA = request.getParameter("showSymbolA") != null;
        boolean showSymbolB = request.getParameter("showSymbolB") != null;

        // Check if user clicked "Add To Portfolio"
        String addToPortfolio = request.getParameter("addToPortfolio");

        List<String> selectedSymbols = List.of(symbolA, symbolB);
        List<StockData> stocksDataForSymbols = new ArrayList<>();

        if (addToPortfolio != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                String userEmail = (String) session.getAttribute("user");
                System.out.println(userEmail);

                Boolean success = user_management.addToPortfolio(userEmail, symbolA,symbolB);
            }

            request.setAttribute("portfolioMessage", "Added to portfolio: " + symbolA + " and " + symbolB);
        }

        // Load MySQL driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL driver not found!");
            e.printStackTrace();
        }

        String connectionString = "jdbc:mysql://"
                + DatabaseConfig.getHost() + ":"
                + DatabaseConfig.getPort() + "/"
                + DatabaseConfig.getDatabaseName();
        System.out.println("Connecting to " + connectionString);

        try (Connection conn = DriverManager.getConnection(
                connectionString, DatabaseConfig.getUser(), DatabaseConfig.getPassword())) {

            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM stock_prices WHERE stock_symbol IN (");
            StringJoiner placeholders = new StringJoiner(",");
            for (int i = 0; i < selectedSymbols.size(); i++) {
                placeholders.add("?");
            }
            sqlBuilder.append(placeholders.toString()).append(") AND price_date BETWEEN ? AND ?");
            String selectSql = sqlBuilder.toString();

            try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
                // Set symbols
                for (int i = 0; i < selectedSymbols.size(); i++) {
                    ps.setString(i + 1, selectedSymbols.get(i));
                }
                // Set date range parameters (assumes dates in format "yyyy-MM-dd")
                ps.setString(selectedSymbols.size() + 1, startDate);
                ps.setString(selectedSymbols.size() + 2, endDate);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String symbol = rs.getString("stock_symbol");
                        String priceDate = rs.getString("price_date");
                        String open = rs.getString("open");
                        String high = rs.getString("high");
                        String low = rs.getString("low");
                        String close = rs.getString("close");
                        String volume = rs.getString("volume");

                        // Create StockData (ensure your StockData constructor and getters match)
                        StockData stockData = new StockData(symbol, priceDate, open, high, low, close, volume);
                        stocksDataForSymbols.add(stockData);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Determine missing symbols (those without any data in the date range)
            Set<String> symbolsInDB = new HashSet<>();
            for (StockData data : stocksDataForSymbols) {
                symbolsInDB.add(data.getSymbol().toUpperCase());
            }
            List<String> missingSymbols = new ArrayList<>();
            for (String sym : selectedSymbols) {
                if (!symbolsInDB.contains(sym.toUpperCase())) {
                    missingSymbols.add(sym);
                }
            }

            // ------------------------------------------
            // Step 2: For any missing symbol, fetch data from the API and insert into the DB.
            if (!missingSymbols.isEmpty()) {
                for (String missingSymbol : missingSymbols) {
                    try {
                        // Call your API helper to get trending data.
                        Data_Handling_Component database = user_management.getexternalDB();
                        JSONObject apiResponse = database.getStockDataByDateRange(missingSymbol, startDate, endDate);
                        // Format the API response into a list of StockData.
                        List<StockData> apiStockData = database.Format_AlphaVantageData_ForDatabase(apiResponse);
                        // Insert the fetched data into the database.
                        database.insertStockData(apiStockData, missingSymbol);
                    } catch (Exception e) {
                        System.err.println("Error processing symbol " + missingSymbol);
                        e.printStackTrace();
                    }
                }
                // Re-query to get a complete data set.
                stocksDataForSymbols.clear();
                try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
                    for (int i = 0; i < selectedSymbols.size(); i++) {
                        ps.setString(i + 1, selectedSymbols.get(i));
                    }
                    ps.setString(selectedSymbols.size() + 1, startDate);
                    ps.setString(selectedSymbols.size() + 2, endDate);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            String symbol = rs.getString("stock_symbol");
                            String priceDate = rs.getString("price_date");
                            String open = rs.getString("open");
                            String high = rs.getString("high");
                            String low = rs.getString("low");
                            String close = rs.getString("close");
                            String volume = rs.getString("volume");

                            StockData stockData = new StockData(symbol, priceDate, open, high, low, close, volume);
                            stocksDataForSymbols.add(stockData);
                        }
                    }
                }
            }

            // ------------------------------------------
            // Step 3: Process the real data to build JSON arrays for charting.
            // We will:
            //  a) Group StockData by symbol.
            //  b) Build a sorted union of all dates from the data.
            //  c) For each date, obtain the closing price for each symbol (or null if missing).

            // (a) Group data by symbol (all keys in uppercase)
            Map<String, List<StockData>> dataBySymbol = new HashMap<>();
            for (StockData sd : stocksDataForSymbols) {
                String upperSymbol = sd.getSymbol().toUpperCase();
                dataBySymbol.computeIfAbsent(upperSymbol, k -> new ArrayList<>()).add(sd);
            }
            // Sort each symbol's data by date (assumes date format "yyyy-MM-dd" works in natural order)
            for (List<StockData> list : dataBySymbol.values()) {
                list.sort(Comparator.comparing(StockData::getDate));
            }

            // (b) Build a sorted union of all dates.
            SortedSet<String> unionDates = new TreeSet<>();
            for (List<StockData> list : dataBySymbol.values()) {
                for (StockData sd : list) {
                    unionDates.add(sd.getDate());
                }
            }
            List<String> labelsList = new ArrayList<>(unionDates);

            // (c) For symbolA and symbolB build arrays of closing prices.
            Map<String, Double> priceMapA = new HashMap<>();
            Map<String, Double> priceMapB = new HashMap<>();
            if (dataBySymbol.containsKey(symbolA.toUpperCase())) {
                for (StockData sd : dataBySymbol.get(symbolA.toUpperCase())) {
                    try {
                        priceMapA.put(sd.getDate(), Double.parseDouble(sd.getClose()));
                    } catch (Exception ex) {
                        priceMapA.put(sd.getDate(), null);
                    }
                }
            }
            if (dataBySymbol.containsKey(symbolB.toUpperCase())) {
                for (StockData sd : dataBySymbol.get(symbolB.toUpperCase())) {
                    try {
                        priceMapB.put(sd.getDate(), Double.parseDouble(sd.getClose()));
                    } catch (Exception ex) {
                        priceMapB.put(sd.getDate(), null);
                    }
                }
            }

            List<Double> pricesAList = new ArrayList<>();
            List<Double> pricesBList = new ArrayList<>();
            for (String date : labelsList) {
                pricesAList.add(priceMapA.getOrDefault(date, null));
                pricesBList.add(priceMapB.getOrDefault(date, null));
            }

            // Convert the labels and price arrays to JSON strings.
            String labelsJson = arrayToJson(labelsList.toArray(new String[0]));
            String dataAJson = listToJson(pricesAList);
            String dataBJson = listToJson(pricesBList);

            // ------------------------------------------
            // Set attributes for the JSP page.
            request.setAttribute("symbolA", symbolA);
            request.setAttribute("symbolB", symbolB);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("showSymbolA", showSymbolA);
            request.setAttribute("showSymbolB", showSymbolB);
            request.setAttribute("labelsJson", labelsJson);
            request.setAttribute("dataAJson", dataAJson);
            request.setAttribute("dataBJson", dataBJson);

            request.getRequestDispatcher("compareStocks.jsp").forward(request, response);
        } catch (SQLException e) {
            System.err.println("Database connection error!");
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database connection error!");
            request.getRequestDispatcher("compareStocks.jsp").forward(request, response);
        }
    }

    // Helper: Convert a String array to a JSON array string.
    private String arrayToJson(String[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append("\"").append(arr[i]).append("\"");
            if (i < arr.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // Helper: Convert a List<Double> to a JSON array string.
    // Null values will be represented as null.
    private String listToJson(List<Double> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            Double value = list.get(i);
            if (value == null) {
                sb.append("null");
            } else {
                sb.append(value);
            }
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

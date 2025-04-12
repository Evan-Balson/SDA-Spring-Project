package com.stockshark.stockshark.controllers;

import com.stockshark.config.DatabaseConfig;
import com.stockshark.stockshark.models.Data_Handling.DataAggregator;
import com.stockshark.stockshark.models.Data_Handling.StockData;
import com.stockshark.stockshark.models.CompoundCoomponents.User_Management_Compound;
import com.stockshark.stockshark.models.Data_Handling.externalAPI;

import java.sql.*;
import java.util.*;

public class StockDataService {
    private User_Management_Compound userManagementCompound;
    private externalAPI api;
    private DataAggregator aggregator;

    public StockDataService() {
        this.api = new externalAPI();
        this.aggregator = new DataAggregator();

        this.userManagementCompound = new User_Management_Compound();
    }


    public List<StockData> getTopTrendingStocks() {
        List<StockData> trendingStocks = new ArrayList<>();
        // List of trending symbols (adjust the list as needed)
        List<String> trendingSymbols = List.of("AAPL", "TSLA", "GOOGL", "AMZN", "MSFT","NFLX");
        // Track symbols already found in the database.
        Set<String> foundSymbols = new HashSet<>();

        // Load MySQL driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL driver not found!");
            e.printStackTrace();
            return trendingStocks;
        }

        // Build the connection string using DatabaseConfig values
        String connectionString = "jdbc:mysql://"
                + DatabaseConfig.getHost() + ":"
                + DatabaseConfig.getPort() + "/"
                + DatabaseConfig.getDatabaseName();
        System.out.println("Connecting to " + connectionString);

        try (Connection conn = DriverManager.getConnection(connectionString, DatabaseConfig.getUser(), DatabaseConfig.getPassword())) {
            // --- Step 1: Query the trendingstock table for available data ---
            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM trendingstock WHERE symbol IN (");
            StringJoiner placeholders = new StringJoiner(",");
            for (int i = 0; i < trendingSymbols.size(); i++) {
                placeholders.add("?");
            }
            sqlBuilder.append(placeholders.toString()).append(")");
            String selectSql = sqlBuilder.toString();

            try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
                for (int i = 0; i < trendingSymbols.size(); i++) {
                    ps.setString(i + 1, trendingSymbols.get(i));
                }
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String symbol = rs.getString("symbol");
                        String price = rs.getString("price");
                        // Note: Updated column name from "change" to "priceChange"
                        String priceChange = rs.getString("priceChange");
                        String changePercent = rs.getString("change_percent");

                        // Create a new StockData object with the fetched data.
                        // Make sure your StockData class supports the priceChange property.
                        StockData stockData = new StockData(symbol, price, priceChange, changePercent);
                        trendingStocks.add(stockData);
                        foundSymbols.add(symbol);
                    }
                }
            }

            // --- Step 2: For each symbol not in the database, fetch via the API and insert into the trendingstock table ---
            for (String symbol : trendingSymbols) {
                if (!foundSymbols.contains(symbol)) {
                    try {
                        // Get the JSON data from the API
                        String jsonTrending = api.getTrendingMarketData(symbol).toString();
                        // Convert the JSON string into a StockData object using the modified formatter
                        StockData stockData = aggregator.formatGlobalQuoteData(jsonTrending);
                        trendingStocks.add(stockData);

                        // Insert the new stock data into the trendingstock table.
                        // The insert includes the newly named column priceChange.
                        String insertSql = "INSERT INTO trendingstock (symbol, price, priceChange, change_percent) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                            insertPs.setString(1, stockData.getSymbol());
                            insertPs.setString(2, stockData.getPrice());
                            // Make sure that stockData.getPriceChange() returns the correct value.
                            insertPs.setString(3, stockData.getPriceChange());
                            insertPs.setString(4, stockData.getChangePercent());
                            insertPs.executeUpdate();
                        }
                    } catch (Exception e) {
                        System.err.println("Error retrieving or inserting data for symbol " + symbol);
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Database connection error!");
            e.printStackTrace();
        }

        return trendingStocks;
    }


}

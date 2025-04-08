package com.stockshark.stockshark.models.Data_Handling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.stockshark.config.DatabaseConfig;
import org.json.JSONObject;

public class Database {

    private DataAggregator aggregator;
    private externalAPI api;

    public Database(DataAggregator aggregator, externalAPI api) {
        this.aggregator = aggregator;
        this.api = api;
    }

    public List<StockData> Format_AlphaVantageData_ForDatabase(JSONObject jsonData){
        return aggregator.formatData_from_AlphaVantage(jsonData);
    }

    public static void insertStockData(List<StockData> stockDataList, String symbol) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connectionString = "jdbc:mysql://" + DatabaseConfig.getHost() + ":" + DatabaseConfig.getPort() + "/" + DatabaseConfig.getDatabaseName();
            System.out.println("Connecting to " + connectionString);
            Connection conn = DriverManager.getConnection(connectionString, DatabaseConfig.getUser(), DatabaseConfig.getPassword());

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO stock_prices (symbol, date, open, high, low, close, volume) " +
                    "SELECT ?, ?, ?, ?, ?, ?, ? FROM dual " +
                    "WHERE NOT EXISTS ( " +
                    "    SELECT 1 FROM stock_prices WHERE symbol = ? AND date = ? " +
                    ")"
            );

            System.out.println("Connected to the database successfully!");
            for (StockData data : stockDataList) {
                stmt.setString(1, symbol);
                stmt.setString(2, data.getDate());

                double open = Double.parseDouble(data.getOpen());
                double high = Double.parseDouble(data.getHigh());
                double low = Double.parseDouble(data.getLow());
                double close = Double.parseDouble(data.getClose());
                long volume = Long.parseLong(data.getVolume());

                stmt.setDouble(3, open);
                stmt.setDouble(4, high);
                stmt.setDouble(5, low);
                stmt.setDouble(6, close);
                stmt.setLong(7, volume);
                stmt.setString(8, symbol);
                stmt.setString(9, data.getDate());
                stmt.addBatch();
            }

            stmt.executeBatch();

            if (conn != null) conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database or insert data.");
        }
    }

    public static List<StockData> getAllStockData() {
        List<StockData> allStocks = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connectionString = "jdbc:mysql://" + DatabaseConfig.getHost() + ":" + DatabaseConfig.getPort() + "/" + DatabaseConfig.getDatabaseName();
            conn = DriverManager.getConnection(connectionString, DatabaseConfig.getUser(), DatabaseConfig.getPassword());

            String sql = "SELECT Symbol, Price, priceChange, ChangePercent, Volume, AverageVolume3M, MarketCap, PERatio, FiftyTwoWeekChange FROM TrendingStock";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                StockData stock = new StockData();
                stock.setSymbol(rs.getString("Symbol"));
                stock.setClose(String.valueOf(rs.getDouble("Price"))); // Assuming 'Price' is the current price
                stock.setChange(rs.getDouble("priceChange"));
                stock.setChangePercent(rs.getDouble("ChangePercent"));
                stock.setVolume(rs.getLong("Volume"));
                stock.setAvgVolume3M(rs.getString("AverageVolume3M"));
                stock.setMarketCap(rs.getString("MarketCap"));
                stock.setPeRatio(rs.getString("PERatio"));
                stock.setFiftyTwoWeekChange(rs.getString("FiftyTwoWeekChange"));
                allStocks.add(stock);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error fetching all stock data.");
        } finally {
            // Close resources in a finally block to ensure they are always closed
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return allStocks;
    }
}
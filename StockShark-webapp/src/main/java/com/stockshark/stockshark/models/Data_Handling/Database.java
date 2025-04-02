package com.stockshark.stockshark.models.Data_Handling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

            // Handle your database logic here
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO stock_prices (symbol, date, open, high, low, close, volume) " +
                    "SELECT ?, ?, ?, ?, ?, ?, ? FROM dual " + // "dual" can be omitted in some databases like PostgreSQL
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

            stmt.executeBatch(); // Execute the batch



        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database.");
        }
    }
}

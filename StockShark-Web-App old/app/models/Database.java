package com.sad.stockshark.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database implements iStockGraph {
    public void generateGraph() {
        // Implementation code
    }

    public  static void getFromDatabase(){

        // Database URL and credentials
        String url = "jdbc:mysql://localhost:3306/share_price_app_db";
        String user = "root";
        String password = "";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection to the database
            conn = DriverManager.getConnection(url, user, password);

            // Create a statement object to send to the database
            stmt = conn.createStatement();

            // Execute SQL query
            String sql = "SELECT * FROM stock_prices";
            rs = stmt.executeQuery(sql);

            // Process the result set
            while (rs.next()) {
                String symbol = rs.getString("symbol"); // Adjust column names as per your table
                double open_price = rs.getDouble("open_price");
                System.out.println("ID: " + symbol + ", Open Price: " + open_price);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database access error:");
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}



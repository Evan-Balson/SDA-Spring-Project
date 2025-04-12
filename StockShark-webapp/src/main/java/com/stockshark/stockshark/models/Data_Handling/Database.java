package com.stockshark.stockshark.models.Data_Handling;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.stockshark.config.DatabaseConfig;
import com.stockshark.stockshark.models.CompoundCoomponents.Data_Handling_Component;
import com.stockshark.stockshark.models.CompoundCoomponents.Stock_Analysis_Component;
import com.stockshark.stockshark.models.User_Management.Portfolio;
import org.json.JSONObject;

public class Database {

    private DataAggregator aggregator;
    private externalAPI api;


    public Database() {
    }

    public Database(DataAggregator aggregator, externalAPI api) {
        this.aggregator = aggregator;
        this.api = api;
    }

    public List<StockData> Format_AlphaVantageData_ForDatabase(JSONObject jsonData) {
        return aggregator.formatData_from_AlphaVantage(jsonData);
    }


    public void insertStockData(List<StockData> stockDataList, String symbol) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connectionString = "jdbc:mysql://"
                    + DatabaseConfig.getHost() + ":"
                    + DatabaseConfig.getPort() + "/"
                    + DatabaseConfig.getDatabaseName();
            System.out.println("Connecting to " + connectionString);
            try (Connection conn = DriverManager.getConnection(connectionString, DatabaseConfig.getUser(), DatabaseConfig.getPassword())) {
                conn.setAutoCommit(false); // Use transactions to ensure integrity

                // First check if the stock exists in the stocks table.
                String stockCheckSql = "SELECT COUNT(*) FROM stocks WHERE stock_symbol = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(stockCheckSql)) {
                    checkStmt.setString(1, symbol);
                    ResultSet rs = checkStmt.executeQuery();
                    int count = 0;
                    if (rs.next()) {
                        count = rs.getInt(1);
                    }
                    rs.close();
                    // If not found, insert into the stocks table.
                    if (count == 0) {
                        String insertStockSql = "INSERT INTO stocks (stock_symbol, company_name) VALUES (?, ?)";
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertStockSql)) {
                            insertStmt.setString(1, symbol);
                            insertStmt.setString(2, "Unknown Company"); // Use a placeholder value; adjust as needed.
                            insertStmt.executeUpdate();
                            System.out.println("Inserted stock symbol " + symbol + " into stocks table.");
                        }
                    }
                }

                // Now, prepare batch insert for stock_prices.
                String insertSql =
                        "INSERT INTO stock_prices (stock_symbol, price_date, open, high, low, close, volume) " +
                                "SELECT ?, ?, ?, ?, ?, ?, ? FROM dual " +
                                "WHERE NOT EXISTS ( " +
                                "    SELECT 1 FROM stock_prices WHERE stock_symbol = ? AND price_date = ? " +
                                ")";
                try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                    System.out.println("Preparing batch insert for stock prices...");
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
                        // For the NOT EXISTS clause
                        stmt.setString(8, symbol);
                        stmt.setString(9, data.getDate());

                        stmt.addBatch();
                    }
                    int[] updateCounts = stmt.executeBatch();
                    conn.commit(); // Commit the transaction after batch execution.
                    System.out.println("Batch insert executed. Rows affected: " + Arrays.toString(updateCounts));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database or execute batch insert.");
        }
    }


    public JSONObject getStockDataByDateRange(String symbol, String start, String end) throws IOException, InterruptedException {
        return api.getStockDataByDateRange(symbol, start, end);
    }

    public JSONObject getTrendingStockDataFromAlphaVantage(String symbol) throws IOException, InterruptedException {
        return api.getTrendingMarketData(symbol);
    }

    public StockData formatGlobalQuoteData(JSONObject rawData) {
        return aggregator.formatGlobalQuoteData(rawData);
    }

    public Boolean beginRegistration(String username, String email, String password) {
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connection string
            String connectionString = "jdbc:mysql://" + DatabaseConfig.getHost() + ":" + DatabaseConfig.getPort() + "/" + DatabaseConfig.getDatabaseName();
            System.out.println("Connecting to " + connectionString);

            // Establish connection
            Connection conn = DriverManager.getConnection(connectionString, DatabaseConfig.getUser(), DatabaseConfig.getPassword());

            // Hash the password (example using SHA-256, you should consider using bcrypt for production)
            String hashedPassword = hashPassword(password);

            // Prepare SQL query to insert user
            String sql = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set parameters
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, hashedPassword);

            // Execute the query
            int rowsAffected = stmt.executeUpdate();

            // Check if the user was inserted successfully
            if (rowsAffected > 0) {
                System.out.println("User registered successfully!");
                return true;
            } else {
                System.out.println("Failed to register user.");
                return false;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database or error during registration.");
            return false;
        }
    }

    /**
     * Hashes a password using SHA-256.
     *
     * @param password The plain-text password.
     * @return The hexadecimal string of the hashed password.
     */
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            // Convert byte array into signum representation
            BigInteger number = new BigInteger(1, hashedBytes);
            // Convert the message digest into hex value
            StringBuilder hexString = new StringBuilder(number.toString(16));

            // Pad with leading zeros if necessary
            while (hexString.length() < 64) {
                hexString.insert(0, '0');
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found.", e);
        }
    }


    public Boolean authenticateUser(String email, String password) {
        try {
            // Load MySQL driver (adjust if using a different DB)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Build the connection string using the DatabaseConfig values
            String connectionString = "jdbc:mysql://"
                    + DatabaseConfig.getHost() + ":"
                    + DatabaseConfig.getPort() + "/"
                    + DatabaseConfig.getDatabaseName();

            System.out.println("Connecting to " + connectionString);

            // Get a database connection
            Connection conn = DriverManager.getConnection(connectionString, DatabaseConfig.getUser(), DatabaseConfig.getPassword());

            // Prepare SQL to retrieve the stored hashed password for the given email
            String sql = "SELECT password_hash FROM users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // If the user exists, compare the stored hash with the hash of the input password
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");

                // Hash the input password and compare it with the stored hash
                String inputHash = hashPassword(password);
                if (storedHash.equals(inputHash)) {
                    System.out.println("User authenticated successfully!");
                    return true;
                } else {
                    System.out.println("Authentication failed: incorrect password.");
                    return false;
                }
            } else {
                // User not found
                System.out.println("Authentication failed: user not found.");
                return false;
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to authenticate the user.");
            return false;
        }
    }

    public Boolean signOut(String email) {
        try {
            // Load MySQL driver (adjust if using a different DB)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Build the connection string using the DatabaseConfig values
            String connectionString = "jdbc:mysql://"
                    + DatabaseConfig.getHost() + ":"
                    + DatabaseConfig.getPort() + "/"
                    + DatabaseConfig.getDatabaseName();

            System.out.println("Connecting to " + connectionString);

            // Get a database connection
            Connection conn = DriverManager.getConnection(connectionString, DatabaseConfig.getUser(), DatabaseConfig.getPassword());

            // Prepare SQL to retrieve the stored hashed password for the given email
            String sql = "UPDATE users SET is_active = ? WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, false);
            stmt.setString(2, email);

            // Execute the query
            int rowsUpdated = stmt.executeUpdate();


            System.out.println("Signed out successfully.");
            return true;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to logout the user.");
            return false;
        }
    }

    public Boolean putPortfolioToDatabase(String email, String symbolA, String symbolB) {
        Connection conn = null;
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("[DEBUG] MySQL driver loaded successfully.");

            // Build the connection string using DatabaseConfig values
            String connectionString = "jdbc:mysql://" + DatabaseConfig.getHost() + ":"
                    + DatabaseConfig.getPort() + "/" + DatabaseConfig.getDatabaseName();
            System.out.println("[DEBUG] Connecting to: " + connectionString);

            // Establish the connection to the database
            conn = DriverManager.getConnection(connectionString,
                    DatabaseConfig.getUser(),
                    DatabaseConfig.getPassword());
            System.out.println("[DEBUG] Database connection established.");

            // Start the transaction by disabling auto-commit
            conn.setAutoCommit(false);
            System.out.println("[DEBUG] Auto-commit disabled. Transaction started.");

            // Retrieve the user_id based on the provided email
            String getUserIdSql = "SELECT user_id FROM users WHERE email = ?";
            PreparedStatement userStmt = conn.prepareStatement(getUserIdSql);
            userStmt.setString(1, email);
            System.out.println("[DEBUG] Executing query to retrieve user_id for email: " + email);

            ResultSet userRs = userStmt.executeQuery();
            if (!userRs.next()) {
                System.out.println("[ERROR] User not found for email: " + email);
                return false;  // Exit if the user does not exist
            }
            int userId = userRs.getInt("user_id");
            System.out.println("[DEBUG] Retrieved user_id: " + userId);

            // Use a fixed portfolio name
            String portfolioName = "Default Portfolio";
            System.out.println("[DEBUG] Portfolio name set to: " + portfolioName);

            // Insert a new portfolio row
            String insertPortfolioSql = "INSERT INTO portfolios (user_id, portfolio_name) VALUES (?, ?)";
            PreparedStatement portfolioStmt = conn.prepareStatement(insertPortfolioSql, Statement.RETURN_GENERATED_KEYS);
            portfolioStmt.setInt(1, userId);
            portfolioStmt.setString(2, portfolioName);
            System.out.println("[DEBUG] Inserting portfolio for user_id: " + userId + " with name: " + portfolioName);

            int rowsAffected = portfolioStmt.executeUpdate();
            System.out.println("[DEBUG] Portfolio insert returned rowsAffected: " + rowsAffected);
            if (rowsAffected <= 0) {
                System.out.println("[ERROR] Failed to create portfolio for user_id: " + userId);
                conn.rollback();
                return false;
            }

            // Retrieve the generated portfolio_id
            ResultSet generatedKeys = portfolioStmt.getGeneratedKeys();
            if (!generatedKeys.next()) {
                System.out.println("[ERROR] Failed to retrieve generated portfolio id.");
                conn.rollback();
                return false;
            }
            int portfolioId = generatedKeys.getInt(1);
            System.out.println("[DEBUG] Portfolio created with id: " + portfolioId);

            // Insert items into the portfolio_items table for each symbol
            String insertPortfolioItemSql = "INSERT INTO portfolio_items (portfolio_id, stock_symbol) VALUES (?, ?)";
            PreparedStatement itemStmt = conn.prepareStatement(insertPortfolioItemSql);

            // Debug log for symbolA insert
            System.out.println("[DEBUG] Preparing to insert portfolio item for symbol: " + symbolA);
            itemStmt.setInt(1, portfolioId);
            itemStmt.setString(2, symbolA);
            itemStmt.addBatch();

            // Debug log for symbolB insert
            System.out.println("[DEBUG] Preparing to insert portfolio item for symbol: " + symbolB);
            itemStmt.setInt(1, portfolioId);
            itemStmt.setString(2, symbolB);
            itemStmt.addBatch();

            // Execute the batch; ensure both rows are inserted
            int[] portfolioItemsResults = itemStmt.executeBatch();
            System.out.println("[DEBUG] Portfolio items batch executed. Result length: " + portfolioItemsResults.length);
            if (portfolioItemsResults.length != 2) {
                System.out.println("[ERROR] Failed to insert both portfolio items. Expected 2, got: " + portfolioItemsResults.length);
                conn.rollback();
                return false;
            }

            // Commit the transaction if all operations succeeded
            conn.commit();
            System.out.println("[DEBUG] Transaction committed successfully. Portfolio and portfolio items created.");

            return true;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("[ERROR] Exception encountered: " + e.getMessage());
            e.printStackTrace();
            try {
                if (conn != null) {
                    System.out.println("[DEBUG] Attempting to rollback transaction due to error.");
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                System.out.println("[ERROR] Rollback exception: " + rollbackEx.getMessage());
                rollbackEx.printStackTrace();
            }
            System.out.println("[ERROR] Database error occurred while updating portfolio.");
            return false;
        } finally {
            // Restore auto-commit and close the connection
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                    System.out.println("[DEBUG] Database connection closed and auto-commit restored.");
                } catch (SQLException e) {
                    System.out.println("[ERROR] Exception while closing connection: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Portfolio> getPortfolioDetails(String userId) {
        List<Portfolio> portfolioList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // Load MySQL JDBC driver.
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Build the connection string.
            String connectionString = "jdbc:mysql://"
                    + DatabaseConfig.getHost() + ":"
                    + DatabaseConfig.getPort() + "/"
                    + DatabaseConfig.getDatabaseName();
            conn = DriverManager.getConnection(connectionString, DatabaseConfig.getUser(), DatabaseConfig.getPassword());

            int userId_db=-1;
            String sqlUser = "SELECT user_id FROM users WHERE email = ?";
            ps = conn.prepareStatement(sqlUser);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                userId_db = rs.getInt("user_id");
            }
            rs.close();
            ps.close();

            // If no user_id was found, return an empty list.
            if (userId_db == -1) {
                System.out.println("No user found with email: " + userId);
                return portfolioList;
            }

            String sql = "SELECT p.portfolio_id, p.portfolio_name, p.created_date, " +
                    "GROUP_CONCAT(pi.stock_symbol SEPARATOR ', ') AS symbols " +
                    "FROM portfolios p " +
                    "LEFT JOIN portfolio_items pi ON p.portfolio_id = pi.portfolio_id " +
                    "WHERE p.user_id = ? " +
                    "GROUP BY p.portfolio_id, p.portfolio_name, p.created_date";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId_db);
            rs = ps.executeQuery();

            while (rs.next()) {
                int portfolioId = rs.getInt("portfolio_id");
                String portfolioName = rs.getString("portfolio_name");
                Timestamp createdDate = rs.getTimestamp("created_date");
                String savedDate = createdDate.toString();
                String symbols = rs.getString("symbols");
                // In case there are no items, default to empty string.
                if (symbols == null) {
                    symbols = "";
                }

                Portfolio p = new Portfolio(portfolioId, portfolioName, symbols, savedDate);

                portfolioList.add(p);
                System.out.println("[DEBUG] Portfolio List: " + portfolioList);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Close resources.
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return portfolioList;
    }

    public Portfolio getPortfolioById(int portfolioId) {
        Portfolio portfolio = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // Load the MySQL JDBC driver.
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Build the connection string.
            String connectionString = "jdbc:mysql://"
                    + DatabaseConfig.getHost() + ":"
                    + DatabaseConfig.getPort() + "/"
                    + DatabaseConfig.getDatabaseName();
            conn = DriverManager.getConnection(connectionString, DatabaseConfig.getUser(), DatabaseConfig.getPassword());

            // Query the portfolios joined with portfolio_items
            // GROUP_CONCAT aggregates the stock symbols (if any) for that portfolio.
            String sql = "SELECT p.portfolio_id, p.portfolio_name, p.created_date, " +
                    "GROUP_CONCAT(pi.stock_symbol SEPARATOR ', ') AS symbols " +
                    "FROM portfolios p " +
                    "LEFT JOIN portfolio_items pi ON p.portfolio_id = pi.portfolio_id " +
                    "WHERE p.portfolio_id = ? " +
                    "GROUP BY p.portfolio_id, p.portfolio_name, p.created_date";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, portfolioId);
            rs = ps.executeQuery();

            if (rs.next()) {
                int pid = rs.getInt("portfolio_id");
                String portfolioName = rs.getString("portfolio_name");
                Timestamp createdDate = rs.getTimestamp("created_date");
                String savedDate = createdDate.toString();
                String symbols = rs.getString("symbols");
                if (symbols == null) {
                    symbols = "";
                }
                // Create and assign the Portfolio object using the appropriate constructor.
                portfolio = new Portfolio(pid, portfolioName, symbols, savedDate);
            }
        } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return portfolio;
    }

    public Data_Handling_Component getDataHandlingComponentPort(){
        return new Data_Handling_Component();
    }


}

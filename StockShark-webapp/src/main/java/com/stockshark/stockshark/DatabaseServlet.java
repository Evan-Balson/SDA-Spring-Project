package com.stockshark.stockshark;

import com.stockshark.config.DatabaseConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/database")
public class DatabaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connectionString = "jdbc:mysql://" + DatabaseConfig.getHost() + ":" + DatabaseConfig.getPort() + "/" + DatabaseConfig.getDatabaseName();
            System.out.println("Connecting to " + connectionString);
            Connection conn = DriverManager.getConnection(connectionString, DatabaseConfig.getUser(), DatabaseConfig.getPassword());
            // Handle your database logic here
// ----------------------------------------------------------------->>
            resp.getWriter().write("Connected to the database successfully!");
        } catch (SQLException | ClassNotFoundException e) {

            e.printStackTrace();
            resp.getWriter().write("Failed to connect to the database.");
            System.out.println("Failed to connect to the database.");
        }
    }
}

package com.stockshark.stockshark.controllers;

import com.stockshark.stockshark.models.CompoundCoomponents.User_Management_Compound;
import com.stockshark.stockshark.models.User_Management.Portfolio;
import com.stockshark.stockshark.models.User_Management.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Portfolio")
public class PortfolioServlet extends HttpServlet {

    private UserService userService;

    private User_Management_Compound user_management;

    @Override
    public void init() throws ServletException {

        this.userService = new UserService();

        this.user_management = userService.user_management_compoundPort();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Portfolio> portfolioList = null;

        // Retrieve user ID from session.
        HttpSession session = request.getSession(false);
        String userId = null;
        if (session != null) {
            userId = (String) session.getAttribute("user");
        }
        if (userId != null) {

            System.out.println("User ID: " + userId);
            portfolioList = user_management.getPortfolioDetails(userId);
            System.out.println("Portfolio List: " + portfolioList);

        }

        request.setAttribute("portfolioList", portfolioList);
        request.getRequestDispatcher("portfolio.jsp").forward(request, response);


        /*
        // Dummy data for demonstration
        List<Portfolio> portfolioList = new ArrayList<>();
        portfolioList.add(new Portfolio("My Watchlist + count", portfolio ID, "Stock symbolA", "Stock Symbol B", "saved date"));
        request.setAttribute("portfolioList", portfolioList);

        request.getRequestDispatcher("portfolio.jsp").forward(request, response);*/
    }
}

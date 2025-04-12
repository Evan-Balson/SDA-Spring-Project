package com.stockshark.stockshark.controllers;

import com.stockshark.stockshark.models.CompoundCoomponents.User_Management_Compound;
import com.stockshark.stockshark.models.User_Management.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {

    //private LoginService loginService;
    private User_Management_Compound user_management;

    @Override
    public void init() throws ServletException {
        // Initialize your LoginService (replace with your actual dependency injection or factory method)
        //loginService = new LoginService();
        user_management = new User_Management_Compound();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the login JSP that renders the login form
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve credentials from request parameters
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Use your LoginService to check credentials (replace with real logic)
        Boolean success = user_management.Login(email, password);
        //Boolean success = loginService.loginUser(email, password);

        if (success) {
            // Login successful. Set the user in the session.
            HttpSession session = request.getSession();
            session.setAttribute("user", email);

            // Optionally, redirect to a landing or home page.
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            // Login failed. Set an error message and forward back to login page.
            request.setAttribute("loginError", "Invalid email or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}

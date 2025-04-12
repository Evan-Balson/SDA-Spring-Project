package com.stockshark.stockshark.controllers;

import com.stockshark.stockshark.models.CompoundCoomponents.User_Management_Compound;
import com.stockshark.stockshark.models.User_Management.LoginService; // or your dedicated RegistrationService
import com.stockshark.stockshark.models.User_Management.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Registration")
public class RegistrationServlet extends HttpServlet {

    private User_Management_Compound user_management; // assuming LoginService has a registerUser method

    @Override
    public void init() throws ServletException {
        // Initialize your user model / service
        user_management = new User_Management_Compound();
    }

    // Handle GET request to display the registration form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to the registration page (JSP)
        request.getRequestDispatcher("/registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form fields
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Basic validation: check if password fields match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match.");
            request.getRequestDispatcher("registration.jsp").forward(request, response);
            return;
        }

        // Call the register method on your user model.
        // Replace "registerUser" with the actual method that inserts the new user into the database.
        // This is a stubbed function call; implement its logic in LoginService or a separate RegistrationService.
        boolean registrationSuccess = user_management.registerUser(username, email, password);
        System.out.println(registrationSuccess);
        if (registrationSuccess) {
            // Registration succeeded, redirect to login page (or home page)
            response.sendRedirect(request.getContextPath() + "/Login");
        } else {
            // Registration failed. Set an error attribute and forward back to the registration page.
            request.setAttribute("errorMessage", "Registration failed. Please try again.");
            request.getRequestDispatcher("registration.jsp").forward(request, response);
        }
    }
}

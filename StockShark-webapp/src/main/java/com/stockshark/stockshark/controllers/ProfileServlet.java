package com.stockshark.stockshark.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/Profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Load user data from DB or session, set request attributes...
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("updateEmail".equals(action)) {
            String newEmail = request.getParameter("email");
            // TODO: Validate & update email in DB
        } else if ("updatePassword".equals(action)) {
            String currentPass = request.getParameter("currentPassword");
            String newPass = request.getParameter("newPassword");
            // TODO: Validate current pass, update with new pass
        } else if ("deleteAccount".equals(action)) {
            // TODO: Perform account deletion
        }

        // Optionally set success/error messages, re-load user data, etc.
        response.sendRedirect("Profile");
    }
}

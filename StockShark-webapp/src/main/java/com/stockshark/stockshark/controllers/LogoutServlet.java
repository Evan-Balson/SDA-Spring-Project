package com.stockshark.stockshark.controllers;

import com.stockshark.stockshark.models.CompoundCoomponents.User_Management_Compound;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {

    User_Management_Compound user_management;

    @Override
    public void init() throws ServletException {

        user_management = new User_Management_Compound();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Invalidate the current session if one exists.
        HttpSession session = request.getSession(false);
        if (session != null) {
            System.out.println("Logged in as " + session.getAttribute("user") +" " + session.getId());

            user_management.logoutUser(session.getAttribute("user").toString());
            session.invalidate();
        }
        // Redirect the user to the login page (or any public landing page)
        response.sendRedirect(request.getContextPath() + "/Login");
    }
}

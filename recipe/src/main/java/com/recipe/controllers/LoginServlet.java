package com.recipe.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jspPath = getServletContext().getInitParameter("jspPath");

        String username = req.getParameter("username");
        String password = req.getParameter("password");


        if ("user".equals(username) && "password".equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            resp.sendRedirect("home");
        } else {
            req.setAttribute("errorMessage", "Invalid username or password.");
            req.getRequestDispatcher(jspPath + "login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jspPath = getServletContext().getInitParameter("jspPath");

        req.getRequestDispatcher(jspPath + "login.jsp").forward(req, resp);
    }
}


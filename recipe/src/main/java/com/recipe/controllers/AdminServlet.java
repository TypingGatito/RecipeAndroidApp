package com.recipe.controllers;

import com.recipe.config.ServicesConfigurer;
import com.recipe.models.User;
import com.recipe.services.AdminService;
import com.recipe.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private UserService userService;

    private AdminService adminService;

    @Override
    public void init() throws ServletException {
        userService = ServicesConfigurer.getServicesConfig().getUserService();
        adminService = ServicesConfigurer.getServicesConfig().getAdminService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = adminService.getUsers();
        req.setAttribute("users", users);
        String jspPath = getServletContext().getInitParameter("jspPath");
        req.getRequestDispatcher(jspPath + "admin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdStr = req.getParameter("userId");
        String isActiveStr = req.getParameter("isActive");

        if (userIdStr != null && isActiveStr != null) {
            Long userId = Long.parseLong(userIdStr);
            Boolean isActive = Boolean.parseBoolean(isActiveStr);

            adminService.setBan(userId, isActive);
        }

        resp.sendRedirect("/admin");
    }
}


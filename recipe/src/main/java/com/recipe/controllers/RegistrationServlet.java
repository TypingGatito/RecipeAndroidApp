package com.recipe.controllers;

import com.recipe.config.ServicesConfigurer;
import com.recipe.models.User;
import com.recipe.services.UserService;
import com.recipe.validation.WebValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = ServicesConfigurer.getServicesConfig().getUserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jspPath = getServletContext().getInitParameter("jspPath");

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = userService.findUserByEmail(email);

        if (user != null) {
            req.setAttribute("errorMessage", "Email is already taken.");
            req.getRequestDispatcher(jspPath + "register.jsp").forward(req, resp);
            return;
        }

        User newUser = new User();
        newUser.setLogin(username);
        newUser.setEmail(email);
        newUser.setPassword(password);

        try {
            WebValidator.validateUser(newUser);
        } catch (IllegalArgumentException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("password", password);
            req.setAttribute("username", username);
            req.setAttribute("email", email);
            req.getRequestDispatcher(jspPath + "register.jsp").forward(req, resp);
            return;
        }

        userService.addUser(newUser);

        resp.sendRedirect("login");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jspPath = getServletContext().getInitParameter("jspPath");

        req.getRequestDispatcher(jspPath + "register.jsp").forward(req, resp);
    }
}

package com.recipe.controllers;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.config.ElementConfigurer;
import com.recipe.config.ServicesConfig;
import com.recipe.config.ServicesConfigurer;
import com.recipe.sql.connection.ConnectionPool;
import com.recipe.sql.repositories.RecipeRepository;
import com.recipe.in_memory.repositories.UserRepository;
import com.recipe.services.RecipeService;
import com.recipe.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = ServicesConfigurer.getServicesConfig().getUserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jspPath = getServletContext().getInitParameter("jspPath");

        System.out.println(userService.findUserByEmail(req.getParameter("username")));

        HttpSession session = req.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;

        if (username == null) {
            resp.sendRedirect("login");
            return;
        }

        req.setAttribute("username", username);
        req.setAttribute("role", username);
        req.getRequestDispatcher(jspPath + "home.jsp").forward(req, resp);
    }
}

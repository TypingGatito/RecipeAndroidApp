package com.recipe.controllers;

import com.recipe.config.ServicesConfigurer;
import com.recipe.models.User;
import com.recipe.services.UserService;
import com.recipe.utils.CookiesUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = ServicesConfigurer.getServicesConfig().getUserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = CookiesUtil.getAuthTokenEmail(req);
        User user = userService.findUserByEmail(email);

        if (user.getId() == null) {
            resp.sendRedirect("/login");
            return;
        }

        String login = req.getParameter("login");
        email = req.getParameter("email");
        String password = req.getParameter("password");
        Boolean isActive = req.getParameter("isActive") != null;

        if (!password.isEmpty()) {
            user.setPassword(password);
        }
        user.setLogin(login);
        user.setEmail(email);
        user.setIsActive(isActive);

        userService.updateUser(user);

        resp.sendRedirect("/account");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jspPath = getServletContext().getInitParameter("jspPath");
        String email = CookiesUtil.getAuthTokenEmail(req);
        User user = userService.findUserByEmail(email);

        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }

        req.setAttribute("user", user);
        req.getRequestDispatcher(jspPath + "account.jsp").forward(req, resp);
    }

}


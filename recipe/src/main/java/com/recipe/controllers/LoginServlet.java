package com.recipe.controllers;

import com.recipe.config.ServicesConfigurer;
import com.recipe.models.User;
import com.recipe.services.UserService;
import com.recipe.utils.CookiesUtil;
import com.recipe.utils.TokenUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = ServicesConfigurer.getServicesConfig().getUserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jspPath = getServletContext().getInitParameter("jspPath");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (userService.login(email, password)) {
            HttpSession session = req.getSession();

            User user = userService.findUserByEmail(email);

            session.setAttribute("username", user.getLogin());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("role", CookiesUtil.getRole(userService.getUserRoles(user.getId())));

            String token = TokenUtil.createToken(email);

            Cookie tokenCookie = new Cookie("authToken", token);
            tokenCookie.setHttpOnly(true);
            tokenCookie.setSecure(true);
            tokenCookie.setMaxAge(60 * 60);
            resp.addCookie(tokenCookie);

            resp.sendRedirect("home");
        } else {
            req.setAttribute("errorMessage", "Invalid username or password.");
            req.setAttribute("password", password);
            req.setAttribute("email", email);
            req.getRequestDispatcher(jspPath + "login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jspPath = getServletContext().getInitParameter("jspPath");

        String email = CookiesUtil.getAuthTokenEmail(req);
        User user = userService.findUserByEmail(email);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("username", user.getLogin());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("role", CookiesUtil.getRole(userService.getUserRoles(user.getId())));
            resp.sendRedirect("/");
            return;
        }

        req.getRequestDispatcher(jspPath + "login.jsp").forward(req, resp);
    }
}



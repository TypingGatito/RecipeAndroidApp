package com.recipe.controllers;

import com.recipe.config.ServicesConfigurer;
import com.recipe.models.User;
import com.recipe.services.SectionService;
import com.recipe.services.UserService;
import com.recipe.utils.CookiesUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/")
public class HomeServlet extends HttpServlet {

    private UserService userService;

    private SectionService sectionService;

    @Override
    public void init() throws ServletException {
        sectionService = ServicesConfigurer.getServicesConfig().getSectionService();
        userService = ServicesConfigurer.getServicesConfig().getUserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jspPath = getServletContext().getInitParameter("jspPath");



        HttpSession session = req.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;
        String email = (session != null) ? (String) session.getAttribute("email") : null;

        User user = userService.findUserByEmail(email);

        long userId = user == null ? -1 : user.getId();

        req.setAttribute("username", username);
        req.setAttribute("role", CookiesUtil.getRole(userService.getUserRoles(userId)));
        req.setAttribute("sections", sectionService.getAllSections());
        req.getRequestDispatcher(jspPath + "home.jsp").forward(req, resp);
    }
}

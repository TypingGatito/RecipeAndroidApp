package com.recipe.controllers;

import com.recipe.config.ServicesConfigurer;
import com.recipe.models.User;
import com.recipe.services.RatingService;
import com.recipe.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/updateRating")
public class RatingServlet extends HttpServlet {

    private RatingService ratingService;

    private UserService userService;

    @Override
    public void init() throws ServletException {
        ratingService = ServicesConfigurer.getServicesConfig().getRatingService();
        userService = ServicesConfigurer.getServicesConfig().getUserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String recipeIdParam = req.getParameter("recipeId");
        String ratingParam = req.getParameter("rating");

        HttpSession session = req.getSession(false);
        String email = (session != null) ? (String) session.getAttribute("email") : null;
        User user = userService.findUserByEmail(email);
        Long userId = user == null ? -1 : user.getId();

        if (recipeIdParam == null || ratingParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
            return;
        }

        try {
            Long recipeId = Long.parseLong(recipeIdParam);
            int newRating = Integer.parseInt(ratingParam);

            if (newRating < 1 || newRating > 5) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid rating value");
                return;
            }

            ratingService.setRating(userId, recipeId, (double) newRating);

            resp.sendRedirect("/recipe?id=" + recipeId);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameter format");
        }
    }
}


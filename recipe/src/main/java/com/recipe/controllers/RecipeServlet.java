package com.recipe.controllers;

import com.recipe.config.ServicesConfigurer;
import com.recipe.models.*;
import com.recipe.services.*;
import com.recipe.utils.CookiesUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/recipe")
public class RecipeServlet extends HttpServlet {

    private RecipeService recipeService;

    private IngredientService ingredientService;

    private RatingService ratingService;

    private StepService stepService;

    private CommentaryService commentaryService;

    private UserService userService;

    @Override
    public void init() throws ServletException {
        recipeService = ServicesConfigurer.getServicesConfig().getRecipeService();
        ingredientService = ServicesConfigurer.getServicesConfig().getIngredientService();
        ratingService = ServicesConfigurer.getServicesConfig().getRatingService();
        stepService = ServicesConfigurer.getServicesConfig().getStepService();
        commentaryService = ServicesConfigurer.getServicesConfig().getCommentaryService();
        userService = ServicesConfigurer.getServicesConfig().getUserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String recipeId = req.getParameter("id");
        String jspPath = getServletContext().getInitParameter("jspPath");

        String email = CookiesUtil.getAuthTokenEmail(req);
        User user = userService.findUserByEmail(email);

        if (recipeId != null) {
            Long id = Long.parseLong(recipeId);
            Recipe recipe = recipeService.findRecipeById(id);
            List<Ingredient> ingredients = ingredientService.findByRecipeId(id);
            Double rating = ratingService.findRatingByRecipeId(id);
            Double myRating = null;
            if (user != null) {
                Rating myRatingR = ratingService.findRating(user.getId(), id);
                myRating = myRatingR == null ? null : myRatingR.getRating();
            }
            List<Step> steps = stepService.findRecipeSteps(id);
            List<Commentary> commentaries = new ArrayList<>();
            if (user != null) {
                for (Step step : steps) {
                    commentaries.addAll(commentaryService.findUserStepCommentaries(user.getId(), step.getId()));
                }
            }

            req.setAttribute("recipe", recipe);
            req.setAttribute("ingredients", ingredients);
            req.setAttribute("rating", rating);
            req.setAttribute("myRating", myRating);
            req.setAttribute("steps", steps);
            req.setAttribute("commentaries", commentaries);
            req.getRequestDispatcher(jspPath + "recipe.jsp").forward(req, resp);

            req.getSession(false).setAttribute("recipeId", id);
        } else {
            resp.sendRedirect("/");
        }
    }
}


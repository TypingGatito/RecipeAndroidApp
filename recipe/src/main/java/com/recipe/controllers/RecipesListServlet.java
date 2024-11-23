package com.recipe.controllers;

import com.recipe.config.ServicesConfigurer;
import com.recipe.models.Recipe;
import com.recipe.models.User;
import com.recipe.services.RecipeService;
import com.recipe.services.SectionService;
import com.recipe.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import java.util.List;
import java.util.function.Supplier;

@WebServlet("/recipes")
public class RecipesListServlet extends HttpServlet {

    private SectionService sectionService;

    private UserService userService;

    private RecipeService recipeService;

    @Override
    public void init() throws ServletException {
        sectionService = ServicesConfigurer.getServicesConfig().getSectionService();
        userService = ServicesConfigurer.getServicesConfig().getUserService();
        recipeService = ServicesConfigurer.getServicesConfig().getRecipeService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jspPath = getServletContext().getInitParameter("jspPath");

        HttpSession session = req.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;
        String email = (session != null) ? (String) session.getAttribute("email") : null;

        String listType = req.getParameter("type");
        Supplier<List<Recipe>> recipesSupplier = () -> recipeService.findAllRecipes();

        if ("section".equals(listType)) {
            String sectionIdStr = req.getParameter("sectionId");
            if (sectionIdStr != null) {
                Long sectionId = Long.parseLong(sectionIdStr);
                recipesSupplier = () -> recipeService.findRecipesBySectionId(sectionId);


                req.setAttribute("headerText", sectionService.getSectionById(sectionId).getName());
            }

        } else if ("favourite".equals(listType)) {
            User user = userService.findUserByEmail(email);
            recipesSupplier = () -> recipeService.findFavoriteRecipesOfUser(user.getId());
            req.setAttribute("headerText", "Your favourite");
        }


        req.setAttribute("username", username);
        req.setAttribute("role", username);
        req.setAttribute("recipes", recipesSupplier.get());
        req.getRequestDispatcher(jspPath + "recipes.jsp").forward(req, resp);
    }

}

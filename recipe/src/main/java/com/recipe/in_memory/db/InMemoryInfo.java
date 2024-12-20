package com.recipe.in_memory.db;

import com.recipe.annotations.Element;
import com.recipe.in_memory.in_memory_m_t_m_tables.UserFavouriteRecipe;
import com.recipe.models.*;
import com.recipe.models.enums.Unit;
import com.recipe.models.enums.UserRole;
import lombok.Getter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Element
@Getter
public class InMemoryInfo {

    private static InMemoryInfo instance;

    public static synchronized InMemoryInfo getInstance() {
        if (instance == null) instance = new InMemoryInfo();

        return instance;
    }

    private InMemoryInfo() {

        initUsers();
        initCommentaries();
        initIngredients();
        initRatings();
        initRecipes();
        initSections();
        initSteps();
        initUserFavouriteRecipe();
        initUserRoles();

    }

    private List<User> users;

    private List<Commentary> commentaries;

    private List<Ingredient> ingredients;

    private List<Rating> ratings;

    private List<Recipe> recipes;

    private List<Section> sections;

    private List<Step> steps;

    private UserFavouriteRecipe userFavouriteRecipe;

    private Map<Long, Set<UserRole>> userRoles;

    private void initUsers() {
        users = new ArrayList<>();
        //p1
        users.add(new User(1L, "user1", "9kVR/NbweCPLh5cc+5FEZCXaGChrOrHvk14MvXpp9oo=", "p", true));
        users.add(new User(2L, "user2", "password2", "user2@example.com", true));
        users.add(new User(3L, "admin", "a", "a", true));
        users.add(new User(4L, "Solaire", "s", "s.com", true));
    }

    private void initCommentaries() {
        commentaries = new ArrayList<>();
        commentaries.add(new Commentary(1L, 1L, 1, "Great recipe!"));
        commentaries.add(new Commentary(2L, 1L, 2, "I will try this out."));
        commentaries.add(new Commentary(4L, 1L, 1, "Shake more."));
    }

    private void initIngredients() {
        ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(1L, 1L, "Flour", 500, Unit.GRAM));
        ingredients.add(new Ingredient(2L, 1L, "Sugar", 200, Unit.GRAM));
        ingredients.add(new Ingredient(3L, 1L, "Butter", 100, Unit.GRAM));
    }

    private void initRatings() {
        ratings = new ArrayList<>();
        ratings.add(new Rating(1L, 1L, 4.5));
        ratings.add(new Rating(1L, 2L, 5.0));
    }

    private void initRecipes() {
        recipes = new ArrayList<>();
        recipes.add(new Recipe(1L, 1L, 1L, "Chocolate Cake", 400, Duration.ofMinutes(90), 8, "Delicious chocolate cake", LocalDateTime.now()));
        recipes.add(new Recipe(2L, 2L, 2L, "Pasta", 200, Duration.ofMinutes(30), 4, "Quick and easy pasta", LocalDateTime.now()));
    }

    private void initSections() {
        sections = new ArrayList<>();
        sections.add(new Section(1L, "Desserts"));
        sections.add(new Section(2L, "Main Course"));
    }

    private void initSteps() {
        steps = new ArrayList<>();
        steps.add(new Step(1L, 1L, 1, "Mix all ingredients"));
        steps.add(new Step(2L, 1L, 2, "Bake in the oven"));
    }

    private void initUserFavouriteRecipe() {
        userFavouriteRecipe = UserFavouriteRecipe.getInstance();
        userFavouriteRecipe.userCreatesRecipe(1L, 1L);
        userFavouriteRecipe.userCreatesRecipe(2L, 2L);
    }

    private void initUserRoles() {
        userRoles = new HashMap<>();

        Set<UserRole> userRole = new HashSet<>();
        userRole.add(UserRole.USER);
        userRoles.put(1L, userRole);

        userRole = new HashSet<>();
        userRole.add(UserRole.USER);
        userRoles.put(2L, userRole);

        Set<UserRole> adminRole = new HashSet<>();
        adminRole.add(UserRole.ADMIN);
        userRoles.put(3L, adminRole);

        userRole.add(UserRole.USER);
        userRoles.put(4L, userRole);
    }

}

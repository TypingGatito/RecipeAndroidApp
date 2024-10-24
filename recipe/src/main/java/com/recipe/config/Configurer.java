package com.recipe.config;

import com.recipe.in_memory.repositories.AdminRepository;
import com.recipe.repositories.IAdminRepository;
import com.recipe.in_memory.repositories.CommentaryRepository;
import com.recipe.repositories.ICommentaryRepository;
import com.recipe.repositories.IIngredientRepository;
import com.recipe.in_memory.repositories.IngredientRepository;
import com.recipe.repositories.IRatingRepository;
import com.recipe.in_memory.repositories.RatingRepository;
import com.recipe.repositories.IRecipeRepository;
import com.recipe.in_memory.repositories.RecipeRepository;
import com.recipe.repositories.ISectionRepository;
import com.recipe.in_memory.repositories.SectionRepository;
import com.recipe.repositories.IStepRepository;
import com.recipe.in_memory.repositories.StepRepository;
import com.recipe.repositories.IUserRepository;
import com.recipe.in_memory.repositories.UserRepository;
import com.recipe.services.*;

public class Configurer {

    private IAdminRepository adminRepository;

    private ICommentaryRepository commentaryRepository;

    private IIngredientRepository ingredientRepository;

    private IRecipeRepository recipeRepository;

    private IRatingRepository ratingRepository;

    private ISectionRepository sectionRepository;

    private IStepRepository stepRepository;

    private IUserRepository userRepository;

    private ElementConfigurer elementConfigurer;

    public Configurer() {
        elementConfigurer = new ElementConfigurer();
    }

    public ServicesConfig config() {
        configRepositories();

        return ServicesConfig.builder()
                .adminService(adminService())
                .commentaryService(commentaryService())
                .ingredientService(ingredientService())
                .recipeService(recipeService())
                .ratingService(ratingService())
                .sectionService(sectionService())
                .stepService(stepService())
                .userService(userService())
                .build();
    }

    private AdminService adminService() {
        return new AdminService(adminRepository);
    }

    private CommentaryService commentaryService() {
        return new CommentaryService(commentaryRepository);
    }

    private IngredientService ingredientService() {
        return new IngredientService(ingredientRepository);
    }

    private RecipeService recipeService() {
        return new RecipeService(recipeRepository);
    }

    private RatingService ratingService() {
        return new RatingService(ratingRepository);
    }

    private SectionService sectionService() {
        return new SectionService(sectionRepository);
    }

    private StepService stepService() {
        return new StepService(stepRepository);
    }

    private UserService userService() {
        return new UserService(userRepository, recipeRepository);
    }

    private void configRepositories() {

        adminRepository = new AdminRepository();

        commentaryRepository = new CommentaryRepository();

        ingredientRepository = new IngredientRepository();

        recipeRepository = new RecipeRepository();

        ratingRepository = new RatingRepository();

        sectionRepository = new SectionRepository();

        stepRepository = new StepRepository();

        userRepository = new UserRepository();

        try {
            elementConfigurer.configureObject(adminRepository);
            elementConfigurer.configureObject(commentaryRepository);
            elementConfigurer.configureObject(ingredientRepository);
            elementConfigurer.configureObject(recipeRepository);
            elementConfigurer.configureObject(ratingRepository);
            elementConfigurer.configureObject(sectionRepository);
            elementConfigurer.configureObject(stepRepository);
            elementConfigurer.configureObject(userRepository);
        } catch (Exception e) {
            throw new RuntimeException("Not enough Element for Injected");
        }

    }

}

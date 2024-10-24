package com.recepe.config;

import com.recepe.in_memory.repositories.AdminRepository;
import com.recepe.repositories.IAdminRepository;
import com.recepe.in_memory.repositories.CommentaryRepository;
import com.recepe.repositories.ICommentaryRepository;
import com.recepe.repositories.IIngredientRepository;
import com.recepe.in_memory.repositories.IngredientRepository;
import com.recepe.repositories.IRatingRepository;
import com.recepe.in_memory.repositories.RatingRepository;
import com.recepe.repositories.IRecipeRepository;
import com.recepe.in_memory.repositories.RecipeRepository;
import com.recepe.repositories.ISectionRepository;
import com.recepe.in_memory.repositories.SectionRepository;
import com.recepe.repositories.IStepRepository;
import com.recepe.in_memory.repositories.StepRepository;
import com.recepe.repositories.IUserRepository;
import com.recepe.in_memory.repositories.UserRepository;
import com.recepe.services.*;

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

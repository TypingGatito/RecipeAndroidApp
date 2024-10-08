package com.shoe_store.config;

import com.shoe_store.in_memory_info.InMemoryInfo;
import com.shoe_store.repositories.admin.AdminRepository;
import com.shoe_store.repositories.admin.IAdminRepository;
import com.shoe_store.repositories.commentary.CommentaryRepository;
import com.shoe_store.repositories.commentary.ICommentaryRepository;
import com.shoe_store.repositories.ingredient.IIngredientRepository;
import com.shoe_store.repositories.ingredient.IngredientRepository;
import com.shoe_store.repositories.rating.IRatingRepository;
import com.shoe_store.repositories.rating.RatingRepository;
import com.shoe_store.repositories.recipe.IRecipeRepository;
import com.shoe_store.repositories.recipe.RecipeRepository;
import com.shoe_store.repositories.section.ISectionRepository;
import com.shoe_store.repositories.section.SectionRepository;
import com.shoe_store.repositories.step.IStepRepository;
import com.shoe_store.repositories.step.StepRepository;
import com.shoe_store.repositories.user.IUserRepository;
import com.shoe_store.repositories.user.UserRepository;
import com.shoe_store.services.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Configurer {

    private IAdminRepository adminRepository;

    private ICommentaryRepository commentaryRepository;

    private IIngredientRepository ingredientRepository;

    private IRecipeRepository recipeRepository;

    private IRatingRepository ratingRepository;

    private ISectionRepository sectionRepository;

    private IStepRepository stepRepository;

    private IUserRepository userRepository;

    public ServicesConfig config() {
        configRepositories();

        ServicesConfig config = ServicesConfig.builder()
                .adminService(adminService())
                .commentaryService(commentaryService())
                .ingredientService(ingredientService())
                .recipeService(recipeService())
                .ratingService(ratingService())
                .sectionService(sectionService())
                .stepService(stepService())
                .userService(userService())
                .build();

        return config;
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

        adminRepository = new AdminRepository(InMemoryInfo.getInstance());

        commentaryRepository = new CommentaryRepository(InMemoryInfo.getInstance());

        ingredientRepository = new IngredientRepository(InMemoryInfo.getInstance());

        recipeRepository = new RecipeRepository(InMemoryInfo.getInstance());

        ratingRepository = new RatingRepository(InMemoryInfo.getInstance());

        sectionRepository = new SectionRepository(InMemoryInfo.getInstance());

        stepRepository = new StepRepository(InMemoryInfo.getInstance());

        userRepository = new UserRepository(InMemoryInfo.getInstance());

    }
}

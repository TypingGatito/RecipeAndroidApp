package com.shoe_store.config;

import com.shoe_store.services.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServicesConfig {

    private AdminService adminService;

    private CommentaryService commentaryService;

    private IngredientService ingredientService;

    private RecipeService recipeService;

    private RatingService ratingService;

    private SectionService sectionService;

    private StepService stepService;

    private UserService userService;

}

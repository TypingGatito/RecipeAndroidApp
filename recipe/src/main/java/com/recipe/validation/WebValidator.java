package com.recipe.validation;

import com.recipe.models.*;
import java.time.LocalDateTime;

public class WebValidator {

    public static void validate(Object object) {
        if (object instanceof Commentary) {
            validateCommentary((Commentary) object);
        } else if (object instanceof Ingredient) {
            validateIngredient((Ingredient) object);
        } else if (object instanceof Rating) {
            validateRating((Rating) object);
        } else if (object instanceof Recipe) {
            validateRecipe((Recipe) object);
        } else if (object instanceof Section) {
            validateSection((Section) object);
        } else if (object instanceof Step) {
            validateStep((Step) object);
        } else if (object instanceof User) {
            validateUser((User) object);
        } else {
            throw new IllegalArgumentException("Unsupported object type for validation");
        }
    }

    public static void validateCommentary(Commentary commentary) {
        if (commentary.getUserId() == null || commentary.getUserId() <= 0) {
            throw new IllegalArgumentException("Commentary userId must be positive and non-null");
        }
        if (commentary.getStepId() == null || commentary.getStepId() <= 0) {
            throw new IllegalArgumentException("Commentary stepId must be positive and non-null");
        }
        if (commentary.getOrderNum() == null || commentary.getOrderNum() <= 0) {
            throw new IllegalArgumentException("Commentary orderNum must be positive and non-null");
        }
        if (commentary.getText() == null || commentary.getText().isEmpty() || commentary.getText().length() > 255) {
            throw new IllegalArgumentException("Commentary text must be between 1 and 255 characters");
        }
    }

    public static void validateIngredient(Ingredient ingredient) {
        if (ingredient.getRecipeId() == null || ingredient.getRecipeId() <= 0) {
            throw new IllegalArgumentException("Ingredient recipeId must be positive and non-null");
        }
        if (ingredient.getName() == null || ingredient.getName().isEmpty() || ingredient.getName().length() > 100) {
            throw new IllegalArgumentException("Ingredient name must be between 1 and 100 characters");
        }
        if (ingredient.getAmount() == null || ingredient.getAmount() <= 0) {
            throw new IllegalArgumentException("Ingredient amount must be positive and non-null");
        }
        if (ingredient.getUnit() == null) {
            throw new IllegalArgumentException("Ingredient unit must be non-null");
        }
    }

    public static void validateRating(Rating rating) {
        if (rating.getRecipeId() == null || rating.getRecipeId() <= 0) {
            throw new IllegalArgumentException("Rating recipeId must be positive and non-null");
        }
        if (rating.getUserId() == null || rating.getUserId() <= 0) {
            throw new IllegalArgumentException("Rating userId must be positive and non-null");
        }
        if (rating.getRating() == null || rating.getRating() < 0 || rating.getRating() > 5) {
            throw new IllegalArgumentException("Rating value must be between 0 and 5");
        }
    }

    public static void validateRecipe(Recipe recipe) {
        if (recipe.getId() == null || recipe.getId() <= 0) {
            throw new IllegalArgumentException("Recipe id must be positive and non-null");
        }
        if (recipe.getSectionId() == null || recipe.getSectionId() <= 0) {
            throw new IllegalArgumentException("Recipe sectionId must be positive and non-null");
        }
        if (recipe.getUserId() == null || recipe.getUserId() <= 0) {
            throw new IllegalArgumentException("Recipe userId must be positive and non-null");
        }
        if (recipe.getName() == null || recipe.getName().isEmpty() || recipe.getName().length() > 255) {
            throw new IllegalArgumentException("Recipe name must be between 1 and 255 characters");
        }
        if (recipe.getCaloriesOnHundG() != null && recipe.getCaloriesOnHundG() < 0) {
            throw new IllegalArgumentException("Recipe caloriesOnHundG cannot be negative");
        }
        if (recipe.getTimeToCook() != null && recipe.getTimeToCook().isNegative()) {
            throw new IllegalArgumentException("Recipe timeToCook cannot be negative");
        }
        if (recipe.getDoseNum() == null || recipe.getDoseNum() <= 0) {
            throw new IllegalArgumentException("Recipe doseNum must be positive and non-null");
        }
        if (recipe.getShortDescription() != null && recipe.getShortDescription().length() > 500) {
            throw new IllegalArgumentException("Recipe shortDescription cannot exceed 500 characters");
        }
        if (recipe.getCreatedAt() == null || recipe.getCreatedAt().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Recipe createdAt must be a past or present timestamp");
        }
    }

    public static void validateSection(Section section) {
        if (section.getId() == null || section.getId() <= 0) {
            throw new IllegalArgumentException("Section id must be positive and non-null");
        }
        if (section.getName() == null || section.getName().isEmpty() || section.getName().length() > 100) {
            throw new IllegalArgumentException("Section name must be between 1 and 100 characters");
        }
    }

    public static void validateStep(Step step) {
        if (step.getRecipeId() == null || step.getRecipeId() <= 0) {
            throw new IllegalArgumentException("Step recipeId must be positive and non-null");
        }
        if (step.getNum() == null || step.getNum() <= 0) {
            throw new IllegalArgumentException("Step num must be positive and non-null");
        }
        if (step.getText() == null || step.getText().isEmpty() || step.getText().length() > 255) {
            throw new IllegalArgumentException("Step text must be between 1 and 255 characters");
        }
    }

    public static void validateUser(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().length() > 50) {
            throw new IllegalArgumentException("User login must be between 1 and 50 characters");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty() || user.getPassword().length() < 6) {
            throw new IllegalArgumentException("User password must be at least 6 characters");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new IllegalArgumentException("User email must be a valid email address");
        }
    }
}

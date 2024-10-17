package com.shoe_store.ui.states;

import com.shoe_store.models.*;
import com.shoe_store.services.*;
import com.shoe_store.ui.enums.StateName;
import com.shoe_store.ui.info.StateInfo;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class RecipePage implements Page {

    private final Page menu;

    private final RecipeService recipeService;

    private final IngredientService ingredientService;

    private final RatingService ratingService;

    private final StateInfo stateInfo;

    private final StepService stepService;

    private final CommentaryService commentaryService;

    private Recipe recipe;

    @Override
    public void display() {
        menu.display();

        recipe = recipeService.getRecipeById(Long.parseLong(stateInfo.getStringParams().get("ID")));

        System.out.println(recipe.getName());

        Double rating = ratingService.findRatingByRecipeId(recipe.getId());
        System.out.printf("Рейтинг: %.2f\n", rating);

        printRecipesInfo();

        printIngredients();

        printSteps();

        printCommands();
    }

    @Override
    public StateInfo handleInput() {
        StateInfo input = menu.handleInput();

        if (input.getNewState() != null) return input;

        processRate(input);

        addToFavourite(input);

        if (input.getNewState() == null) return stateInfo;
        return input;
    }

    private void printCommands() {
        if (recipeService.isInFavourite(recipe.getId(), stateInfo.getUser().getId()))
            System.out.println("f-удалить из избранного");
        else
            System.out.println("f-добавить в избранное");
        System.out.println("r-оценить");
    }

    private void printSteps() {
        System.out.println("Шаги приготовления");

        List<Step> steps = stepService.findRecipeSteps(recipe.getId());
        steps.forEach(this::printStep);
    }

    private void printStep(Step step) {
        System.out.println("Шаг №" + step.getNum());
        System.out.println(step.getText());

        List<Commentary> commentaries = commentaryService.findUserStepCommentaries(stateInfo.getUser().getId(),
                step.getId());

        if (commentaries.isEmpty()) return;
        System.out.println("Ваши комментраии к шагу: ");
        commentaries.forEach(c -> {
            System.out.print(c.getOrderNum() + ") ");
            System.out.println(c.getText());
        });
    }

    private String createSeparator(int[] columnWidths) {
        StringBuilder separator = new StringBuilder("+");
        for (int width : columnWidths) {
            separator.append("-".repeat(width)).append("+");
        }
        return separator.toString();
    }

    private void printRecipesInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        int[] columnWidths = {16, 19, 13, 9, 30, 25};

        System.out.println(createSeparator(columnWidths));

        System.out.printf("| %-14s | %-17s | %-11s | %-7s | %-28s | %-23s |\n",
                "Name", "Calories/100g", "Cook Time", "Doses", "Description", "Created At");

        System.out.println(createSeparator(columnWidths));

        System.out.printf("| %-14s | %-17d | %-11s | %-7d | %-28s | %-23s |\n",
                recipe.getName(), recipe.getCaloriesOnHundG(), formatDuration(recipe.getTimeToCook()),
                recipe.getDoseNum(), recipe.getShortDescription(),
                recipe.getCreatedAt().format(formatter));

        System.out.println(createSeparator(columnWidths));
    }

    public static String formatDuration(Duration duration) {
        long minutes = duration.toMinutes();
        return minutes + " min";
    }

    private void printIngredients() {
        List<Ingredient> ingredients = ingredientService.findByRecipeId(recipe.getId());
        System.out.println("+----------------+--------+-------+");
        System.out.printf("| %-14s | %-6s | %-5s |\n", "Name", "Amount", "Unit");
        System.out.println("+----------------+--------+-------+");
        ingredients.forEach(this::printIngredient);
    }

    private void printIngredient(Ingredient ingredient) {
        System.out.printf("| %-14s | %-6d | %-5s |\n", ingredient.getName(), ingredient.getAmount(), ingredient.getUnit());
        System.out.println("+----------------+--------+-------+");
    }

    private void processRate(StateInfo info) {
        Scanner scanner = new Scanner(System.in);
        if (info.getStringParams().get("Choice").equals("r")) {
            Rating rating = ratingService.findRating(stateInfo.getUser().getId(), recipe.getId());
            if (rating != null) {
                System.out.println("Ваша оценка: " + rating.getRating());
                String del = "";
                while (!del.equals("y") && !del.equals("n")) {
                    System.out.print("Удалить оценку [Y/N]: ");
                    del = scanner.nextLine().toLowerCase();
                }
                if (del.equals("y")) {
                    ratingService.deleteRating(stateInfo.getUser().getId(), recipe.getId());
                    return;
                }
            }
            System.out.print("Введите оценку: ");
            String rat = scanner.nextLine();

            ratingService.setRating(stateInfo.getUser().getId(), recipe.getId(), Double.parseDouble(rat));
        }
    }

    private void addToFavourite(StateInfo info) {
        if (info.getStringParams().get("Choice").equals("f")) {
            if (!recipeService.isInFavourite(recipe.getId(), info.getUser().getId()))
                recipeService.addToFavourite(recipe.getId(), info.getUser().getId());
            else
                recipeService.removeFromFavourite(recipe.getId(), info.getUser().getId());
        }
    }

}

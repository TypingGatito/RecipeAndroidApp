package com.shoe_store.ui.states;

import com.shoe_store.models.Recipe;
import com.shoe_store.services.RecipeService;
import com.shoe_store.ui.enums.StateName;
import com.shoe_store.ui.info.StateInfo;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class RecipesListPage implements Page {

    private final Page menu;

    private final RecipeService recipeService;

    private final StateInfo stateInfo;

    List<Recipe> recipes;

    @Override
    public void display() {
        menu.display();

        System.out.println("Введите номер блюда в формате ({номер}d)");

        Map<String, String> stringParams = stateInfo.getStringParams();

        System.out.printf("------------%s-----------\n", title());

        if (!stringParams.containsKey("Type")) {
            return;
        }

        Consumer<Recipe> recipePrinter = (Recipe r) -> System.out.printf("(%d) %s\n", r.getId(), r.getName());

        if (stringParams.get("Type").equals("ALL")) {
            recipes = recipeService.findAllRecipes();
            recipes.forEach(recipePrinter);
        }

        if (stringParams.get("Type").equals("FAV")) {
            recipes = recipeService.findFavoriteRecipesOfUser(stateInfo.getUser().getId());
            recipes.forEach(recipePrinter);
        }

        if (stringParams.get("Type").equals("SEC")) {
            long sec = Long.parseLong(stringParams.get("SEC"));
            recipes = recipeService.findRecipesBySectionId(sec);
            recipes.forEach(recipePrinter);
        }
    }

    @Override
    public StateInfo handleInput() {
        StateInfo stateInfo = menu.handleInput();

        if (stateInfo.getNewState() != null) {
            return stateInfo;
        }

        chooseRecipe(stateInfo);

        if (stateInfo.getNewState() == null) {
            return this.stateInfo;
        }

        return stateInfo;
    }

    private void chooseRecipe(StateInfo info) {
        Long recipeId;
        try {
            recipeId = Long.parseLong(info.getStringParams().get("Choice").strip().split("d")[0]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Неверный ввод");
            info.setNewState(null);
            return;
        }

        Recipe recipe = recipes
                .stream()
                .filter(u -> u.getId().equals(recipeId))
                .findFirst()
                .orElse(null);

        if (recipe != null) {
            info.setNewState(StateName.RECIPE_PAGE);
            info.setStringParams(new HashMap<String, String>());
            info.getStringParams().put("ID", recipeId.toString());
        } else {
            System.out.println("Блюдо не найдено");
        }

    }

    private String title() {
        if (!stateInfo.getStringParams().containsKey("Type")) {
            return "Блюда";
        }

        String title = switch (stateInfo.getStringParams().get("Type")) {
            case "ALL" -> "Все блюда";
            case "FAV" -> "Избранное";
            case "SEC" -> stateInfo.getStringParams().get("SEC_NAME");
            default -> "Блюда";
        };

        return title;
    }

}

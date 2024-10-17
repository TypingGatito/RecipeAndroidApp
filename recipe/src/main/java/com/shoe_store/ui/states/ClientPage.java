package com.shoe_store.ui.states;

import com.shoe_store.models.Recipe;
import com.shoe_store.services.RecipeService;
import com.shoe_store.ui.enums.StateName;
import com.shoe_store.ui.info.StateInfo;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class ClientPage implements Page {

    private final Page menu;

    private final StateInfo stateInfo;

    private final RecipeService recipeService;

    private List<Recipe> recipes;

    @Override
    public void display() {
        menu.display();

        if (stateInfo.getUser() == null) System.out.println("Вы не авторизованы (r - зарегестрироваться)");
        System.out.printf("Email: %s\n", stateInfo.getUser().getEmail());
        System.out.printf("Лигон: %s\n", stateInfo.getUser().getLogin());

        recipes = recipeService.findFavoriteRecipesOfUser(stateInfo.getUser().getId());

        printRecipes();
    }

    @Override
    public StateInfo handleInput() {
        StateInfo input = menu.handleInput();

        if (input.getNewState() != null) return input;

        toAuth(input);

        chooseRecipe(input);

        if (input.getNewState() == null) return stateInfo;
        return input;
    }

    private void printRecipes() {
        Consumer<Recipe> recipePrinter = (Recipe r) -> System.out.printf("(%d) %s\n", r.getId(), r.getName());

        recipes.forEach(recipePrinter);
    }

    private void toAuth(StateInfo info) {
        if (stateInfo.getUser() != null) return;
        if (!info.getStringParams().get("Choice").equals("r")) {
            info.setNewState(null);
            System.out.println("Неверный ввод");
            return;
        }

        info.setNewState(StateName.REGISTRATION_PAGE);
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

}

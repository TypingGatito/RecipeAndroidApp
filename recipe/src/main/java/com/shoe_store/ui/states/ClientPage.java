package com.shoe_store.ui.states;

import com.shoe_store.models.Recipe;
import com.shoe_store.services.RecipeService;
import com.shoe_store.ui.enums.StateName;
import com.shoe_store.ui.info.StateInfo;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ClientPage implements Page {

    private final Page menu;

    private final StateInfo stateInfo;

    private final RecipeService recipeService;

    private List<Recipe> recipes;

    @Override
    public void display() {
        menu.display();

        if (stateInfo.getUser() == null) {
            System.out.println("Вы не авторизованы (r - зарегестрироваться)");
            return;
        }

        System.out.printf("Email: %s\n", stateInfo.getUser().getEmail());
        System.out.printf("Лигон: %s\n", stateInfo.getUser().getLogin());

        recipes = recipeService.findFavoriteRecipesOfUser(stateInfo.getUser().getId());

        System.out.println("Посмотреть ваши рецепты (v)");

        if (stateInfo.getUser() != null) {
            System.out.println("Добавить рецепт (a)");
        }

    }

    @Override
    public StateInfo handleInput() {
        StateInfo input = menu.handleInput();

        if (input.getNewState() != null) return input;

        toAuth(input);

        chooseRecipe(input);

        addRecipe(input);

        if (input.getNewState() == null) return stateInfo;
        return input;
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
        if (info.getStringParams().get("Choice").equals("v")) {
            info.setNewState(StateName.RECIPE_LIST);
            info.getStringParams().put("Type", "USER");
        }
    }

    private void addRecipe(StateInfo info) {
        if (stateInfo.getUser() == null) return;
        if (info.getStringParams().get("Choice").equals("a")) {
            info.setNewState(StateName.ADD_RECIPE);
            Map<String, String> params = new HashMap<>();
            params.put("Type", "ADD");
            info.setStringParams(params);
        }
    }
}

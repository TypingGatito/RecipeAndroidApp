package com.recepe.ui.states;

import com.recepe.models.Recipe;
import com.recepe.services.RecipeService;
import com.recepe.ui.enums.StateName;
import com.recepe.ui.info.StateInfo;
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

    private List<Recipe> recipes;

    @Override
    public void display() {
        menu.display();

        Map<String, String> stringParams = stateInfo.getStringParams();

        System.out.printf("------------%s-----------\n", title());

        if (!stringParams.containsKey("Type")) {
            return;
        }

        Consumer<Recipe> recipePrinter = (Recipe r) -> System.out.printf("(%d) %s\n", r.getId(), r.getName());

        if (stringParams.get("Type").equals("ALL")) {
            recipes = recipeService.findAllRecipes();
        }

        if (stringParams.get("Type").equals("FAV")) {
            recipes = recipeService.findFavoriteRecipesOfUser(stateInfo.getUser().getId());
        }

        if (stringParams.get("Type").equals("SEC")) {
            long sec = Long.parseLong(stringParams.get("SEC"));
            recipes = recipeService.findRecipesBySectionId(sec);
        }

        if (stringParams.get("Type").equals("USER")) {
            long userId = stateInfo.getUser().getId();
            recipes = recipeService.findRecipesByUserId(userId);
        }

        recipes.forEach(recipePrinter);

        printCommands();
    }

    @Override
    public StateInfo handleInput() {
        StateInfo stateInfo = menu.handleInput();

        if (stateInfo.getNewState() != null) {
            return stateInfo;
        }

        chooseRecipe(stateInfo);

        if (stateInfo.getNewState() != null) {
            return stateInfo;
        }

        deleteRecipe(stateInfo);

        if (stateInfo.getNewState() == null) {
            return this.stateInfo;
        }

        return stateInfo;
    }

    private void printCommands() {
        System.out.println("Посмотреть блюдо ({номер}d)");
        if (stateInfo.getStringParams().get("Type").equals("USER")) {
            System.out.println("Удалить блюдо ({номер}r)");
        }
    }

    private void chooseRecipe(StateInfo info) {
        Long recipeId;
        try {
            recipeId = Long.parseLong(info.getStringParams().get("Choice").strip().split("d")[0]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
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

    private void deleteRecipe(StateInfo info) {
        if (!info.getStringParams().get("Type").equals("USER")) {
            return;
        }
        Long recipeId;
        try {
            recipeId = Long.parseLong(info.getStringParams().get("Choice").strip().split("r")[0]);
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
            recipeService.removeRecipe(recipeId);
            info.setNewState(null);
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
            case "USER" -> "Ваши блюда";
            default -> "Блюда";
        };

        return title;
    }

}

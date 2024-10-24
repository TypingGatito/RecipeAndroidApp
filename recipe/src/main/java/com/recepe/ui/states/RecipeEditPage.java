package com.recepe.ui.states;

import com.recepe.models.Ingredient;
import com.recepe.models.Recipe;
import com.recepe.models.Section;
import com.recepe.models.Step;
import com.recepe.models.enums.Unit;
import com.recepe.services.IngredientService;
import com.recepe.services.RecipeService;
import com.recepe.services.SectionService;
import com.recepe.services.StepService;
import com.recepe.ui.info.StateInfo;
import lombok.RequiredArgsConstructor;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@RequiredArgsConstructor
public class RecipeEditPage implements Page {

    private final Page menu;

    private final RecipeService recipeService;

    private final SectionService sectionService;

    private final IngredientService ingredientService;

    private final StepService stepService;

    private final StateInfo stateInfo;

    private Recipe recipe;

    @Override
    public void display() {
        menu.display();

        Map<String, String> stringParams = stateInfo.getStringParams();

        if (stringParams.get("Type").equals("ADD")) {
            System.out.println("--------Добавить рецепт--------");
            System.out.println("Введите st, чтобы продолжить");
        }

        if (stringParams.get("Type").equals("EDIT")) {
            Long recipeId = Long.parseLong(stringParams.get("RecipeId"));
            recipe = recipeService.findRecipeById(recipeId);
            System.out.println("i - добавить ингредиент");
            System.out.println("s - добавить шаг");
        }

    }

    @Override
    public StateInfo handleInput() {
        Map<String, String> stringParams = stateInfo.getStringParams();

        StateInfo stateInfo = menu.handleInput();

        if (stateInfo.getNewState() != null) {
            return stateInfo;
        }

        if (stringParams.get("Type").equals("ADD")) {
            Recipe recipe1 = newRecipe();
            recipeService.addRecipe(recipe1);
        }

        if (stringParams.get("Type").equals("EDIT")) {
            Long recipeId = Long.parseLong(stringParams.get("RecipeId"));
            recipe = recipeService.findRecipeById(recipeId);
            editRecipe(stateInfo);
        }

        if (stateInfo.getNewState() == null) {
            return this.stateInfo;
        }

        return stateInfo;
    }

    private Recipe newRecipe() {
        Scanner scanner = new Scanner(System.in);
        Recipe recipe = new Recipe();

        recipe.setUserId(stateInfo.getUser().getId());

        List<Section> sections = sectionService.getAllSections();
        System.out.println("Выберите номер секции:");
        for (int i = 0; i < sections.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, sections.get(i).getName());
        }

        int sectionChoice;
        while (true) {
            try {
                sectionChoice = Integer.parseInt(scanner.nextLine());
                if (sectionChoice < 1 || sectionChoice > sections.size()) {
                    System.out.println("Некорректный выбор, попробуйте снова.");
                } else {
                    recipe.setSectionId(sections.get(sectionChoice - 1).getId());
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите числовое значение.");
            }
        }

        System.out.println("Введите название рецепта:");
        recipe.setName(scanner.nextLine());

        System.out.println("Введите количество калорий на 100 г:");
        recipe.setCaloriesOnHundG(Integer.parseInt(scanner.nextLine()));

        System.out.println("Введите время на приготовление в минутах:");
        recipe.setTimeToCook(Duration.ofMinutes(Long.parseLong(scanner.nextLine())));

        System.out.println("Введите количество порций:");
        recipe.setDoseNum(Integer.parseInt(scanner.nextLine()));

        System.out.println("Введите краткое описание рецепта:");
        recipe.setShortDescription(scanner.nextLine());

        return recipe;
    }

    private void editRecipe(StateInfo info) {
        System.out.println("i-добавить ингредиент");
        System.out.println("s-добавить шаг");
        if (info.getStringParams().get("Choice").equals("i")) {
            Ingredient ingredient = newIngredient();
            ingredientService.add(ingredient);
        }

        if (info.getStringParams().get("Choice").equals("s")) {

            Step step = newStep();
            stepService.addStep(step);
        }
    }

    private Ingredient newIngredient() {
        Scanner scanner = new Scanner(System.in);
        Ingredient ingredient = new Ingredient();

        ingredient.setRecipeId(recipe.getId());

        System.out.println("Введите название ингредиента:");
        ingredient.setName(scanner.nextLine());

        System.out.println("Введите количество ингредиента:");
        ingredient.setAmount(Integer.parseInt(scanner.nextLine()));

        System.out.println("Выберите единицу измерения из списка:");
        Unit[] units = Unit.values();
        for (int i = 0; i < units.length; i++) {
            System.out.printf("%d. %s\n", i + 1, units[i]);
        }

        int unitChoice;
        while (true) {
            try {
                unitChoice = Integer.parseInt(scanner.nextLine());
                if (unitChoice < 1 || unitChoice > units.length) {
                    System.out.println("Некорректный выбор, попробуйте снова.");
                } else {
                    ingredient.setUnit(units[unitChoice - 1]);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите числовое значение.");
            }
        }

        return ingredient;
    }

    private Step newStep() {
        Scanner scanner = new Scanner(System.in);
        Step step = new Step();

        step.setRecipeId(recipe.getId());

        System.out.println("Введите номер шага: ");
        step.setNum(Integer.parseInt(scanner.nextLine()));

        System.out.println("Введите текст: ");
        step.setText(scanner.nextLine());

        return step;
    }

}

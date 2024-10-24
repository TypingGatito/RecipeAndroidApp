package com.recipe.ui;

import com.recipe.config.ServicesConfig;
import com.recipe.ui.info.StateInfo;
import com.recipe.ui.states.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StateFactory {

    private final ServicesConfig servicesConfig;

    public Page getState(StateInfo info) {
        Page page = switch (info.getNewState()) {
            case MAIN -> createMainState(info);
            case RECIPE_LIST -> createDishListState(info);
            case CLIENT_PAGE -> createClientPage(info);
            case RECIPE_PAGE -> createRecipePage(info);
            case REGISTRATION_PAGE -> createRegistrationState();
            case ADMIN -> createAdminState(info);
            case ADD_RECIPE -> createRecipeEdiPage(info);
        };

        return page;
    }

    private Page createRecipeEdiPage(StateInfo info) {
        return new RecipeEditPage(menuHead(info), servicesConfig.getRecipeService(),
                servicesConfig.getSectionService(),
                servicesConfig.getIngredientService(),
                servicesConfig.getStepService(),
                info);
    }

    private Page createAdminState(StateInfo info) {
        return new AdminPage(menuHead(info), servicesConfig.getAdminService(), info);
    }

    private Page createRecipePage(StateInfo info) {
        return new RecipePage(menuHead(info), servicesConfig.getRecipeService(),
                servicesConfig.getIngredientService(),
                servicesConfig.getRatingService(),
                info,
                servicesConfig.getStepService(),
                servicesConfig.getCommentaryService());
    }

    private Page createDishListState(StateInfo info) {
        return new RecipesListPage(menuHead(info), servicesConfig.getRecipeService(),
                info);
    }

    private Page createClientPage(StateInfo info) {
        return new ClientPage(menuHead(info), info, servicesConfig.getRecipeService());
    }

    private Page createMainState(StateInfo info) {
        return new MainPage(menuHead(info), servicesConfig.getSectionService());
    }

    private Page createRegistrationState() {
        return new AuthorizationPage(servicesConfig.getUserService());
    }

    private Page menuHead(StateInfo info) {
        return new MenuHead(info);
    }

}

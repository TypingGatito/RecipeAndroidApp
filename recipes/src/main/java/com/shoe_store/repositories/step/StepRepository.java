package com.shoe_store.repositories.step;

import com.shoe_store.models.Step;
import java.util.ArrayList;
import java.util.List;

public final class StepRepository implements IStepRepository {

    private static StepRepository instance;

    public static synchronized StepRepository getInstance() {
        if (instance == null) instance = new StepRepository();

        return instance;
    }

    private StepRepository() {
    }

    private final List<Step> steps = new ArrayList<>();


    @Override
    public Step findStepById(Long id) {
        return null;
    }

    @Override
    public List<Step> findRecipeSteps(Long recipeId) {
        return List.of();
    }

    @Override
    public Boolean addStep(Step step) {
        return null;
    }

    @Override
    public Boolean updateStep(Step step) {
        return null;
    }

    @Override
    public Boolean deleteStep(Long stepId) {
        return null;
    }

}

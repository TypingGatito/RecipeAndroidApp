package com.shoe_store.in_memory.repositories;

import com.shoe_store.in_memory.db.InMemoryInfo;
import com.shoe_store.models.Step;
import com.shoe_store.repositories.IStepRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class StepRepository implements IStepRepository {

    private final InMemoryInfo inMemoryInfo;

    @Override
    public Step findStepById(Long id) {
        return inMemoryInfo
                .getSteps()
                .stream()
                .filter((s) -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Step> findRecipeSteps(Long recipeId) {
        return inMemoryInfo
                .getSteps()
                .stream()
                .filter((s) -> s.getRecipeId().equals(recipeId))
                .toList();
    }

    @Override
    public Boolean addStep(Step step) {
        if (inMemoryInfo.getSteps().contains(step)) return false;

        return true;
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

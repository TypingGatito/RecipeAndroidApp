package com.recipe.sql_repositories;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.in_memory.db.InMemoryInfo;
import com.recipe.models.Step;
import com.recipe.repositories.IStepRepository;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Element
@RequiredArgsConstructor
@NoArgsConstructor
public final class StepRepository implements IStepRepository {

    @Injected
    @NonNull
    private InMemoryInfo inMemoryInfo;

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
        step.setId(inMemoryInfo.getSteps()
                .stream()
                .mapToLong(Step::getId)
                .max()
                .orElse(0) + 1);

        inMemoryInfo.getSteps().add(step);
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

package com.recipe.services;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.models.Step;
import com.recipe.repositories.IStepRepository;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor
@Element
public class StepService {

    @Injected
    @NonNull
    private IStepRepository stepRepository;

    public List<Step> findRecipeSteps(Long recipeId) {
        return stepRepository.findRecipeSteps(recipeId);
    }

    public Boolean addStep(Step step) {
        return stepRepository.addStep(step);
    }

}

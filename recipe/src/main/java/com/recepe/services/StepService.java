package com.recepe.services;

import com.recepe.models.Step;
import com.recepe.repositories.IStepRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StepService {

    private final IStepRepository stepRepository;

    public List<Step> findRecipeSteps(Long recipeId) {
        return stepRepository.findRecipeSteps(recipeId);
    }

    public Boolean addStep(Step step) {
        return stepRepository.addStep(step);
    }

}

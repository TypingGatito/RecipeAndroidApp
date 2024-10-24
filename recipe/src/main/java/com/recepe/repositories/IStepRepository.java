package com.recepe.repositories;

import com.recepe.models.Step;

import java.util.List;

public interface IStepRepository {

    Step findStepById(Long id);

    List<Step> findRecipeSteps(Long recipeId);

    Boolean addStep(Step step);

    Boolean updateStep(Step step);

    Boolean deleteStep(Long stepId);

}

package com.recipe.services;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.models.Rating;
import com.recipe.repositories.IRatingRepository;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Element
@NoArgsConstructor
@RequiredArgsConstructor
public class RatingService {

    @Injected
    @NonNull
    private IRatingRepository ratingRepository;

    public Double findRatingByRecipeId(Long recipeId) {
        return ratingRepository.findRatingByRecipeId(recipeId);
    }

    public Rating findRating(Long userId, Long recipeId) {
        return ratingRepository.findRating(userId, recipeId);
    }

    public void setRating(Long userId, Long recipeId, Double ratingV) {
        ratingRepository.setRating(userId, recipeId, ratingV);
    }

    public void deleteRating(Long userId, Long recipeId) {
        ratingRepository.deleteRating(userId, recipeId);
    }

}

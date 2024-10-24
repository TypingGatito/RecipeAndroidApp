package com.recipe.services;

import com.recipe.models.Rating;
import com.recipe.repositories.IRatingRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RatingService {

    private final IRatingRepository ratingRepository;

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

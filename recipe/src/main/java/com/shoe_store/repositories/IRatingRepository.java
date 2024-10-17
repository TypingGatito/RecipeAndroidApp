package com.shoe_store.repositories;

import com.shoe_store.models.Rating;

import java.util.List;

public interface IRatingRepository {

    List<Rating> findRatingsByUserId(Long id);

    List<Rating> findRatingsByRecipeId(Long id);

    Rating findRating(Long userId, Long recipeId);

    Double findRatingByRecipeId(Long recipeId);

    void setRating(Long userId, Long recipeId, Double rating);

    void deleteRating(Long userId, Long recipeId);

}
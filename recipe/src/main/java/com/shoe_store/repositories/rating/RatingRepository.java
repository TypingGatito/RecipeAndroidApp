package com.shoe_store.repositories.rating;

import com.shoe_store.in_memory_info.InMemoryInfo;
import com.shoe_store.models.Rating;
import com.shoe_store.models.Step;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public final class RatingRepository implements IRatingRepository {

    private final InMemoryInfo inMemoryInfo;

    @Override
    public List<Rating> findRatingsByUserId(Long id) {
        return List.of();
    }

    @Override
    public List<Rating> findRatingsByRecipeId(Long recipeId) {
        return inMemoryInfo
                .getRatings()
                .stream()
                .filter(r -> r.getRecipeId().equals(recipeId))
                .toList();
    }

    @Override
    public Rating findRating(Long userId, Long recipeId) {
        return inMemoryInfo
                .getRatings()
                .stream()
                .filter(r -> r.getRecipeId().equals(recipeId) && r.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

}

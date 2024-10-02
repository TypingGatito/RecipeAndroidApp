package com.shoe_store.repositories.rating;

import com.shoe_store.models.Rating;
import com.shoe_store.models.Step;
import java.util.ArrayList;
import java.util.List;

public final class RatingRepository implements IRatingRepository {

    private static RatingRepository instance;

    public static synchronized RatingRepository getInstance() {
        if (instance == null) instance = new RatingRepository();

        return instance;
    }

    private RatingRepository() {
    }

    private final List<Step> steps = new ArrayList<>();


    @Override
    public List<Rating> findRatingsByUserId(Long id) {
        return List.of();
    }

    @Override
    public List<Rating> findRatingsByRecipeId(Long id) {
        return List.of();
    }

    @Override
    public Double getAverageRatingByRecipeId(Long id) {
        return 0.0;
    }

    @Override
    public Rating findRating(Long userId, Long recipeId) {
        return null;
    }

}

package com.shoe_store.in_memory.repositories;


import com.shoe_store.in_memory.db.InMemoryInfo;
import com.shoe_store.models.Rating;
import com.shoe_store.repositories.IRatingRepository;
import lombok.RequiredArgsConstructor;

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

    @Override
    public void setRating(Long userId, Long recipeId, Double ratingV) {
        Rating rating =  inMemoryInfo
                .getRatings()
                .stream()
                .filter(r -> r.getRecipeId().equals(recipeId) && r.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
        if (rating == null) {
            rating = new Rating();
            rating.setUserId(userId);
            rating.setRecipeId(recipeId);
            inMemoryInfo.getRatings().add(rating);
        }
        rating.setRating(ratingV);
    }

    @Override
    public Double findRatingByRecipeId(Long recipeId) {
        List<Double> favourites = inMemoryInfo.getRatings().
                stream()
                .filter(r -> r.getRecipeId().equals(recipeId))
                .map(Rating::getRating)
                .toList();

        Double sum = 0d;
        for (Double el: favourites) sum += el;
        return sum / favourites.size();
    }

    @Override
    public void deleteRating(Long userId, Long recipeId) {
        inMemoryInfo.getRatings().removeIf(r -> r.getUserId().equals(userId) && r.getRecipeId().equals(recipeId));
    }

}

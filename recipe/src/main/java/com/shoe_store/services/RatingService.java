package com.shoe_store.services;

import com.shoe_store.repositories.rating.IRatingRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RatingService {

    private final IRatingRepository ratingRepository;

}

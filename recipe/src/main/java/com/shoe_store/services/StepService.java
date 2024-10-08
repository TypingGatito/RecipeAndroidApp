package com.shoe_store.services;

import com.shoe_store.repositories.step.IStepRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StepService {

    private final IStepRepository stepRepository;

}

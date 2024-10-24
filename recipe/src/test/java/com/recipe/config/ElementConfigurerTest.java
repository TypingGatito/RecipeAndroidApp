package com.recipe.config;

import com.recipe.services.StepService;
import org.junit.jupiter.api.Test;

class ElementConfigurerTest {

    @Test
    public void testElementConfigurer() {
        ElementConfigurer configurer = new ElementConfigurer();

        StepService stepService = new StepService();

        try {
            configurer.configureObject(stepService);
        } catch (Exception e) {
            System.out.println("Step service was not initialized");
        }

    }

}
package com.recipe.ui;

import com.recipe.config.ElementConfigurer;
import com.recipe.config.ServicesConfig;
import com.recipe.ui.enums.StateName;

public class ConsoleApp {

    private ElementConfigurer elementConfigurer = new ElementConfigurer();

    public ConsoleApp() {
    }

    public void run() {

        ServicesConfig servicesConfig = new ServicesConfig();
        Context context;
        try {
            elementConfigurer.configureObject(servicesConfig);
        } catch (Exception e) {
            throw new RuntimeException("Sevices config error" + e.getMessage());
        }

        context = new Context(StateName.REGISTRATION_PAGE,
                new StateFactory(servicesConfig));

        while (true) {
            context.display();
            context.handleInput();
        }

    }

}

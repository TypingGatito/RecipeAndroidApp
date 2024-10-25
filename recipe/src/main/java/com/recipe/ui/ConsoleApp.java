package com.recipe.ui;

import com.recipe.config.ElementConfigurer;
import com.recipe.ui.enums.StateName;

public class ConsoleApp {

    private final ElementConfigurer elementConfigurer = new ElementConfigurer();

    public ConsoleApp() {
    }

    public void run() {
        Context context = new Context();

        elementConfigurer.configureObject(context);

        context.setStateName(StateName.REGISTRATION_PAGE);

        while (context.hasContent()) {
            context.display();
            context.handleInput();
        }

    }

}

package com.recipe.ui;

import com.recipe.config.Configurer;
import com.recipe.ui.enums.StateName;

public class ConsoleApp {

    public ConsoleApp() {
    }

    public void run() {
        Context context = new Context(StateName.REGISTRATION_PAGE, new StateFactory(new Configurer().config()));

        while (true) {
            context.display();
            context.handleInput();
        }
    }

}

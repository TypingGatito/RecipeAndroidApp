package com.shoe_store.ui;

import com.shoe_store.config.Configurer;
import com.shoe_store.ui.enums.StateName;

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

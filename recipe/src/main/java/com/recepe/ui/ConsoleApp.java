package com.recepe.ui;

import com.recepe.config.Configurer;
import com.recepe.ui.enums.StateName;

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

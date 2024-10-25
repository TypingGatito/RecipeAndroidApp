package com.recipe.ui;

import com.recipe.ui.enums.StateName;
import com.recipe.ui.info.StateInfo;
import com.recipe.ui.states.Page;

public class Context {

    private Page page;

    private final StateFactory stateFactory;

    public Context(StateName stateName, StateFactory stateFactory) {
        StateInfo stateInfo = new StateInfo();
        stateInfo.setNewState(stateName);
        this.page = stateFactory.getState(stateInfo);
        this.stateFactory = stateFactory;
    }

    public void display() {
        page.display();
    }

    public void handleInput() {
        StateInfo info = page.handleInput();

        page = stateFactory.getState(info);
    }

    public boolean hasContent() {
        return this.page != null;
    }

}

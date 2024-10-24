package com.recepe.ui;

import com.recepe.ui.enums.StateName;
import com.recepe.ui.info.StateInfo;
import com.recepe.ui.states.Page;

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

}

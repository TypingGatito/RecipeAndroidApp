package com.recipe.ui.info;

import com.recipe.models.User;
import com.recipe.models.enums.UserRole;
import com.recipe.ui.enums.StateName;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class StateInfo {

    private StateName newState;

    private Map<String, String> stringParams = new HashMap<String, String>();

    private Set<UserRole> rolesSet;

    private User user;

    @Override
    public StateInfo clone() {
        StateInfo stateInfo = new StateInfo();
        stateInfo.setNewState(this.newState);
        stateInfo.setStringParams( new HashMap<String, String>(this.stringParams));
        stateInfo.setRolesSet(this.rolesSet);
        stateInfo.setUser(this.user);
        return stateInfo;
    }

}

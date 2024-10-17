package com.shoe_store.ui.states;

import com.shoe_store.models.User;
import com.shoe_store.services.AdminService;
import com.shoe_store.services.RecipeService;
import com.shoe_store.ui.enums.StateName;
import com.shoe_store.ui.info.StateInfo;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class AdminPage implements Page {

    private final Page menu;

    private final AdminService adminService;

    private final StateInfo stateInfo;

    private List<User> users;

    @Override
    public void display() {
        menu.display();
        System.out.println("----------Панель администратора----------");

        users = adminService.getUsers()
                .stream()
                .filter(u -> !u.getId().equals(stateInfo.getUser().getId()))
                .toList();

        for (int i = 0; i < users.size(); i++) {
            System.out.printf("(%d) %s %s\n", i, users.get(i).getEmail(), users.get(i).getIsActive());
        }

        printCommands();
    }

    @Override
    public StateInfo handleInput() {
        StateInfo input = menu.handleInput();

        if (input.getNewState() != null) return input;

        banUser(input);

        if (input.getNewState() == null) return stateInfo;
        return input;
    }

    private void printCommands() {
        System.out.println("Бан: b-{№}");
        System.out.println("Разбан: u-{№}");
    }

    private void banUser(StateInfo info) {
        Long userId;
        try {
            userId = Long.parseLong(info.getStringParams().get("Choice").strip().split("-")[1]);
        } catch (NumberFormatException e) {
            info.setNewState(null);
            return;
        }

        if (info.getStringParams().get("Choice").strip().split("-")[0].equals("b")) {
            adminService.setBan(users.get(userId.intValue()).getId(), false);
        }

        if (info.getStringParams().get("Choice").strip().split("-")[0].equals("u")) {
            adminService.setBan(users.get(userId.intValue()).getId(), true);
        }

        info.setNewState(StateName.ADMIN);
    }

}

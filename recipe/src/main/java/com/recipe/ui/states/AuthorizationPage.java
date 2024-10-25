package com.recipe.ui.states;

import com.recipe.models.User;
import com.recipe.services.UserService;
import com.recipe.ui.enums.StateName;
import com.recipe.ui.info.StateInfo;
import lombok.RequiredArgsConstructor;
import java.util.Scanner;

@RequiredArgsConstructor
public class AuthorizationPage implements Page {

    private final UserService userService;

    @Override
    public void display() {
        System.out.println("----- Авторизация -----");
        System.out.print("Выйти [Y(да)/N(нет)]: ");
    }

    @Override
    public StateInfo handleInput() {
        StateInfo info = new StateInfo();

        Scanner scanner = new Scanner(System.in);
        if (handleExit(scanner)) return null;

        boolean reg = handleRegistration(scanner);

        if (reg) {
            register(scanner, info);
        } else {
            authorize(scanner, info);
        }

        return info;
    }

    private boolean handleExit(Scanner scanner) {
        String input = scanner.nextLine().toLowerCase();

        while (!input.equals("y") && !input.equals("n")) {
            System.out.print("Выйти [Y(да)/N(нет)]: ");
            input = scanner.nextLine().toLowerCase();
        }

        return input.equals("y");
    }

    private boolean handleRegistration(Scanner scanner) {
        String input = "";

        while (!input.equals("r") && !input.equals("a")) {
            System.out.print("Зарегистрироваться / Авторизоваться [R/A]: ");
            input = scanner.nextLine().toLowerCase();
        }

        return input.equals("r");
    }

    private void authorize(Scanner scanner, StateInfo info) {
        System.out.print("Введите email: ");
        String email = scanner.nextLine();

        User user = userService.findUserByEmail(email);
        if (user == null) {
            System.out.println("Пользователь не найден");
            info.setNewState(StateName.REGISTRATION_PAGE);
            return;
        }

        if (!user.getIsActive()) {
            System.out.println("Пользователь заблокированан");
            info.setNewState(StateName.REGISTRATION_PAGE);
            return;
        }

        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        if (!password.equals(user.getPassword())) {
            System.out.println("Неверный пароль");
            info.setNewState(StateName.REGISTRATION_PAGE);
            return;
        }

        info.setNewState(StateName.MAIN);
        info.setUser(user);
        info.setRolesSet(userService.getUserRoles(user.getId()));
    }

    private void register(Scanner scanner, StateInfo info) {
        User newUser = new User();

        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        newUser.setEmail(email);

        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        newUser.setEmail(login);

        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        newUser.setPassword(password);

        if(!userService.addUser(newUser)) return;

        info.setNewState(StateName.MAIN);
        info.setUser(newUser);
    }

}

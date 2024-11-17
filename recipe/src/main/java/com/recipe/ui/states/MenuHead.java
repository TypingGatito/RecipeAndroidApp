package com.recipe.ui.states;

import com.recipe.models.enums.UserRole;
import com.recipe.ui.enums.StateName;
import com.recipe.ui.info.StateInfo;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Scanner;

@RequiredArgsConstructor
public class MenuHead implements Page {

    private final StateInfo stateInfo;

    @Override
    public void display() {
        System.out.println("----------------------------");
        System.out.print(stateInfo.getUser().getLogin());
        System.out.print(" Выход(0)");
        System.out.print(" | ");
        System.out.print("Очистить(c)");
        if (stateInfo.getRolesSet() != null && stateInfo.getRolesSet().contains(UserRole.ADMIN)) {
            System.out.print(" | ");
            System.out.print(" Админ(adm)");
        }
        System.out.print(" | ");
        System.out.println();
        System.out.print("== ");
        System.out.print(" | ");
        System.out.print("Главная(1)");
        System.out.print(" | ");
        System.out.print("Блюда(2)");
        System.out.print(" | ");
        System.out.print("Избранные блюда(3)");
        System.out.print(" | ");
        System.out.print("Профиль(4)");
        System.out.print(" | ");
        System.out.println(" ==");
    }

    @Override
    public StateInfo handleInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Выберите действие: ");
        String choice = scanner.nextLine();

        StateInfo newInfo = stateInfo.clone();

        switch (choice) {
            case "1":
                newInfo.setNewState(StateName.MAIN);
                break;
            case "2":
                newInfo.setNewState(StateName.RECIPE_LIST);
                newInfo.getStringParams().put("Type", "ALL");
                break;
            case "3":
                newInfo.setNewState(StateName.RECIPE_LIST);
                newInfo.getStringParams().put("Type", "FAV");
                break;
            case "4":
                newInfo.setNewState(StateName.CLIENT_PAGE);
                break;
            case "c":
                try {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                newInfo.setNewState(StateName.MAIN);
                break;
            case "0":
                System.out.println("Выход из аккаунта.");
                newInfo.setNewState(StateName.REGISTRATION_PAGE);
                break;
            default:
                newInfo.setNewState(null);
                newInfo.getStringParams().put("Choice", choice);
        }

        if (stateInfo.getRolesSet().contains(UserRole.ADMIN) && choice.equals("adm")){
            newInfo.setNewState(StateName.ADMIN);
        }

        return newInfo;
    }

}

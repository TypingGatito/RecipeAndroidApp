package com.recipe.ui.states;

import com.recipe.models.Section;
import com.recipe.services.SectionService;
import com.recipe.ui.enums.StateName;
import com.recipe.ui.info.StateInfo;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
public class MainPage implements Page {

    private final Page menu;

    private final SectionService sectionService;

    @Override
    public void display() {
        menu.display();

        System.out.println("Введите номер секции в формате ({номер}s)");
        sectionService.getAllSections().forEach(section
                -> System.out.printf("(%d)%s\n", section.getId(), section.getName()));
    }

    @Override
    public StateInfo handleInput() {
        StateInfo stateInfo = menu.handleInput();

        if (stateInfo.getNewState() != null) {
            return stateInfo;
        }

        chooseSection(stateInfo);

        if (stateInfo.getNewState() == null) {
            stateInfo.setNewState(StateName.MAIN);
        }

        return stateInfo;
    }

    private void chooseSection(StateInfo info) {
        List<Section> sections = sectionService.getAllSections();

        Long sectionId;
        try {
            sectionId = Long.parseLong(info.getStringParams().get("Choice").strip().split("s")[0]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Неверный ввод");
            return;
        }

        Section section = sections
                .stream()
                .filter(u -> u.getId().equals(sectionId))
                .findFirst()
                .orElse(null);
        if (section != null) {
            info.setNewState(StateName.RECIPE_LIST);
            info.setStringParams(new HashMap<String, String>());
            info.getStringParams().put("Type", "SEC");
            info.getStringParams().put("SEC", sectionId.toString());
            info.getStringParams().put("SEC_NAME", section.getName());
        }
    }

}

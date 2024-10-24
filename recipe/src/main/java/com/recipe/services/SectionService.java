package com.recipe.services;

import com.recipe.annotations.Element;
import com.recipe.models.Section;
import com.recipe.repositories.ISectionRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Element
@RequiredArgsConstructor
public class SectionService {

    private final ISectionRepository sectionRepository;

    public List<Section> getAllSections() {
        return sectionRepository.findAllSections();
    }

}

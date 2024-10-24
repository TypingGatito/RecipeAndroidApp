package com.recipe.services;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.models.Section;
import com.recipe.repositories.ISectionRepository;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Element
@NoArgsConstructor
@RequiredArgsConstructor
public class SectionService {

    @Injected
    @NonNull
    private ISectionRepository sectionRepository;

    public List<Section> getAllSections() {
        return sectionRepository.findAllSections();
    }

}

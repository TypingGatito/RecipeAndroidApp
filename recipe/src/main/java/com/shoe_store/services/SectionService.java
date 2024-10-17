package com.shoe_store.services;

import com.shoe_store.models.Section;
import com.shoe_store.repositories.ISectionRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SectionService {

    private final ISectionRepository sectionRepository;

    public List<Section> getAllSections() {
        return sectionRepository.findAllSections();
    }

}

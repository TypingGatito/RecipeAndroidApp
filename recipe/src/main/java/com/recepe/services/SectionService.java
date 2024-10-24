package com.recepe.services;

import com.recepe.models.Section;
import com.recepe.repositories.ISectionRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SectionService {

    private final ISectionRepository sectionRepository;

    public List<Section> getAllSections() {
        return sectionRepository.findAllSections();
    }

}

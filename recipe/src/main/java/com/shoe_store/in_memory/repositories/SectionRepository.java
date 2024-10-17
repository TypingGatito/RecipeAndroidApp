package com.shoe_store.in_memory.repositories;

import com.shoe_store.in_memory.db.InMemoryInfo;
import com.shoe_store.models.Section;
import com.shoe_store.repositories.ISectionRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class SectionRepository implements ISectionRepository {

    private final InMemoryInfo inMemoryInfo;

    @Override
    public List<Section> findAllSections() {
        return inMemoryInfo.getSections();
    }

}

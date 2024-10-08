package com.shoe_store.repositories.section;

import com.shoe_store.in_memory_info.InMemoryInfo;
import com.shoe_store.models.Section;
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

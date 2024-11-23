package com.recipe.in_memory.repositories;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.in_memory.db.InMemoryInfo;
import com.recipe.models.Section;
import com.recipe.repositories.ISectionRepository;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Element
@RequiredArgsConstructor
@NoArgsConstructor
public final class SectionRepository implements ISectionRepository {

    @Injected
    @NonNull
    private InMemoryInfo inMemoryInfo;

    @Override
    public List<Section> findAllSections() {
        return inMemoryInfo.getSections();
    }

    @Override
    public Section findSectionById(Long id) {
        return inMemoryInfo.getSections()
                .stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}

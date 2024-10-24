package com.recepe.in_memory.repositories;

import com.recepe.annotations.Element;
import com.recepe.annotations.Injected;
import com.recepe.in_memory.db.InMemoryInfo;
import com.recepe.models.Section;
import com.recepe.repositories.ISectionRepository;
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

}

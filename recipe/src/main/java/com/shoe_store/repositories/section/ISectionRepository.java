package com.shoe_store.repositories.section;

import com.shoe_store.models.Section;
import java.util.List;

public interface ISectionRepository {

    List<Section> findAllSections();

}

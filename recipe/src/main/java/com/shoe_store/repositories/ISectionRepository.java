package com.shoe_store.repositories;

import com.shoe_store.models.Section;
import java.util.List;

public interface ISectionRepository {

    List<Section> findAllSections();

}

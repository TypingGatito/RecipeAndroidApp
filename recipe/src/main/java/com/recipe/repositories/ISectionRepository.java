package com.recipe.repositories;

import com.recipe.models.Section;
import java.util.List;

public interface ISectionRepository {

    List<Section> findAllSections();

}

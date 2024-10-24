package com.recepe.repositories;

import com.recepe.models.Section;
import java.util.List;

public interface ISectionRepository {

    List<Section> findAllSections();

}

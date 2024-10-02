package com.shoe_store.repositories.section;

import com.shoe_store.models.Section;
import com.shoe_store.models.User;
import com.shoe_store.repositories.user.UserRepository;
import java.util.ArrayList;
import java.util.List;

public final class SectionRepository implements ISectionRepository {

    private static SectionRepository instance;

    public static synchronized SectionRepository getInstance() {
        if (instance == null) instance = new SectionRepository();

        return instance;
    }

    private SectionRepository() {
    }

    private final List<User> users = new ArrayList<>();

    @Override
    public List<Section> findAllSections() {
        return List.of();
    }

}

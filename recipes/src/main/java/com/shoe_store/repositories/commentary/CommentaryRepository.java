package com.shoe_store.repositories.commentary;

import com.shoe_store.models.Commentary;
import com.shoe_store.models.User;
import com.shoe_store.repositories.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public final class CommentaryRepository implements ICommentaryRepository {

    private static CommentaryRepository instance;

    public static synchronized CommentaryRepository getInstance() {
        if (instance == null) instance = new CommentaryRepository();

        return instance;
    }

    private CommentaryRepository() {
    }

    private final List<User> users = new ArrayList<>();


    @Override
    public List<Commentary> findUserCommentaries(Long userId) {
        return List.of();
    }

    @Override
    public List<Commentary> findStepCommentaries(Long stepId) {
        return List.of();
    }

    @Override
    public List<Commentary> findUserStepCommentaries(Long userId, Long stepId) {
        return List.of();
    }

    @Override
    public Boolean addCommentary(Commentary commentary) {
        return null;
    }

    @Override
    public Boolean updateCommentary(Commentary commentary) {
        return null;
    }

    @Override
    public Boolean deleteCommentary(Long id) {
        return null;
    }

}

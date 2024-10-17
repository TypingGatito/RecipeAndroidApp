package com.shoe_store.in_memory.repositories;


import com.shoe_store.in_memory.db.InMemoryInfo;
import com.shoe_store.models.Commentary;
import com.shoe_store.repositories.ICommentaryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class CommentaryRepository implements ICommentaryRepository {

    private final InMemoryInfo inMemoryInfo;

    @Override
    public List<Commentary> findUserCommentaries(Long userId) {
        return List.of();
    }

    @Override
    public List<Commentary> findStepCommentaries(Long stepId) {
        return inMemoryInfo
                .getCommentaries()
                .stream()
                .filter(s -> s.getStepId().equals(stepId))
                .toList();
    }

    @Override
    public List<Commentary> findUserStepCommentaries(Long userId, Long stepId) {
        return inMemoryInfo
                .getCommentaries()
                .stream()
                .filter(c -> c.getStepId().equals(stepId) && c.getUserId().equals(userId))
                .toList();
    }

    @Override
    public Boolean addCommentary(Commentary commentary) {
        return inMemoryInfo
                .getCommentaries()
                .add(commentary);
    }

    @Override
    public Boolean updateCommentary(Commentary commentary) {
        return null;
    }

    @Override
    public Boolean deleteCommentary(Long stepId, Long userId) {
        return inMemoryInfo
                .getCommentaries()
                .removeIf(c -> c.getStepId().equals(stepId) && c.getUserId().equals(userId));
    }

}

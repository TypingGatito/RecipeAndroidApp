package com.shoe_store.services;

import com.shoe_store.models.Commentary;
import com.shoe_store.repositories.ICommentaryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CommentaryService {

    private final ICommentaryRepository commentaryRepository;

    public Boolean deleteCommentary(Long stepId, Long userId, Integer order_num) {
        return commentaryRepository.deleteCommentary(stepId, userId, order_num);
    }

    public List<Commentary> findUserStepCommentaries(Long userId, Long stepId) {
        return commentaryRepository.findUserStepCommentaries(userId, stepId);
    }

    public Boolean addCommentary(Commentary commentary) {
        return commentaryRepository.addCommentary(commentary);
    }

    public Boolean updateCommentary(Commentary commentary) {
        return commentaryRepository.updateCommentary(commentary);
    }

}

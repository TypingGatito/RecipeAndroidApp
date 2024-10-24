package com.recipe.services;

import com.recipe.models.Commentary;
import com.recipe.repositories.ICommentaryRepository;
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

package com.recipe.services;

import com.recipe.annotations.Element;
import com.recipe.annotations.Injected;
import com.recipe.models.Commentary;
import com.recipe.repositories.ICommentaryRepository;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Element
@NoArgsConstructor
@RequiredArgsConstructor
public class CommentaryService {

    @Injected
    @NonNull
    private ICommentaryRepository commentaryRepository;

    public Boolean deleteCommentary(Long stepId, Long userId, Integer order_num) {
        return commentaryRepository.deleteCommentary(stepId, userId, order_num);
    }

    public List<Commentary> findUserStepCommentaries(Long userId, Long stepId) {
        return commentaryRepository.findUserStepCommentaries(userId, stepId)
                .stream().sorted((c1, c2) -> c1.getOrderNum() - c2.getOrderNum())
                .toList();
    }

    public Boolean addCommentary(Commentary commentary) {
        return commentaryRepository.addCommentary(commentary);
    }

    public Boolean updateCommentary(Commentary commentary) {
        return commentaryRepository.updateCommentary(commentary);
    }

}

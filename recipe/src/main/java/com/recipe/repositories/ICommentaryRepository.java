package com.recipe.repositories;

import com.recipe.models.Commentary;
import java.util.List;

public interface ICommentaryRepository {

    List<Commentary> findUserCommentaries(Long userId);

    List<Commentary> findStepCommentaries(Long stepId);

    List<Commentary> findUserStepCommentaries(Long userId, Long stepId);

    Boolean addCommentary(Commentary commentary);

    Boolean updateCommentary(Commentary commentary);

    Boolean deleteCommentary(Long stepId, Long userId, Integer order_num);

}

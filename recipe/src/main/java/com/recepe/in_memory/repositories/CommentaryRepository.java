package com.recepe.in_memory.repositories;


import com.recepe.annotations.Element;
import com.recepe.annotations.Injected;
import com.recepe.in_memory.db.InMemoryInfo;
import com.recepe.models.Commentary;
import com.recepe.repositories.ICommentaryRepository;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Element
@RequiredArgsConstructor
@NoArgsConstructor
public final class CommentaryRepository implements ICommentaryRepository {

    @Injected
    @NonNull
    private InMemoryInfo inMemoryInfo;

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
        commentary.setOrderNum(
                findUserStepCommentaries(commentary.getUserId(), commentary.getStepId())
                        .stream()
                        .mapToInt(Commentary::getOrderNum)
                        .max()
                        .orElse(0) + 1
        );
        return inMemoryInfo
                .getCommentaries()
                .add(commentary);
    }

    @Override
    public Boolean updateCommentary(Commentary commentary) {
        Commentary commentary1 = inMemoryInfo.getCommentaries()
                .stream()
                .filter(c -> c.getStepId().equals(commentary.getStepId())
                && c.getUserId().equals(commentary.getUserId())
                && c.getOrderNum().equals(commentary.getOrderNum()))
                .findFirst()
                .orElse(null);

        if (commentary1 != null) {
            commentary1.setText(commentary.getText());
            return true;
        }

        return false;
    }

    @Override
    public Boolean deleteCommentary(Long stepId, Long userId, Integer order_num) {
        return inMemoryInfo
                .getCommentaries()
                .removeIf(c -> c.getStepId().equals(stepId) && c.getUserId().equals(userId)
                && c.getOrderNum().equals(order_num));
    }

}

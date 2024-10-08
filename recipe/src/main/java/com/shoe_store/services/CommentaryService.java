package com.shoe_store.services;

import com.shoe_store.repositories.commentary.ICommentaryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentaryService {

    private final ICommentaryRepository commentaryRepository;

}

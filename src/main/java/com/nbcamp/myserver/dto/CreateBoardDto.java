package com.nbcamp.myserver.dto;

import com.nbcamp.myserver.entity.Comment;

import java.util.List;

@FunctionalInterface
public interface CreateBoardDto {
    public BoardResponseDto create(Long id, String title, String author, String context, String createdAt, String comments);
}

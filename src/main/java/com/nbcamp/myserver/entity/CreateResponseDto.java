package com.nbcamp.myserver.entity;

import com.nbcamp.myserver.dto.BoardResponseDto;

@FunctionalInterface
public interface CreateResponseDto {
    public BoardResponseDto create(Long id, String title, String author, String context, String createdAt);
}

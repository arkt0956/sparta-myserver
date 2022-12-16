package com.nbcamp.myserver.dto;

@FunctionalInterface
public interface CreateBoardDto {
    public BoardResponseDto create(Long id, String title, String author, String context, String createdAt);
}

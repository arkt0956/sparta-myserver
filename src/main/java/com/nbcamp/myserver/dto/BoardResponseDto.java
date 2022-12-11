package com.nbcamp.myserver.dto;

import lombok.Getter;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String author;
    private String context;
    private String createdAt;

    public BoardResponseDto(Long id, String title, String author, String context, String createdAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.context = context;
        this.createdAt = createdAt;
    }

}

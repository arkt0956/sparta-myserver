package com.nbcamp.myserver.dto;

import lombok.Getter;

@Getter
public class CommentResponseDto {
    //comment 아이디
    private Long id;
    private Long boardId;
    private String username;
    private String context;
    private String createAt;

    public CommentResponseDto(Long id, Long boardId, String username, String context, String createAt) {
        this.id = id;
        this.boardId = boardId;
        this.username = username;
        this.context = context;
        this.createAt = createAt;
    }
}

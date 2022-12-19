package com.nbcamp.myserver.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    //board 아이디
    private Long id;
    private String username;
    private String password;
    private String context;

    public CommentRequestDto(String username, String password, String context) {
        this.username = username;
        this.password = password;
        this.context = context;
    }
}

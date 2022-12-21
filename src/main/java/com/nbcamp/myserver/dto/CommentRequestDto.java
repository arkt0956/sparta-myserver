package com.nbcamp.myserver.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long id;  //board 아이디
    private String username;
    private String password;
    private String context;

}

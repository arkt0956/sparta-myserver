package com.nbcamp.myserver.dto;


import lombok.Getter;

@Getter
public class BoardRequestDto {
    private Long id;
    private String title;
    private String username;
    private String password;
    private String context;
}

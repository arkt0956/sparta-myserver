package com.nbcamp.myserver.dto;

import lombok.Getter;

@Getter
public class SignupResponseDto {
    private String message;
    private String stateCode;

    public SignupResponseDto(String message, String stateCode) {
        this.message = message;
        this.stateCode = stateCode;
    }
}

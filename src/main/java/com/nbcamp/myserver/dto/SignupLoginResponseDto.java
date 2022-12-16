package com.nbcamp.myserver.dto;

import lombok.Getter;

@Getter
public class SignupLoginResponseDto {
    private String message;
    private String stateCode;

    public SignupLoginResponseDto(String message, String stateCode) {
        this.message = message;
        this.stateCode = stateCode;
    }
}

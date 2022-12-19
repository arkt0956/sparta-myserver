package com.nbcamp.myserver.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @Pattern(regexp = "[a-zA-z0-9]{4,10}")
    private String username;
    @Pattern(regexp = "\\w{8,15}")
    private String password;

    private boolean admin = false;
    private String adminToken = "";

    public SignupRequestDto(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public SignupRequestDto(String username, String password, boolean admin, String adminToken) {
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.adminToken = adminToken;
    }
}

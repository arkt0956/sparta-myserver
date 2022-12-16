package com.nbcamp.myserver.controller;

import com.nbcamp.myserver.dto.SignupRequestDto;
import com.nbcamp.myserver.dto.SignupResponseDto;
import com.nbcamp.myserver.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    //회원가입
    @GetMapping("/signup")
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }

    @PostMapping("/signup")
    @ResponseBody
    public SignupResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        SignupResponseDto response = userService.signup(signupRequestDto);
        return response;
    }

    //로그인
}

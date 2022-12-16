package com.nbcamp.myserver.controller;

import com.nbcamp.myserver.dto.LoginRequestDto;
import com.nbcamp.myserver.dto.SignupRequestDto;
import com.nbcamp.myserver.dto.SignupLoginResponseDto;
import com.nbcamp.myserver.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
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
    public SignupLoginResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        SignupLoginResponseDto response = userService.signup(signupRequestDto);
        return response;
    }

    //로그인
    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    @ResponseBody
    public SignupLoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);

    }

}

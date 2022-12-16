package com.nbcamp.myserver.service;

import com.nbcamp.myserver.dto.SignupRequestDto;
import com.nbcamp.myserver.dto.SignupResponseDto;
import com.nbcamp.myserver.entity.User;
import com.nbcamp.myserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

//        if(!Pattern.matches("[a-zA-z0-9]{4,10}", username)) {
//            throw new IllegalArgumentException("아이디는 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 합니다.");
//        }
//        if(!Pattern.matches("\\w{8,15}", password)) {
//            throw new IllegalArgumentException("비밀번호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 합니다.");
//        }

        User user = new User(username, password);
        userRepository.save(user);

        return new SignupResponseDto("success", "200");
    }

}

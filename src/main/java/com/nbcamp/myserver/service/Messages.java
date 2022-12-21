package com.nbcamp.myserver.service;

import org.springframework.web.bind.annotation.PathVariable;

public class Messages {
    public static void createMessage(String stateCode) {
        // 1:   토큰을 전달하지 않았거나 정상 토큰이 아닐 때
        // 2:   토큰이 있고, 유효한 토큰이지만 해당 사용자가 작성한 게시글/댓글이 아닌 경우
        // 3:   DB에 이미 존재하는 username으로 회원가입을 요청한 경우
        // 4:   로그인 시, 전달된 username과 password 중 맞지 않는 정보가 있을 때

        if(stateCode.equals("1")) {
            System.out.println("토큰이 유효하지 않습니다.");
        } else if (stateCode.equals("2")) {
            System.out.println("작성자만 삭제/수정할 수 있습니다.");
        } else if (stateCode.equals("3")) {
            System.out.println("중복된 username 입니다.");
        } else if (stateCode.equals("4")){
            System.out.println("회원을 찾을 수 없습니다.");
        }
    }
}

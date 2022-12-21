package com.nbcamp.myserver.service;

import com.nbcamp.myserver.dto.CommentRequestDto;
import com.nbcamp.myserver.dto.CommentResponseDto;
import com.nbcamp.myserver.dto.SignupLoginResponseDto;
import com.nbcamp.myserver.entity.Board;
import com.nbcamp.myserver.entity.Comment;
import com.nbcamp.myserver.entity.User;
import com.nbcamp.myserver.entity.UserRoleEnum;
import com.nbcamp.myserver.jwt.JwtUtil;
import com.nbcamp.myserver.repository.BoardRepository;
import com.nbcamp.myserver.repository.CommentRepository;
import com.nbcamp.myserver.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public CommentResponseDto addComments(CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }

        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );

        List<Comment> existCommentList = commentRepository.findAllByUser(user);

        Board board = boardRepository.findById(commentRequestDto.getId()).get();

        Comment comment = new Comment(commentRequestDto.getContext(), user, board);
        existCommentList.add(comment);
        board.postComment(comment);
        commentRepository.saveAll(existCommentList);

        return comment.createResponse();
    }

    //로그인이 되어있다고 가정 (비밀번호 확인 x)
    @Transactional
    public List<CommentResponseDto> getComments(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if(jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("회원을 찾을 수 없습니다.")
            );

            // 사용자 권한 가져와서 ADMIN 이면 전체 조회, USER 면 본인이 추가한 부분 조회
            UserRoleEnum userRoleEnum = user.getRole();
            System.out.println("role = " + userRoleEnum);

            List<Comment> commentList;

            if (userRoleEnum == UserRoleEnum.USER) {
                // 사용자 권한이 USER일 경우
                commentList =  commentRepository.findAllByUser(user);
            } else {
                commentList = commentRepository.findAll();
            }


            List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
            for(Comment comment : commentList) {
                commentResponseDtoList.add(comment.createResponse());
            }
            return commentResponseDtoList;
        }
        return null;
    }

    @Transactional
    public CommentResponseDto changeComments(CommentRequestDto commentRequestDto, HttpServletRequest request) {
        Comment updated = new Comment();

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("회원을 찾을 수 없습니다.")
            );

            UserRoleEnum userRoleEnum = user.getRole();
            System.out.println("role = " + userRoleEnum);

            List<Comment> commentList;

            if (userRoleEnum == UserRoleEnum.USER) {
                commentList =  commentRepository.findAllByUser(user);
            } else {
                commentList = commentRepository.findAll();
            }

            String stateCode = "2";
            for (Comment comment : commentList) {
                if (comment.getId() == commentRequestDto.getId()) {
                    comment.update(commentRequestDto);
                    updated = comment;
                    stateCode = "0";
                    break;
                }
            }
            commentRepository.saveAll(commentList);
            Messages.createMessage(stateCode);
        }
        if(updated.getId() != null) {
            return updated.createResponse(); //찾을 수 없을때 null이 그대로 반환돼서 statecode 500이 찍힘
        } else {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }

    }

    @Transactional
    public SignupLoginResponseDto deleteComments(CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
            );

            UserRoleEnum userRoleEnum = user.getRole();
            System.out.println("role = " + userRoleEnum);

            List<Comment> commentList;

            if (userRoleEnum == UserRoleEnum.USER) {
                commentList =  commentRepository.findAllByUser(user);
            } else {
                commentList = commentRepository.findAll();
            }

            for (Comment comment : commentList) {
                if (comment.getId() == commentRequestDto.getId()) {
                    commentRepository.deleteById(comment.getId());
                    return new SignupLoginResponseDto("success", "200");
                }
            }
            Messages.createMessage("2");
        }
        return new SignupLoginResponseDto("토큰이 유효하지 않습니다.", "400");
    }
}

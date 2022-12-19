package com.nbcamp.myserver.service;

import com.nbcamp.myserver.dto.CommentRequestDto;
import com.nbcamp.myserver.dto.CommentResponseDto;
import com.nbcamp.myserver.entity.Board;
import com.nbcamp.myserver.entity.Comment;
import com.nbcamp.myserver.entity.User;
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
            throw new IllegalArgumentException("Token을 확인해 주세요.");
        }

        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        List<Comment> existCommentList = commentRepository.findAllByUser(user);

        Board board = boardRepository.findById(commentRequestDto.getId()).get();

        Comment comment = new Comment(commentRequestDto.getContext(), user, board);
        existCommentList.add(comment);
        commentRepository.saveAll(existCommentList);

        return comment.createResponse();
    }

    @Transactional
    public List<CommentResponseDto> getComments(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if(jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
            );

            List<Comment> commentList =  commentRepository.findAllByUser(user);
            List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
            for(Comment comment : commentList) {
                commentResponseDtoList.add(comment.createResponse());
            }
            return commentResponseDtoList;
        }
        return null;
    }
}

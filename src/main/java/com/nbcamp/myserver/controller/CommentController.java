package com.nbcamp.myserver.controller;

import com.nbcamp.myserver.dto.CommentRequestDto;
import com.nbcamp.myserver.dto.CommentResponseDto;
import com.nbcamp.myserver.dto.SignupLoginResponseDto;
import com.nbcamp.myserver.entity.Comment;
import com.nbcamp.myserver.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comments")
    public List<CommentResponseDto> getComments(HttpServletRequest request) {
        return commentService.getComments(request);
    }

    @PostMapping("/comments")
    public CommentResponseDto addComments(@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.addComments(commentRequestDto, request);
    }

    @PutMapping("/comments")
    public CommentResponseDto changeComments(@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.changeComments(commentRequestDto, request);
    }

    @DeleteMapping("/comments")
    public SignupLoginResponseDto deleteComments(@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.deleteComments(commentRequestDto, request);
    }
}

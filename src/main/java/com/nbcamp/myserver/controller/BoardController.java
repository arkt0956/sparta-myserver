package com.nbcamp.myserver.controller;

import com.nbcamp.myserver.dto.BoardRequestDto;
import com.nbcamp.myserver.dto.BoardResponseDto;
import com.nbcamp.myserver.dto.SignupLoginResponseDto;
import com.nbcamp.myserver.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @PostMapping("/api/boards")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        return boardService.createBoard(requestDto, request);
    }

    @GetMapping("/api/boards")
    public List<BoardResponseDto> getBoards(HttpServletRequest request) {
        return boardService.findBoards(request);
    }

    @PutMapping("/api/boards/{id}")
    public BoardResponseDto updateBoards(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        return boardService.update(id, requestDto, request);
    }

    @DeleteMapping("/api/boards/{id}")
    public SignupLoginResponseDto deleteBoards(@PathVariable Long id, HttpServletRequest request) {
        return boardService.delete(id, request);
    }
}
package com.nbcamp.myserver.controller;

import com.nbcamp.myserver.dto.BoardRequestDto;
import com.nbcamp.myserver.dto.BoardResponseDto;
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

    // 전체 게시글 조회(관리자 토큰 필요)
    @GetMapping("/api/allboards")
    public List<BoardResponseDto> getAllBoards(HttpServletRequest request) { return boardService.findAllBoards(request);}

//    @GetMapping("/api/board/{id}")
//    public BoardResponseDto getBoard(@PathVariable Long id) {
//        return boardService.findOne(id);
//    }

    @PutMapping("/api/boards/{id}")
    public BoardResponseDto updateBoards(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.update(id, requestDto);
    }

    @DeleteMapping("/api/boards/{id}")
    public String deleteBoards(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.delete(id, requestDto);
    }
}


package com.nbcamp.myserver.controller;

import com.nbcamp.myserver.dto.BoardRequestDto;
import com.nbcamp.myserver.dto.BoardResponseDto;
import com.nbcamp.myserver.service.BoardService;
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
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto) {
        return boardService.createBoard(requestDto);
    }

    @GetMapping("/api/boards")
    public List<BoardResponseDto> getBoards() {
        return boardService.findBoards();
    }

    @GetMapping("/api/board/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id) {
        return boardService.findOne(id);
    }

    @PutMapping("/api/boards/{id}")
    public BoardResponseDto updateBoards(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.update(id, requestDto);
    }

    @DeleteMapping("/api/boards/{id}")
    public String deleteBoards(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.delete(id, requestDto);
    }
}


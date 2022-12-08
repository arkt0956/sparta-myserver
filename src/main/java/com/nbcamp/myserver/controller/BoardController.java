package com.nbcamp.myserver.controller;

import com.nbcamp.myserver.dto.BoardRequestDto;
import com.nbcamp.myserver.entity.Board;
import com.nbcamp.myserver.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public Board createBoard(@RequestBody BoardRequestDto requestDto) {
        return boardService.createBoard(requestDto);
    }

    @GetMapping("/api/boards")
    public List<Board> getBoards() {
        return boardService.findBoards();
    }

    @GetMapping("/api/board/{id}")
    public Board getBoard(@PathVariable Long id) {
        return boardService.findOne(id);
    }

    @PutMapping("/api/boards/{id}")
    public Board updateBoards(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.update(id, requestDto);
    }

    @DeleteMapping("/api/boards/{id}")
    public String deleteBoards(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.deleteMemo(id, requestDto);
    }
}


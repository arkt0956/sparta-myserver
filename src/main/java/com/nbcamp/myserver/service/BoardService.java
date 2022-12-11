package com.nbcamp.myserver.service;

import com.nbcamp.myserver.dto.BoardRequestDto;
import com.nbcamp.myserver.dto.BoardResponseDto;
import com.nbcamp.myserver.entity.Board;
import com.nbcamp.myserver.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        Board board = new Board(requestDto);
        boardRepository.save(board);
        return board.createResponse(BoardResponseDto::new);
    }

    public BoardResponseDto update(Long id, BoardRequestDto requestDto) {

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
        );
        if(board.getPassword().equals(requestDto.getPassword())) {
            board.update(requestDto);
        }
        return board.createResponse(BoardResponseDto::new);
    }


    public String delete(Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
        );
        if(board.getPassword().equals(requestDto.getPassword())) {
            boardRepository.deleteById(id);
            return "{\"msg\":\"success\"}";
        }
        return "{\"msg\":\"fail\"}";
    }

    public List<BoardResponseDto> findBoards() {
        List<Board> boards = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<BoardResponseDto> responseDtos = new ArrayList<>();
        for(Board board: boards) {
            responseDtos.add(board.createResponse(BoardResponseDto::new));
        }
        return responseDtos;
    }
    public BoardResponseDto findOne(Long memberId) {
        Board board = boardRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
        );
        return board.createResponse(BoardResponseDto::new);
    }
}

package com.nbcamp.myserver.service;

import com.nbcamp.myserver.dto.BoardRequestDto;
import com.nbcamp.myserver.dto.BoardResponseDto;
import com.nbcamp.myserver.dto.SignupLoginResponseDto;
import com.nbcamp.myserver.entity.Board;
import com.nbcamp.myserver.entity.User;
import com.nbcamp.myserver.jwt.JwtUtil;
import com.nbcamp.myserver.repository.BoardRepository;
import com.nbcamp.myserver.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
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
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final String ADMIN_TOKEN = "TMzNDQiLCJhdXRoIjoiZWZlZmVmNjY2NzciLCJleHA";

    public BoardResponseDto createBoard(BoardRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("Sorry. That Post not exist.")
            );

            Board board = boardRepository.saveAndFlush(new Board(requestDto, user.getId()));

            return board.createResponse(BoardResponseDto::new);
        } else {
            return null;
        }
    }

    public BoardResponseDto update(Long id, BoardRequestDto requestDto,HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if(jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("해당 사용자가 없는데요?")
            );

            Board board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
            );
            if(board.getPassword().equals(requestDto.getPassword())) {
                board.update(requestDto);
            }
            return board.createResponse(BoardResponseDto::new);
        }
        return null;

    }


    public SignupLoginResponseDto delete(Long id, BoardRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if(jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("해당 사용자가 없습니다.")
            );

            Board board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
            );
            if(board.getPassword().equals(requestDto.getPassword())) {
                boardRepository.deleteById(id);
                return new SignupLoginResponseDto("success", "200");
            }
        }
        return new SignupLoginResponseDto("fail", "400");
    }

    public List<BoardResponseDto> findBoards(HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("Sorry. That user not exist.")
            );

            List<BoardResponseDto> list = new ArrayList<>();
            List<Board> boardList;

            // 아이디로 조회
            boardList = boardRepository.findAllByUserId(user.getId());
            for (Board board : boardList) {
                list.add(board.createResponse(BoardResponseDto::new));
            }
            return list;
        } else {
            return null;
        }

    }

    public List<BoardResponseDto> findAllBoards(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if(token.equals(ADMIN_TOKEN)) {
                List<Board> boards = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
                List<BoardResponseDto> responseDtos = new ArrayList<>();
                for(Board board: boards) {
                    responseDtos.add(board.createResponse(BoardResponseDto::new));
                }
                return responseDtos;
            }
        }
        return null;
    }
//    public BoardResponseDto findOne(Long memberId) {
//        Board board = boardRepository.findById(memberId).orElseThrow(
//                () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
//        );
//        return board.createResponse(BoardResponseDto::new);
//    }
}

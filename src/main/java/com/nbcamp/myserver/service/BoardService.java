package com.nbcamp.myserver.service;

import com.nbcamp.myserver.dto.BoardRequestDto;
import com.nbcamp.myserver.dto.BoardResponseDto;
import com.nbcamp.myserver.dto.SignupLoginResponseDto;
import com.nbcamp.myserver.entity.Board;
import com.nbcamp.myserver.entity.User;
import com.nbcamp.myserver.entity.UserRoleEnum;
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

            Board board = boardRepository.saveAndFlush(new Board(requestDto, user));

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
                    () -> new IllegalArgumentException("?????? ???????????? ?????????????")
            );

            Board board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("???????????? ???????????? ????????????.")
            );


            // ????????? ?????? ???????????? ADMIN ?????? ?????? ??????, USER ??? ????????? ????????? ?????? ??????
            UserRoleEnum userRoleEnum = user.getRole();
            System.out.println("role = " + userRoleEnum);

            List<Board> boardList;

            if (userRoleEnum == UserRoleEnum.USER) {
                // ????????? ????????? USER??? ??????
                if(board.getPassword().equals(requestDto.getPassword())) {
                    boardList = boardRepository.findAllByUserId(user.getId());
                } else {
                    throw new IllegalArgumentException("??????????????? ???????????????.");
                }
            } else {
                boardList = boardRepository.findAll();
            }


            for (Board b : boardList) {
                if(b.getId() == id) {
                    b.update(requestDto);
                    boardRepository.save(b);
                }
                return b.createResponse(BoardResponseDto::new);
            }

        }
        return null;

    }


    public SignupLoginResponseDto delete(Long id, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if(jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("?????? ???????????? ????????????.")
            );

            UserRoleEnum userRoleEnum = user.getRole();
            System.out.println("role = " + userRoleEnum);

            List<Board> boardList;

            if (userRoleEnum == UserRoleEnum.USER) {
                // ????????? ????????? USER??? ??????
                boardList = boardRepository.findAllByUserId(user.getId());
            } else {
                boardList = boardRepository.findAll();
            }


            for (Board b : boardList) {
                if(b.getId().equals(id)) {
                    boardRepository.deleteById(id);
                    return new SignupLoginResponseDto("success","200");
                }
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

            UserRoleEnum userRoleEnum = user.getRole();
            System.out.println("role = " + userRoleEnum);

            List<BoardResponseDto> list = new ArrayList<>();
            List<Board> boardList;

            if(userRoleEnum == UserRoleEnum.USER) {
                boardList = boardRepository.findAllByUserId(user.getId());
                for (Board board : boardList) {
                    list.add(board.createResponse(BoardResponseDto::new));
                }
            } else {
                List<Board> boards = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
                for(Board board: boards) {
                    list.add(board.createResponse(BoardResponseDto::new));
                }
            }
            return list;
        } else {
            return null;
        }

    }

}

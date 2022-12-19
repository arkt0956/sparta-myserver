package com.nbcamp.myserver.repository;

import com.nbcamp.myserver.entity.Board;
import com.nbcamp.myserver.entity.Comment;
import com.nbcamp.myserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByUserId(Long userId);
    List<Board> findAll();
    Optional<Board> findByIdAndUserId(Long id, Long userId);

}
